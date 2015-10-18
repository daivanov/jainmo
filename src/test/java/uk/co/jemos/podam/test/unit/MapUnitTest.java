package uk.co.jemos.podam.test.unit;

import org.junit.Assert;
import org.junit.Test;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.RandomDataProviderStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author divanov
 * Map of string and values, which cannot be created by PODAM.
 * PODAM will use nulls as values. However, not all Maps allow nulls as values.
 */
public class MapUnitTest {

	private static final DataProviderStrategy strategy =
			new RandomDataProviderStrategy();

	private static final PodamFactory factory = new PodamFactoryImpl(strategy);

	private void testMap(Class<?> mapType, int mapSize) {
		Map<?,?> pojo = (Map<?,?>)factory.manufacturePojo(mapType,
				String.class, TestInterface.class);
		Assert.assertNotNull("Construction failed", pojo);
		Assert.assertEquals("Wrong size of key set",
				mapSize, pojo.keySet().size());
		Assert.assertEquals("Wrong size of value set",
				mapSize, pojo.values().size());
	}
	
	@Test
	public void testSortedMapCreation() {

		testMap(TreeMap.class,
				strategy.getNumberOfCollectionElements(TestInterface.class));
	}

	@Test
	public void testConcurrentMapCreation() {

		testMap(ConcurrentHashMap.class, 0);
	}

	@Test
	public void testHashMapCreation() {

		testMap(HashMap.class,
				strategy.getNumberOfCollectionElements(TestInterface.class));
	}
}
