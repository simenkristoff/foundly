package foundly.database.daoImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import foundly.core.model.Model;
import foundly.core.model.Model.DBTable;
import foundly.database.ConnectionHandler;
import foundly.database.dao.GenericDao;

/**
 * The Class GenericDaoImpl.
 * Foundational class for database-management of all objects extending the class Model.
 * Handles basic database-functions such as SELECT (by id, and all), INSERT, UPDATE, REMOVE 
 *
 * @param <V> the object type
 * 
 * @apiNote V must extend {@link Model}
 */
public abstract class GenericDaoImpl<V extends Model> implements GenericDao<V> {
	
	protected Class<V> model;
	protected String table;
	protected String mapper;
	
	/**
	 * Instantiates a new instance of GenericDaoImpl.
	 * @param table the table in which the object V is stored in the database.
	 * <p>
	 * 	Table-reference can be found in the objects DaoImpl class </br>
	 * 	For example: </br>
	 * 	The table for {@link Item} can be found in the constructor of {@link ItemDaoImpl}
	 * </p>
	 * @see ItemDaoImpl
	 */
	@SuppressWarnings("unchecked")
	public GenericDaoImpl(String table) {
		this.table = table;
		this.model = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Gets the object V by it's id.
	 *
	 * @param id the id
	 * @return object with @param id
	 */
	public V getById(Integer id) {
		V object = null;
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + table
				+ " WHERE " + table + ".id = ? "
				+ "LIMIT 1"
			);
			stmt.setObject(1, id);
			if(stmt.execute() && stmt.getResultSet() != null) {
				ResultSet rs = stmt.getResultSet();
				rs.next();
				try {
					Constructor<V> cons = this.model.getDeclaredConstructor(ResultSet.class);
					object = (V) cons.newInstance(rs);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | SecurityException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * Gets all objects of type V.
	 *
	 * @return all objects of type V
	 */
	public List<V> getAll() {
		List<V> list = new ArrayList<V>();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM " + table
			);
			if(stmt.execute() && stmt.getResultSet() != null) {
				ResultSet rs = stmt.getResultSet();
				Constructor<V> cons = this.model.getDeclaredConstructor(ResultSet.class);
				while(rs.next()) {
					V object = (V) cons.newInstance(rs);
					list.add(object);
				}	
			}
			conn.close();
		} catch (SQLException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Insert object to database.
	 *
	 * @param object the object to be inserted
	 */
	public void insert(V object) {
		LinkedHashMap<String, Object> map = mapper(object);
		ArrayList<String> index = new ArrayList<String>(map.keySet());
		ArrayList<Object> values = new ArrayList<Object>(map.values());
		String cols = index.toString();
		cols = cols.substring(1, cols.length() - 1);
		
		String params = parseInsertParameters(values);

		String query = "INSERT INTO " + table + " ("
				+ cols + ") VALUES (" + params +")";
		try {
			Connection conn = ConnectionHandler.getConnection();			
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ParameterMetaData pmd = stmt.getParameterMetaData();
			for(int i = 1; i <= pmd.getParameterCount(); i++) {
				stmt.setObject(i, values.get(i));
			}
			if(stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				Integer id = rs.getInt(1);
				object.setId(id);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Update object.
	 *
	 * @param object the object to be updated
	 */
	public void update(V object) {
		LinkedHashMap<String, Object> map = mapper(object);
		ArrayList<String> index = new ArrayList<String>(map.keySet());
		ArrayList<Object> values = new ArrayList<Object>(map.values());
		
		String params = parseUpdateParameters(index, values);
		
		String query = "UPDATE " + table
				+ " SET " + params
				+ " WHERE " + table + ".id = " + object.getId();
		
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			ParameterMetaData pmd = stmt.getParameterMetaData();
			for(int i = 1; i <= pmd.getParameterCount(); i++) {
				stmt.setObject(i, values.get(i));
			}
			stmt.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete object from database.
	 *
	 * @param object the object to be removed
	 */
	public void delete(V object) {
		Integer id = object.getId();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + table + " WHERE "
					+ table + ".id = ?;"
			);
			stmt.setInt(1, (int) id);
			stmt.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the insert parameters.
	 *
	 * @param values the values to be inserted
	 * @return the insert parameters
	 */
	protected String parseInsertParameters(ArrayList<Object> values) {
		StringBuilder params = new StringBuilder();
		String delimiter = "";
		for(Object object : values) {
			try {
				params.append(delimiter + object.toString().replaceAll("(\\b[\\S\\s]+\\b)", "?")); // old regex: ([^?!null].*$)
			} catch(NullPointerException e) {
				params.append(delimiter + "null");
			}
			delimiter = ", ";
		}
		return params.toString();
	}
	
	/**
	 * Parses the update parameters.
	 *
	 * @param index name of the columns inside database-table
	 * @param values the values belonging to each column
	 * @return the update parameters
	 */
	protected String parseUpdateParameters(ArrayList<String> index, ArrayList<Object> values) {
		StringBuilder params = new StringBuilder();
		int k = (index.get(0).equals("id") ? 1 : 0);
		String delimiter = "";
		for(int i = k; i < index.size(); i++) {
			try {
				params.append(delimiter + index.get(i) + " = ");
				params.append(values.get(i).toString().replaceAll("(\\b[\\S\\s]+\\b)", "?")); // old regex: ([^?!null].*$)
			} catch(NullPointerException e) {
				params.append("null");
			}
			delimiter = ", ";
		}
		return params.toString();
	}
	
	/**
	 * Maps the declared fields in the Model-class along with their values
	 *
	 * @param object the object to be mapped
	 * @return the linked hash map with "column" as KEY, and "value" as VALUE
	 */
	protected LinkedHashMap<String, Object> mapper(V object) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Class<?> model = object.getClass();
		for(Field field : model.getDeclaredFields()) {
			field.setAccessible(true);
			DBTable column = (field.getAnnotation(DBTable.class) != null) ? field.getAnnotation(DBTable.class) : null;
			if(column != null) {
				try {
					String col = column.columnName();
					// Get values from the model
					Object val = model.getMethod(column.func(), null).invoke(object, null);
					map.put(col, val);
				} catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}	
}
