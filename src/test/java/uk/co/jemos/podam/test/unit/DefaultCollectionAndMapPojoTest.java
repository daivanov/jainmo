package uk.co.jemos.podam.test.unit;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.PojoWithCollectionAndMap;
import uk.co.jemos.podam.test.utils.TestUtils;

public class DefaultCollectionAndMapPojoTest {

	@Test
	public void testPojoAttributeImplementingListAndMapWithDefaultValues() {

		PodamFactory factory = new PodamFactoryImpl();
		PojoWithCollectionAndMap pojo = factory.manufacturePojo(PojoWithCollectionAndMap.class);
		assertNotNull(pojo);
		assertNotNull(pojo.getList());
		TestUtils.assertCollectionElementsType(pojo.getList(), String.class);
		assertNotNull(pojo.getMap());
		TestUtils.assertMapElementsType(pojo.getMap(), Integer.class, String.class);
		assertNotNull(pojo.getList2());
		TestUtils.assertCollectionElementsType(pojo.getList2(), String.class);
		assertNotNull(pojo.getMap2());
		TestUtils.assertMapElementsType(pojo.getMap2(), Integer.class, String.class);
		assertNotNull(pojo.getList3());
		TestUtils.assertCollectionElementsType(pojo.getList3(), String.class);
		assertNotNull(pojo.getMap3());
		TestUtils.assertMapElementsType(pojo.getMap3(), Integer.class, String.class);
	}

}
