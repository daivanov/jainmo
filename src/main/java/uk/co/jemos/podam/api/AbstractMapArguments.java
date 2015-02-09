package uk.co.jemos.podam.api;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Parent for map-related arguments POJO
 * 
 * @author Marco Tedone
 * 
 */
public abstract class AbstractMapArguments {

	/** Set of manufactured pojos' types. */
	private Map<Class<?>, Integer> pojos;
	/** The annotations for the attribute. */
	private List<Annotation> annotations;

	/**
	 * @return the pojos
	 */
	public Map<Class<?>, Integer> getPojos() {
		return pojos;
	}

	/**
	 * @param pojos
	 *            the pojos to set
	 */
	public void setPojos(Map<Class<?>, Integer> pojos) {
		this.pojos = pojos;
	}

	/**
	 * @return the annotations
	 */
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations
	 *            the annotations to set
	 */
	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractMapArguments [pojos=");
		builder.append(pojos);
		builder.append(", annotations=");
		builder.append(annotations);
		builder.append("]");
		return builder.toString();
	}

}
