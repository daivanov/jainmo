package uk.co.jemos.podam.test.dto;

import com.google.common.base.Optional;

/**
 * POJO to test Guava Optional type.
 *
 * @author daivanov
 *
 */
public class OptionalPojo<T> {
	private Optional<T> value;

	public Optional<T> getValue() {
		return value;
	}

	public void setValue(Optional<T> value) {
		this.value = value;
	}
}
