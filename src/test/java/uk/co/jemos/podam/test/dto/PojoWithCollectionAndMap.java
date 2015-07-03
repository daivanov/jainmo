/**
 * 
 */
package uk.co.jemos.podam.test.dto;

/**
 * Test pojo
 * <p>
 * Pojo with read-only map and collection
 * </p>
 * 
 * @author daivanov
 * 
 */
public class PojoWithCollectionAndMap {

	private CollectionImplementingGenericsInterface list = new CollectionImplementingGenericsInterface();

	private MapImplementingGenericInterface map = new MapImplementingGenericInterface();

	public CollectionImplementingGenericsInterface getList() {
		return list;
	}

	public void setList(CollectionImplementingGenericsInterface list) {
		this.list = list;
	}

	public MapImplementingGenericInterface getMap() {
		return map;
	}

	public void setMap(MapImplementingGenericInterface map) {
		this.map = map;
	}

}
