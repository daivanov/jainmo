package uk.co.jemos.podam.api;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Parent for map-related arguments POJO
 * 
 * @author Marco Tedone
 * 
 */
public abstract class AbstractMapArguments {

	/** The name of map attribute in enclosing POJO */
	private String attributeName;
	/** The Map to be returned. */
	private Map<Object, Object> mapToBeFilled;
	/** The annotations for the attribute. */
	private Annotation[] annotations;

	/**
	 * @return the attribute name for this map
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName
	 *            the attribute name for this map
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return the mapToBeFilled
	 */
	public Map<Object, Object> getMapToBeFilled() {
		return mapToBeFilled;
	}

	/**
	 * @param mapToBeFilled
	 *            the mapToBeFilled to set
	 */
	public void setMapToBeFilled(
			Map<Object, Object> mapToBeFilled) {
		this.mapToBeFilled = mapToBeFilled;
	}

	/**
	 * @return the annotations
	 */
	public Annotation[] getAnnotations() {
		return annotations;
	}

	/**
	 * @param annotations
	 *            the annotations to set
	 */
	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractMapArguments [mapToBeFilled=");
		builder.append(mapToBeFilled.getClass());
		builder.append(", annotations=");
		builder.append(annotations);
		builder.append("]");
		return builder.toString();
	}

}
