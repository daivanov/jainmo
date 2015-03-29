/**
 *
 */
package uk.co.jemos.podam.api;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.jemos.podam.common.ConstructorLightFirstComparator;

/**
 * Searches implementations of interfaces and abstract classes from the same
 * jar, where interface or abstract class was defined.
 * <p>
 * The resolver tries to range all found implementations according to
 * complexity to instantiate the found classes and tests it. 
 * <b>If you seek for deterministic implementation resolution,
 * use {@link AbstractRandomDataProviderStrategy#addSpecific(Class, Class)}
 * instead</b>
 * </p>
 *
 * @author daivanov
 *
 * @since 5.3.0
 *
 */

public class SpecificImplementationsResolver {

	// ------------------->> Constants

	// ------------------->> Instance / Static variables

	/** Logger */
	private static final Logger LOG = LoggerFactory
			.getLogger(SpecificImplementationsResolver.class);

	/** The implementation comparator */
	public static final ImplementationComparator IMPLEMENTATION_COMPARATOR
			= new ImplementationComparator();

	/**
	 * Cache of class resources
	 */
	private static final Map<String, Set<Class<?>>> resourceCache
			= new ConcurrentHashMap<String, Set<Class<?>>>();

	/** Test factory to validate implementations */
	private PodamFactory factory;

	// ------------------->> Constructors

	// ------------------->> Public methods

	// ------------------->> Getters / Setters

	/**
	 * Resolves abstract classes and interfaces.
	 * <p>
	 * Should return specific class type, which can be instantiated and assigned
	 * to abstract class type or interface.
	 * </p>
	 * 
	 * @param nonInstantiatableClass
	 *            Abstract class type or interface
	 * @return Non-abstract class type derived from
	 *         {@code nonInstantiatableClass}, or {@code nonInstantiatableClass},
	 *         if no implementation was found
	 */
	public <T> Class<? extends T> getSpecificClass(
			Class<T> nonInstantiatableClass) {

		List<Class<? extends T>> implementations
				= findImplementations(nonInstantiatableClass);
		Class<? extends T> found;
		if (implementations.size() > 0) {

			found = implementations.get(0);
			LOG.warn("For {} found {} implementations, will use {}",
					nonInstantiatableClass, implementations.size(), found);
		} else {

			found = nonInstantiatableClass;
		}
		return found;
	}

	// ------------------->> Private methods

	/**
	 * Find set of classes implementing current abstract class from the same
	 * resource where this class is located
	 *
	 * @param abstractClass
	 *             abstract class to research
	 * @return return set of classes implementing the given abstract class
	 */
	private <T> List<Class<? extends T>> findImplementations(
			Class<T> abstractClass) {

		String clazzName = '/' + abstractClass.getName().replace('.', '/') + ".class";
		String path = abstractClass.getResource(clazzName).getPath();
		path = path.replaceAll(".*:", "");
		path = path.replaceAll("!.*", "");

		Set<Class<?>> classes = listResourceClasses(path);

		int abstractCount = 0;
		List<Class<? extends T>> implementingClasses = new ArrayList<Class<? extends T>>();
		for (Class<?> clazz : classes) {

			if (abstractClass.isAssignableFrom(clazz) && !abstractClass.equals(clazz)) {

				@SuppressWarnings("unchecked")
				Class<? extends T> implementation = (Class<? extends T>) clazz;

				implementingClasses.add(implementation);
				if (Modifier.isAbstract(implementation.getModifiers())) {
					abstractCount++;
				}
			}
		}

		if (abstractCount < implementingClasses.size()) {

			Iterator<Class<? extends T>> iter = implementingClasses.iterator();
			while(iter.hasNext()) {

				Class<? extends T> clazz = iter.next();
				if (Modifier.isAbstract(clazz.getModifiers())) {
					iter.remove();
				}
			}
		}

		Collections.sort(implementingClasses, IMPLEMENTATION_COMPARATOR);

		StackTraceElement[] traces = new Throwable().getStackTrace();
		String resolverClassName = this.getClass().getName();

		int j = 0;
		/* Skip current call traces*/
		for ( ; j < traces.length; j++) {
			if (!traces[j].getClassName().equals(resolverClassName)) {
				break;
			}
		}

		/* Check, if there are other calls to this class in the traces */
		boolean noLoopDetected = true;
		for ( ; j < traces.length; j++) {
			if (traces[j].getClassName().equals(resolverClassName)) {

				LOG.warn("Loop detected in resolving {}, won't test manufacturing",
						abstractClass);
				noLoopDetected = false;
				break;
			}
		}

		if (noLoopDetected) {

			/* Lazy initialization of factory */
			if (factory == null) {
				factory = new PodamFactoryImpl();
			}

			/* It's safe to test POJO manufacturing */
			Iterator<Class<? extends T>> iter = implementingClasses.iterator();
			while (iter.hasNext()) {

				Class<? extends T> implementingClass = iter.next();

				TypeVariable<?>[] types = implementingClass.getTypeParameters();
				Class<?>[] genericParams = new Class[types.length];
				for (int i = 0; i < types.length; i++) {
					genericParams[i] = resolveGenericParameter(types[i]);
				}

				Object instantiated = null;
				try {
					instantiated = factory.manufacturePojo(implementingClass,
							genericParams);
				} catch(Exception e) {
					LOG.debug("Instantiation failure {}", implementingClass, e);
				}
				if (instantiated == null) {
					LOG.warn("Implementation {} is not instantiatable,"
							+ "remove it from cache", implementingClass);
					classes.remove(implementingClass);
					iter.remove();
				} else {
					break;
				}
			}
		}

		LOG.warn("For {} found {} implementations", abstractClass,
				implementingClasses.size());
		return implementingClasses;
	}

