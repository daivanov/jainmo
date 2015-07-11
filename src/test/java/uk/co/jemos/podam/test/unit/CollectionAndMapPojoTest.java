package uk.co.jemos.podam.test.unit;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.PojoWithCollectionAndMap;
import uk.co.jemos.podam.test.utils.PodamTestUtils;

public class CollectionAndMapPojoTest {

	@Test
	public void testPojoAttributeImplementingListAndMapWithDefaultValues() {

		PodamFactory factory = new PodamFactoryImpl();
		PojoWithCollectionAndMap pojo = factory.manufacturePojo(PojoWithCollectionAndMap.class);
		assertNotNull(pojo);
		assertNotNull(pojo.getList());
		PodamTestUtils.assertCollectionElementsType(pojo.getList(), String.class);
		assertNotNull(pojo.getMap());
		PodamTestUtils.assertMapElementsType(pojo.getMap(), Integer.class, String.class);
		assertNotNull(pojo.getList2());
		PodamTestUtils.assertCollectionElementsType(pojo.getList2(), String.class);
		assertNotNull(pojo.getMap2());
		PodamTestUtils.assertMapElementsType(pojo.getMap2(), Integer.class, String.class);
		assertNotNull(pojo.getList3());
		PodamTestUtils.assertCollectionElementsType(pojo.getList3(), String.class);
		assertNotNull(pojo.getMap3());
		PodamTestUtils.assertMapElementsType(pojo.getMap3(), Integer.class, String.class);
	}

}
