package foundly.database.daoImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import foundly.core.model.Item;
import foundly.database.dao.ItemDao;
import foundly.ui.App;
import foundly.ui.controller.ItemController;

public class ItemDaoImpl extends GenericDaoImpl<Item> implements ItemDao{

	public ItemDaoImpl() {
		super("items");
	}
		
}