	/**
	 * Resolves generic types into specific classes
	 *
	 * @param paramType
	 *        parameter type for which to find specific implementing class
	 * @return
	 *        a class matching to the provided type
	 */
	private Class<?> resolveGenericParameter(Type paramType) {

		Class<?> parameterType = null;
		if (paramType instanceof TypeVariable<?>) {
			final TypeVariable<?> typeVariable = (TypeVariable<?>) paramType;
			Type[] bounds = typeVariable.getBounds();
			if (bounds != null && bounds.length > 0) {
				parameterType = resolveGenericParameter(bounds[0]);
				if (parameterType.isAssignableFrom(String.class)) {
					parameterType = String.class;
				}
			} else {
				parameterType = String.class;
			}
		} else if (paramType instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) paramType;
			parameterType = resolveGenericParameter(pType.getRawType());
		} else if (paramType instanceof WildcardType) {
			WildcardType wType = (WildcardType) paramType;
			Type[] bounds = wType.getLowerBounds();
			if (bounds != null && bounds.length > 0) {
				parameterType = resolveGenericParameter(bounds[0]);
			}
		} else if (paramType instanceof Class) {
			parameterType = (Class<?>) paramType;
		}

		if (parameterType == null) {
			parameterType = String.class;
		}
		return parameterType;
	}

	/**
	 * Find classes from the resource
	 *
	 * @param path
	 *            path of the resource
	 * @return set of public classes found within the resource 
	 */
	private Set<Class<?>> listResourceClasses(String path) {

		Set<Class<?>> classes = resourceCache.get(path);
		if (classes != null) {
			return classes;
		} else {
			classes = new HashSet<Class<?>>();
		}

		LOG.info("Caching resource {}", path);

		ZipInputStream zip = null;
		try {

			FileInputStream fis = new FileInputStream(path);
			zip = new ZipInputStream(fis);
			loop : for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {

				if (entry.getName().endsWith(".class") && !entry.isDirectory()) {

					StringBuilder className=new StringBuilder();
					for(String part : entry.getName().split("/")) {

						if (className.length() != 0) {

							className.append(".");
						}
						className.append(part);
						if (part.endsWith(".class")) {

							className.setLength(className.length()-".class".length());
						}
					}
					String foundClass = className.toString();
					if (foundClass.contains("$")) {

						String[] parts = foundClass.split("\\$");
						for (int i = 1; i < parts.length; i++) {

							if (Character.isDigit(parts[i].charAt(0))) {

								continue loop;
							}
						}
					}

					Class<?> clazz = null;
					try {
						clazz = Class.forName(foundClass);
						if (!Modifier.isInterface(clazz.getModifiers())
								&& Modifier.isPublic(clazz.getModifiers())) {
							classes.add(clazz);
						}
					} catch(Throwable e) {
						LOG.error("Cannot load class for name {}: {}",
								foundClass, e);
					}

				}
			}
			zip.close();
			zip = null;
		} catch(Throwable t) {

			LOG.error("Search in JAR file failed", t);
		} finally {

			closeStream(zip);
		}

		resourceCache.put(path, classes);

		return classes;
	}

	/**
	 * Closes closeable safely
	 * @param stream closeable to close
	 */
	private static void closeStream(Closeable stream) {

		if (stream != null) {

			try {
				stream.close();
			} catch (IOException e) {
				LOG.error("Failed to close closeable", e);
			}
		}
	}

	// ------------------->> equals() / hashcode() / toString()

	// ------------------->> Inner classes

	/**
	 * Sorts implementation classes according to heuristics how easy it will
	 * be to instantiate them  
	 */
	public static class ImplementationComparator implements Comparator<Class<?>> {

		/**
		 * Helper method to compute generality of class heuristic
		 * 
		 * @param clazz
		 *        Class to analyze
		 * @return
		 *        integer criteria how general/specific class is, smaller
		 *        numbers correspond to more general classes
		 */
		public int classGenerality(Class<?> clazz) {

			return clazz.getPackage().getName().startsWith("java") ? 0 : 1;
		}

		@Override
		public int compare(Class<?> class1, Class<?> class2) {

			int result = classGenerality(class1) - classGenerality(class2);
			if (result != 0) {
				return result;
			}

			Constructor<?>[] constructors1 = class1.getConstructors();
			if (constructors1.length == 0) {
				constructors1 = class1.getDeclaredConstructors();
			}
			Arrays.sort(constructors1, ConstructorLightFirstComparator.INSTANCE);

			Constructor<?>[] constructors2 = class2.getConstructors();
			if (constructors2.length == 0) {
				constructors2 = class2.getDeclaredConstructors();
			}
			Arrays.sort(constructors2, ConstructorLightFirstComparator.INSTANCE);

			return ConstructorLightFirstComparator.INSTANCE.compare(constructors1[0], constructors2[0]);
		}
	}
}
