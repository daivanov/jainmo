package uk.co.jemos.podam.test.unit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.RandomDataProviderStrategy;
import uk.co.jemos.podam.test.dto.AbstractTestPojo;
import uk.co.jemos.podam.test.dto.CollectionsPojo;
import uk.co.jemos.podam.test.dto.ConcreteTestPojo;
import uk.co.jemos.podam.test.dto.ConstructorWithSelfReferencesButNoDefaultConstructorPojo;
import uk.co.jemos.podam.test.dto.ConstructorWithSelfReferencesPojo;
import uk.co.jemos.podam.test.dto.EmbeddedAbstractFieldTestPojo;
import uk.co.jemos.podam.test.dto.EnumsPojo;
import uk.co.jemos.podam.test.dto.EnumsPojo.RatePodamInternal;
import uk.co.jemos.podam.test.dto.ImmutableNoHierarchicalAnnotatedPojo;
import uk.co.jemos.podam.test.dto.ImmutableNonAnnotatedPojo;
import uk.co.jemos.podam.test.dto.ImmutableWithGenericCollectionsPojo;
import uk.co.jemos.podam.test.dto.ImmutableWithNonGenericCollectionsPojo;
import uk.co.jemos.podam.test.dto.InterfacePojo;
import uk.co.jemos.podam.test.dto.NoDefaultConstructorPojo;
import uk.co.jemos.podam.test.dto.NoSetterWithCollectionInConstructorPojo;
import uk.co.jemos.podam.test.dto.OneDimensionalChildPojo;
import uk.co.jemos.podam.test.dto.OneDimensionalTestPojo;
import uk.co.jemos.podam.test.dto.PrivateNoArgConstructorPojo;
import uk.co.jemos.podam.test.dto.RecursivePojo;
import uk.co.jemos.podam.test.dto.SimplePojoToTestSetters;
import uk.co.jemos.podam.test.dto.SingletonWithParametersInStaticFactoryPojo;
import uk.co.jemos.podam.test.dto.pdm33.NoDefaultPublicConstructorPojo;
import uk.co.jemos.podam.test.dto.pdm33.PrivateOnlyConstructorPojo;
import uk.co.jemos.podam.test.dto.pdm33.ProtectedNonDefaultConstructorPojo;
import uk.co.jemos.podam.test.dto.pdm6.Child;
import uk.co.jemos.podam.test.dto.pdm6.Parent;
import uk.co.jemos.podam.test.dto.pdm6.RecursiveList;
import uk.co.jemos.podam.test.dto.pdm6.RecursiveMap;
import uk.co.jemos.podam.test.enums.ExternalRatePodamEnum;

import javax.activation.DataHandler;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Unit test for simple App.
 */
public class PodamMockerUnitTest {

	/** The podam factory */
	private static final PodamFactory factory = new PodamFactoryImpl();

	/** The default data strategy */
	private static final RandomDataProviderStrategy strategy
			= (RandomDataProviderStrategy) factory.getStrategy();

	/** Backup of memoization setting */
	private final static boolean memoizationBackup = strategy.isMemoizationEnabled();

	/**
	 * Restores memoization settings after test run
	 */
	@After
	public void cleanup() {
		strategy.setMemoization(memoizationBackup);
	}

	@Test
	public void testMockerForClassWithoutDefaultConstructor() {

		NoDefaultConstructorPojo pojo = factory
				.manufacturePojo(NoDefaultConstructorPojo.class);
		Assert.assertNotNull(
				"The pojo with no default constructors must not be null!", pojo);

	}

	@Test
	public void testMockerForAbstractClass() {
		strategy.setMemoization(false);
		// Trying to create an abstract class should thrown an instantiation
		// exception
		AbstractTestPojo pojo = factory.manufacturePojo(AbstractTestPojo.class);
		Assert.assertNull("The abstract pojo should be null!", pojo);
	}

