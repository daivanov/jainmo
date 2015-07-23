/**
 * 
 */
package uk.co.jemos.podam.api;

import uk.co.jemos.podam.annotation.PodamCollection;
import uk.co.jemos.podam.common.AttributeStrategy;

/**
 * A default Object strategy, just to provide a default to
 * {@link PodamCollection#collectionElementStrategy()}.
 * 
 * @author mtedone
 * 
 */
public class ObjectStrategy implements AttributeStrategy<Object> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue() {
		return new Object();
	}

}
