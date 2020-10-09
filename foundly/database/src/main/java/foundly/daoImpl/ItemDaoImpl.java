package foundly.daoImpl;

import foundly.model.Item;
import foundly.dao.ItemDao;

/**
 * The Class ItemDaoImpl.
 * Handles database-operations for all Item-objects.
 * 
 * @apiNote respective table for Item is "items"
 */
public class ItemDaoImpl extends GenericDaoImpl<Item> implements ItemDao{

	/**
	 * Instantiates a new ItemDao
	 */
	public ItemDaoImpl() {
		super("items");
	}
		
}