	@Test
	public void testMockerForAbstractClassWithFactory() {
		strategy.setMemoization(false);
		// Trying to create an abstract class with a factory class
		// implementation should be fine
		strategy.addFactory(Transformer.class, TransformerFactory.class);
		Transformer pojo = factory.manufacturePojo(Transformer.class);
		Assert.assertNotNull(
				"The abstract pojo should not be null since a factory has been specified",
				pojo);
		strategy.removeFactory(Transformer.class);
	}

	@Test
	public void testMockerForAbstractClassWithConcreteSpecified() {
		strategy.setMemoization(false);
		// Trying to create an abstract class with a specified concrete
		// implementation should be fine
		strategy.addSpecific(AbstractTestPojo.class, ConcreteTestPojo.class);
		AbstractTestPojo pojo = factory.manufacturePojo(AbstractTestPojo.class);
		Assert.assertNotNull(
				"The abstract pojo should not be null since a concrete impl has been specified",
				pojo);
		strategy.removeSpecific(AbstractTestPojo.class);
	}

	@Test
	public void testMockerForEmbeddedAbstractClassWithConcreteSpecified() {
		strategy.setMemoization(false);
		// Trying to create an abstract class with a specified concrete
		// implementation should be fine
		strategy.addSpecific(AbstractTestPojo.class, ConcreteTestPojo.class);
		EmbeddedAbstractFieldTestPojo pojo = factory
				.manufacturePojo(EmbeddedAbstractFieldTestPojo.class);
		Assert.assertNotNull("The pojo should not be null", pojo);
		Assert.assertNotNull(
				"The abstract embedded pojo should not be null since a concrete impl has been specified",
				pojo.getPojo());
		strategy.removeSpecific(AbstractTestPojo.class);
	}

	@Test
	public void testMockerForInterface() {
		// Trying to create an interface class should thrown an instantiation
		// exception
		InterfacePojo pojo = factory.manufacturePojo(InterfacePojo.class);
		Assert.assertNull("The interface pojo should be null!", pojo);

	}

	@Test
	public void testMockerForPrimitiveType() {
		int intValue = factory.manufacturePojo(int.class);
		Assert.assertTrue("The int value should not be zero!", intValue != 0);
	}

	@Test
	public void testMockerForPojoWithPrivateNoArgConstructor() {
		PrivateNoArgConstructorPojo pojo = factory
				.manufacturePojo(PrivateNoArgConstructorPojo.class);
		Assert.assertNotNull(
				"The pojo with private default constructor cannot be null!",
				pojo);
	}

	@Test
	public void testOneDimensionalTestPojo() {

		OneDimensionalTestPojo pojo = factory
				.manufacturePojo(OneDimensionalTestPojo.class);
		Assert.assertNotNull("The object cannot be null!", pojo);

		validateDimensionalTestPojo(pojo);
	}

	@Test
	public void testOneDimensionalTestPojoPopulation() {

		OneDimensionalTestPojo pojo = new OneDimensionalTestPojo();
		factory.populatePojo(pojo);

		validateDimensionalTestPojo(pojo);
	}

