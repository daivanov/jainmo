package uk.co.jemos.podam.test.dto;

import com.google.common.collect.ImmutableList;

/**
 * POJO to test Guava ImmutableList type.
 *
 * @author daivanov
 *
 */
public class ImmutableListPojo<E> {
	private ImmutableList<E> value;

	public ImmutableList<E> getValue() {
		return value;
	}

	public void setValue(ImmutableList<E> value) {
		this.value = value;
	}
}
