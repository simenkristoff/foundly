package foundly.database.daoImpl;

import foundly.core.model.Item;
import foundly.database.dao.ItemDao;

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