	public void validateDimensionalTestPojo(OneDimensionalTestPojo pojo) {

		Boolean booleanObjectField = pojo.getBooleanObjectField();
		Assert.assertTrue(
				"The boolean object field should have a value of TRUE",
				booleanObjectField);

		boolean booleanField = pojo.isBooleanField();
		Assert.assertTrue("The boolean field should have a value of TRUE",
				booleanField);

		byte byteField = pojo.getByteField();
		Assert.assertTrue("The byte field should not be zero", byteField != 0);

		Byte byteObjectField = pojo.getByteObjectField();
		Assert.assertTrue("The Byte object field should not be zero",
				byteObjectField != 0);

		short shortField = pojo.getShortField();
		Assert.assertTrue("The short field should not be zero", shortField != 0);

		Short shortObjectField = pojo.getShortObjectField();
		Assert.assertTrue("The Short Object field should not be zero",
				shortObjectField != 0);

		char charField = pojo.getCharField();
		Assert.assertTrue("The char field should not be zero", charField != 0);
		Character characterObjectField = pojo.getCharObjectField();
		Assert.assertTrue("The Character object field should not be zero",
				characterObjectField != 0);

		int intField = pojo.getIntField();
		Assert.assertTrue("The int field cannot be zero", intField != 0);
		Integer integerField = pojo.getIntObjectField();
		Assert.assertTrue("The Integer object field cannot be zero",
				integerField != 0);

		long longField = pojo.getLongField();
		Assert.assertTrue("The long field cannot be zero", longField != 0);
		Long longObjectField = pojo.getLongObjectField();
		Assert.assertTrue("The Long object field cannot be zero",
				longObjectField != 0);

		float floatField = pojo.getFloatField();
		Assert.assertTrue("The float field cannot be zero", floatField != 0.0);
		Float floatObjectField = pojo.getFloatObjectField();
		Assert.assertTrue("The Float object field cannot be zero",
				floatObjectField != 0.0);

		double doubleField = pojo.getDoubleField();
		Assert.assertTrue("The double field cannot be zero",
				doubleField != 0.0d);
		Double doubleObjectField = pojo.getDoubleObjectField();
		Assert.assertTrue("The Double object field cannot be zero",
				doubleObjectField != 0.0d);

		String stringField = pojo.getStringField();
		Assert.assertNotNull("The String field cannot be null", stringField);
		Assert.assertFalse("The String field cannot be empty",
				stringField.equals(""));

		Object objectField = pojo.getObjectField();
		Assert.assertNotNull("The Object field cannot be null", objectField);

		Calendar calendarField = pojo.getCalendarField();
		checkCalendarIsValid(calendarField);

		Date dateField = pojo.getDateField();
		Assert.assertNotNull("The date field is not valid", dateField);

		Random[] randomArray = pojo.getRandomArray();
		Assert.assertNotNull("The array of Random objects cannot be null!",
				randomArray);
		Assert.assertEquals("The array of Random length should be one!",
				strategy.getNumberOfCollectionElements(Random.class),
				randomArray.length);
		Random random = randomArray[0];
		Assert.assertNotNull(
				"The Random array element at [0] should not be null", random);

		int[] intArray = pojo.getIntArray();
		Assert.assertNotNull("The array of ints cannot be null!", intArray);
		Assert.assertEquals(
				"The array of ints length should be the same as defined in the strategy!",
				strategy.getNumberOfCollectionElements(Integer.class),
				intArray.length);
		Assert.assertTrue(
				"The first element in the array of ints must be different from zero!",
				intArray[0] != 0);

		boolean[] booleanArray = pojo.getBooleanArray();
		Assert.assertNotNull("The array of booleans cannot be null!",
				booleanArray);
		Assert.assertEquals(
				"The array of boolean length should be the same as the one set in the strategy!",
				strategy.getNumberOfCollectionElements(Boolean.class),
				booleanArray.length);

		BigDecimal bigDecimalField = pojo.getBigDecimalField();
		Assert.assertNotNull("The BigDecimal field cannot be null!",
				bigDecimalField);

	}

	@Test
	public void testRecursiveHierarchyPojo() {
		RecursivePojo pojo = factory.manufacturePojo(RecursivePojo.class);
		Assert.assertNotNull("The recursive pojo cannot be null!", pojo);

		validateRecursiveHierarchyPojo(pojo);
	}

	@Test
	public void testRecursiveHierarchyPojoPopulation() {
		RecursivePojo pojo = new RecursivePojo();
		factory.populatePojo(pojo);

		validateRecursiveHierarchyPojo(pojo);
	}

