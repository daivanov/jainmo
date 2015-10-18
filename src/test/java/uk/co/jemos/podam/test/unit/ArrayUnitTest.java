package uk.co.jemos.podam.test.unit;

import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.ArrayPojo;
import uk.co.jemos.podam.test.utils.TestUtils;

public class ArrayUnitTest {

	@Test
	public void testArrayCreation() throws Exception {

		PodamFactory podam = new PodamFactoryImpl();
		ArrayPojo pojo = podam.manufacturePojo(ArrayPojo.class);

		TestUtils.assertArrayElementsType(pojo.getMyStringArray(), String.class);
		TestUtils.assertArrayElementsType(pojo.getMyObjectArray(), Object.class);
	}

}
