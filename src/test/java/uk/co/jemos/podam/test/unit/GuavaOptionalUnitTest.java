package uk.co.jemos.podam.test.unit;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import org.junit.Assert;
import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.OptionalPojo;
import uk.co.jemos.podam.test.dto.ImmutableListPojo;

/**
 * Test @uk.co.jemos.podam.test.dto.OptionalPojo@ construction
 *
 * @author daivanov
 *
 */
public class GuavaOptionalUnitTest {

	private final static PodamFactory podam = new PodamFactoryImpl();

	@Test
	public void testGuavaOptionalAsPojo() throws Exception {

		Optional<?> pojo = podam.manufacturePojo(Optional.class, Long.class);
		Assert.assertNotNull("Construction failed", pojo);
		Assert.assertEquals("Optional should hold a value",
				true, pojo.isPresent());
		Assert.assertNotNull("Value attr should hold an object", pojo.get());
		Assert.assertEquals("Invalid object type",
				Long.class, pojo.get().getClass());
	}

	@Test
	public void testGuavaOptionalFieldSetting() throws Exception {

		OptionalPojo<?> pojo = podam.manufacturePojo(OptionalPojo.class, String.class);
		Assert.assertNotNull("Construction failed", pojo);
		Assert.assertNotNull("Value attr should not be empty", pojo.getValue());
		Assert.assertEquals("Optional should hold a value",
				true, pojo.getValue().isPresent());
		Assert.assertNotNull("Value attr should hold an object", pojo.getValue().get());
		Assert.assertEquals("Invalid object type",
				String.class, pojo.getValue().get().getClass());
	}

	@Test
	public void testGuavaImmutableListAsPojo() throws Exception {

		ImmutableList<?> pojo = podam.manufacturePojo(ImmutableList.class, Long.class);
		Assert.assertNotNull("Construction failed", pojo);
		Assert.assertEquals("List should not be empty", false, pojo.isEmpty());
	}

	@Test
	public void testGuavaImmutableListFieldSetting() throws Exception {

		ImmutableListPojo<?> pojo = podam.manufacturePojo(ImmutableListPojo.class, String.class);
		Assert.assertNotNull("Construction failed", pojo);
		Assert.assertNotNull("Value attr should not be empty", pojo.getValue());
		Assert.assertEquals("List should not be empty",
				false, pojo.getValue().isEmpty());
	}

}