	private void validateRecursiveHierarchyPojo(RecursivePojo pojo) {
		Assert.assertTrue("The integer value in the pojo should not be zero!",
				pojo.getIntField() != 0);

		RecursivePojo parentPojo = pojo.getParent();
		Assert.assertNotNull("The parent pojo cannot be null!", parentPojo);
		Assert.assertTrue(
				"The integer value in the parent pojo should not be zero!",
				parentPojo.getIntField() != 0);
		Assert.assertNotNull(
				"The parent attribute of the parent pojo cannot be null!",
				parentPojo.getParent());

	}

	@Test
	public void testCircularDependencyPojos() {
		Parent parent = factory.manufacturePojo(Parent.class);
		Assert.assertNotNull("The parent pojo cannot be null!", parent);

		Child child = parent.getChild();
		Assert.assertNotNull("The child pojo cannot be null!", child);
	}

	@Test
	public void testCircularDependencyCollection() {

		RecursiveList pojo = factory.manufacturePojo(RecursiveList.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		Assert.assertNotNull("The pojo's list cannot be null!", pojo.getList());
		Assert.assertTrue("The pojo's list cannot be empty!", !pojo.getList()
				.isEmpty());
		for (RecursiveList listValue : pojo.getList()) {
			Assert.assertNotNull("The pojo's list element cannot be null!",
					listValue);
		}

	}

	@Test
	public void testCircularDependencyMap() {

		RecursiveMap pojo = factory.manufacturePojo(RecursiveMap.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		Assert.assertNotNull("The pojo's map cannot be null!", pojo.getMap());
		Assert.assertTrue("The pojo's map cannot be empty!", !pojo.getMap()
				.isEmpty());
		for (RecursiveMap mapValue : pojo.getMap().values()) {
			Assert.assertNotNull("The pojo's map element cannot be null!",
					mapValue);
		}

	}

	@Test
	public void testJREPojoWithDependencyLoopInConstructor() {

		URL pojo = factory.manufacturePojo(URL.class);
		Assert.assertNull("Default strategy cannot create java.net.URL object",
				pojo);

	}

	@Test
	public void testJREPojoWithDependencyLoopInConstructor2() {

		DataHandler pojo2 = factory.manufacturePojo(DataHandler.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo2);

	}

	@Test
	public void testImmutableNoHierarchicalAnnotatedPojo() {

		ImmutableNoHierarchicalAnnotatedPojo pojo = factory
				.manufacturePojo(ImmutableNoHierarchicalAnnotatedPojo.class);
		Assert.assertNotNull("The Immutable Simple Pojo cannot be null!", pojo);
		int intField = pojo.getIntField();
		Assert.assertTrue("The int field cannot be zero", intField != 0);
		Calendar dateCreated = pojo.getDateCreated();
		Assert.assertNotNull(
				"The Date Created Calendar object cannot be null!", dateCreated);
		Assert.assertNotNull(
				"The Date object within the dateCreated Calendar object cannot be null!",
				dateCreated.getTime());
		long[] longArray = pojo.getLongArray();
		Assert.assertNotNull("The array of longs cannot be null!", longArray);
		Assert.assertTrue("The array of longs cannot be empty!",
				longArray.length > 0);
		long longElement = longArray[0];
		Assert.assertTrue(
				"The long element within the long array cannot be zero!",
				longElement != 0);

	}

	@Test
	public void testImmutableNonAnnotatedPojo() {

		ImmutableNonAnnotatedPojo pojo = factory
				.manufacturePojo(ImmutableNonAnnotatedPojo.class);
		Assert.assertNotNull(
				"The immutable non annotated POJO should not be null!", pojo);

		Assert.assertNotNull("The date created cannot be null!",
				pojo.getDateCreated());

		Assert.assertTrue("The int field cannot be zero!",
				pojo.getIntField() != 0);

		long[] longArray = pojo.getLongArray();
		Assert.assertNotNull("The array of longs cannot be null!", longArray);
		Assert.assertEquals("The array of longs must have 1 element!",
				strategy.getNumberOfCollectionElements(Long.class),
				longArray.length);

	}

	@Test
	public void testPojoWithSelfReferencesInConstructor() {

		ConstructorWithSelfReferencesPojo pojo = factory
				.manufacturePojo(ConstructorWithSelfReferencesPojo.class);
		Assert.assertNotNull("The POJO cannot be null!", pojo);
		Assert.assertNotNull("The first self-reference cannot be null!",
				pojo.getParent());
		Assert.assertNotNull("The second self-reference cannot be null!",
				pojo.getAnotherParent());

	}

	@Test
	public void testPojoWithSelfReferenceInConstructorButNoDefaultConstructor() {

		ConstructorWithSelfReferencesButNoDefaultConstructorPojo pojo = factory
				.manufacturePojo(ConstructorWithSelfReferencesButNoDefaultConstructorPojo.class);
		Assert.assertNotNull("The POJO cannot be null!", pojo);
		Assert.assertNotNull("The first self-reference cannot be null!",
				pojo.getParent());
		Assert.assertNotNull("The second self-reference cannot be null!",
				pojo.getAnotherParent());

	}

	@Test
	public void testInheritance() {

		OneDimensionalChildPojo pojo = factory
				.manufacturePojo(OneDimensionalChildPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		int parentIntField = pojo.getParentIntField();
		Assert.assertTrue("The super int field must be <= 10",
				parentIntField <= 10);
		Calendar parentCalendarField = pojo.getParentCalendarField();
		checkCalendarIsValid(parentCalendarField);
		int intField = pojo.getIntField();
		Assert.assertTrue("The int field must be different from zero!",
				intField != 0);
		String strField = pojo.getStrField();
		Assert.assertNotNull("The string field cannot be null!", strField);
		Assert.assertTrue("The String field cannot be empty",
				strField.length() != 0);

	}

	@Test
	public void testCollectionsPojo() {

		CollectionsPojo pojo = factory.manufacturePojo(CollectionsPojo.class);
		Assert.assertNotNull("The POJO cannot be null!", pojo);
		List<String> strList = pojo.getStrList();
		validateReturnedList(strList);
		ArrayList<String> arrayListStr = pojo.getArrayListStr();
		validateReturnedList(arrayListStr);
		List<String> copyOnWriteList = pojo.getCopyOnWriteList();
		validateReturnedList(copyOnWriteList);
		HashSet<String> hashSetStr = pojo.getHashSetStr();
		validateReturnedSet(hashSetStr);
		List<String> listStrCollection = new ArrayList<String>(
				pojo.getStrCollection());
		validateReturnedList(listStrCollection);
		Set<String> setStrCollection = new HashSet<String>(
				pojo.getStrCollection());
		validateReturnedSet(setStrCollection);
		Set<String> strSet = pojo.getStrSet();
		validateReturnedSet(strSet);
		Map<String, OneDimensionalTestPojo> map = pojo.getMap();
		validateHashMap(map);
		HashMap<String, OneDimensionalTestPojo> hashMap = pojo.getHashMap();
		validateHashMap(hashMap);
		ConcurrentMap<String, OneDimensionalTestPojo> concurrentHashMap = pojo
				.getConcurrentHashMap();
		validateConcurrentHashMap(concurrentHashMap);
		ConcurrentHashMap<String, OneDimensionalTestPojo> concurrentHashMapImpl = pojo
				.getConcurrentHashMapImpl();
		validateConcurrentHashMap(concurrentHashMapImpl);
		Queue<SimplePojoToTestSetters> queue = pojo.getQueue();
		Assert.assertNotNull("The queue cannot be null!", queue);
		Assert.assertTrue("The queue must be an instance of LinkedList",
				queue instanceof LinkedList);
		SimplePojoToTestSetters pojoQueueElement = queue.poll();
		Assert.assertNotNull("The queue element cannot be null!",
				pojoQueueElement);
		@SuppressWarnings("rawtypes")
		List nonGenerifiedList = pojo.getNonGenerifiedList();
		Assert.assertNotNull("The non generified list cannot be null!",
				nonGenerifiedList);
		Assert.assertFalse("The non-generified list cannot be empty!",
				nonGenerifiedList.isEmpty());

		Map<?,?> nonGenerifiedMap = pojo.getNonGenerifiedMap();
		Assert.assertNotNull("The non generified map cannot be null!",
				nonGenerifiedMap);
		Assert.assertFalse("The non generified Map cannot be empty!",
				nonGenerifiedMap.isEmpty());
		Object object = nonGenerifiedMap.get(nonGenerifiedMap.keySet()
				.iterator().next());
		Assert.assertNotNull(
				"The object element within the Map cannot be null!", object);

	}

	@Test
	public void testPojoWithNoSettersAndCollectionInConstructor() {

		NoSetterWithCollectionInConstructorPojo pojo = factory
				.manufacturePojo(NoSetterWithCollectionInConstructorPojo.class);
		Assert.assertNotNull("The POJO cannot be null!", pojo);
		List<String> strList = pojo.getStrList();
		Assert.assertNotNull(
				"The collection of Strings in the constructor cannot be null!",
				strList);
		Assert.assertFalse(
				"The collection of Strings in the constructor cannot be empty!",
				strList.isEmpty());
		String strElement = strList.get(0);
		Assert.assertNotNull("The collection element cannot be null!",
				strElement);

		int intField = pojo.getIntField();
		Assert.assertTrue(
				"The int field in the constructor must be different from zero",
				intField != 0);

	}

	@Test
	public void testImmutablePojoWithNonGenericCollections() {

		ImmutableWithNonGenericCollectionsPojo pojo = factory
				.manufacturePojo(ImmutableWithNonGenericCollectionsPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		@SuppressWarnings("unchecked")
		Collection<Object> nonGenerifiedCollection = pojo
		.getNonGenerifiedCollection();
		Assert.assertNotNull("The non-generified collection cannot be null!",
				nonGenerifiedCollection);
		Assert.assertFalse("The non-generified collection cannot be empty!",
				nonGenerifiedCollection.isEmpty());
		Assert.assertTrue(
				"The number of elements in the collection: "
						+ nonGenerifiedCollection.size()
						+ " does not match the expected value: "
						+ ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS,
						nonGenerifiedCollection.size() == ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS);

		@SuppressWarnings("unchecked")
		Set<Object> nonGenerifiedSet = pojo.getNonGenerifiedSet();
		Assert.assertNotNull("The non-generified Set cannot be null!",
				nonGenerifiedSet);
		Assert.assertFalse("The non-generified Set cannot be empty!",
				nonGenerifiedSet.isEmpty());
		Assert.assertTrue(
				"The number of elements in the Set: " + nonGenerifiedSet.size()
				+ " does not match the expected value: "
				+ ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS,
				nonGenerifiedSet.size() == ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS);

		@SuppressWarnings("unchecked")
		Map<Object, Object> nonGenerifiedMap = pojo.getNonGenerifiedMap();
		Assert.assertNotNull("The non-generified map cannot be null!",
				nonGenerifiedMap);
		Assert.assertFalse("The non generified map cannot be empty!",
				nonGenerifiedMap.isEmpty());
		Assert.assertTrue(
				"The number of elements in the map: " + nonGenerifiedMap.size()
				+ " does not match the expected value: "
				+ ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS,
				nonGenerifiedMap.size() == ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS);

	}

	@Test
	public void testImmutablePojoWithGenerifiedCollectionsInConstructor() {

		strategy.setMemoization(false);

		ImmutableWithGenericCollectionsPojo pojo = factory
				.manufacturePojo(ImmutableWithGenericCollectionsPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		Collection<OneDimensionalTestPojo> generifiedCollection = pojo
				.getGenerifiedCollection();
		Assert.assertNotNull("The generified collection cannot be null!",
				generifiedCollection);
		Assert.assertFalse("The generified collection cannot be empty!",
				generifiedCollection.isEmpty());
		Assert.assertTrue(
				"The number of elements in the generified collection: "
						+ generifiedCollection.size()
						+ " does not match the expected value: "
						+ ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS,
						generifiedCollection.size() == ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS);

		Map<String, Calendar> generifiedMap = pojo.getGenerifiedMap();
		Assert.assertNotNull("The generified map cannot be null!",
				generifiedMap);
		Assert.assertFalse("The generified map cannot be empty!",
				generifiedMap.isEmpty());
		Assert.assertTrue(
				"The number of elements in the generified map: "
						+ generifiedMap.size()
						+ " does not match the expected value: "
						+ ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS,
						generifiedMap.size() == ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS);

		Set<ImmutableWithNonGenericCollectionsPojo> generifiedSet = pojo
				.getGenerifiedSet();
		Assert.assertNotNull("The generified set cannot be null!",
				generifiedSet);
		Assert.assertFalse("The generified set cannot be empty!",
				generifiedSet.isEmpty());
		Assert.assertTrue(
				"The number of elements in the generified set: "
						+ generifiedSet.size()
						+ " does not match the expected value: "
						+ ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS,
						generifiedSet.size() == ImmutableWithNonGenericCollectionsPojo.NBR_ELEMENTS);

	}

	@Test
	public void testSingletonWithParametersInPublicStaticMethod() {

		SingletonWithParametersInStaticFactoryPojo pojo = factory
				.manufacturePojo(SingletonWithParametersInStaticFactoryPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		Assert.assertNotNull("The calendar object cannot be null!",
				pojo.getCreateDate());

		Assert.assertNotNull("The first name cannot be null!",
				pojo.getFirstName());

		List<OneDimensionalTestPojo> pojoList = pojo.getPojoList();
		Assert.assertNotNull("The pojo list cannot be null!", pojoList);
		Assert.assertFalse("The pojo list cannot be empty", pojoList.isEmpty());

		Map<String, OneDimensionalTestPojo> pojoMap = pojo.getPojoMap();
		Assert.assertNotNull("The pojo map cannot be null!", pojoMap);
		Assert.assertFalse("The pojo map cannot be empty!", pojoMap.isEmpty());

	}

	@Test
	public void testPojoWithEnums() {

		EnumsPojo pojo = factory.manufacturePojo(EnumsPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		ExternalRatePodamEnum ratePodamExternal = pojo.getRatePodamExternal();
		Assert.assertNotNull("The external enum attribute cannot be null!",
				ratePodamExternal);

		RatePodamInternal ratePodamInternal = pojo.getRatePodamInternal();

		// Can't test for equality since internal enum is not visible
		Assert.assertNotNull("The internal enum cannot be null!",
				ratePodamInternal);

	}

	@Test
	public void testEnumPojo() {

		ExternalRatePodamEnum pojo = factory
				.manufacturePojo(ExternalRatePodamEnum.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
	}

	@Test
	public void testPrivateOnlyConstructorPojo() {

		PrivateOnlyConstructorPojo pojo = factory
				.manufacturePojo(PrivateOnlyConstructorPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		Assert.assertNotNull("The string attribute in pojo cannot be null!",
				pojo.getFirstName());
		Assert.assertTrue("The int field in pojo cannot be zero!",
				pojo.getIntField() != 0);

	}

	@Test
	public void testNoDefaultPublicConstructorPojo() {

		NoDefaultPublicConstructorPojo pojo = factory
				.manufacturePojo(NoDefaultPublicConstructorPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		Assert.assertNotNull("The string field cannot be null!",
				pojo.getFirstName());
		Assert.assertTrue("The int field cannot be zero!",
				pojo.getIntField() != 0);

	}

	@Test
	public void testProtectedNonDefaultConstructorPojo() {
		ProtectedNonDefaultConstructorPojo pojo = factory
				.manufacturePojo(ProtectedNonDefaultConstructorPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		Assert.assertNotNull("The string attribute cannot be null!",
				pojo.getFirstName());
		Assert.assertTrue("The int field cannot be zero!",
				pojo.getIntField() != 0);
	}

	@Test
	public void testSomeJavaNativeClasses() {
		String pojo = factory.manufacturePojo(String.class);
		Assert.assertNotNull("The generated String object cannot be null!",
				pojo);

		Integer integerPojo = factory.manufacturePojo(Integer.class);
		Assert.assertNotNull("The integer pojo cannot be null!", integerPojo);

		Calendar calendarPojo = factory
				.manufacturePojo(GregorianCalendar.class);
		Assert.assertNotNull("The calendar pojo cannot be null", calendarPojo);

		Date datePojo = factory.manufacturePojo(Date.class);
		Assert.assertNotNull("The date pojo cannot be null!", datePojo);

		BigDecimal bigDecimalPojo = factory.manufacturePojo(BigDecimal.class);
		Assert.assertNotNull("The Big decimal pojo cannot be null!",
				bigDecimalPojo);

	}

	// -----------------------------> Private methods

	/**
	 * It checks that the Calendar instance is valid
	 * <p>
	 * If the calendar returns a valid date then it's a valid instance
	 * </p>
	 *
	 * @param calendarField
	 *            The calendar instance to check
	 */
	private void checkCalendarIsValid(Calendar calendarField) {
		Assert.assertNotNull("The Calendar field cannot be null", calendarField);
		Date calendarDate = calendarField.getTime();
		Assert.assertNotNull("It appears the Calendar field is not valid",
				calendarDate);
	}

	/**
	 * It validates that the returned list contains the expected values
	 *
	 * @param list
	 *            The list to verify
	 */
	private void validateReturnedList(List<String> list) {
		Assert.assertNotNull("The List<String> should not be null!", list);
		Assert.assertFalse("The List<String> cannot be empty!", list.isEmpty());
		String element = list.get(0);
		Assert.assertNotNull(
				"The List<String> must have a non-null String element", element);
	}

	/**
	 * It validates that the returned list contains the expected values
	 *
	 * @param set
	 *            The set to verify
	 */
	private void validateReturnedSet(Set<String> set) {
		Assert.assertNotNull("The Set<String> should not be null!", set);
		Assert.assertFalse("The Set<String> cannot be empty!", set.isEmpty());
		String element = set.iterator().next();
		Assert.assertNotNull(
				"The Set<String> must have a non-null String element", element);
	}

	/**
	 * It validates the {@link HashMap} returned by Podam
	 *
	 * @param map
	 *            the map to be validated
	 */
	private void validateHashMap(Map<String, OneDimensionalTestPojo> map) {

		Assert.assertTrue("The map attribute must be of type HashMap",
				map instanceof HashMap);
		Assert.assertNotNull("The map object in the POJO cannot be null", map);
		Set<String> keySet = map.keySet();
		Assert.assertNotNull("The Map must have at least one element", keySet);

		validateMapElement(map, keySet);
	}

	/**
	 * It validates the concurrent hash map returned by podam
	 *
	 * @param map
	 */
	private void validateConcurrentHashMap(
			ConcurrentMap<String, OneDimensionalTestPojo> map) {

		Assert.assertTrue(
				"The map attribute must be of type ConcurrentHashMap",
				map instanceof ConcurrentHashMap);
		Assert.assertNotNull("The map object in the POJO cannot be null", map);
		Set<String> keySet = map.keySet();
		Assert.assertNotNull("The Map must have at least one element", keySet);

		validateMapElement(map, keySet);
	}

	/**
	 * It validates a map element
	 *
	 * @param map
	 *            The Map to validate
	 * @param keySet
	 *            The Set of keys in the map
	 */
	private void validateMapElement(Map<String, OneDimensionalTestPojo> map,
			Set<String> keySet) {
		OneDimensionalTestPojo oneDimensionalTestPojo = map.get(keySet
				.iterator().next());

		Assert.assertNotNull("The map element must not be null!",
				oneDimensionalTestPojo);
	}
}
