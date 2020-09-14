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

public abstract class GenericDaoImpl<V extends Model> implements GenericDao<V> {
	
	protected Class<V> model;
	protected String table;
	protected String mapper;
	
	@SuppressWarnings("unchecked")
	public GenericDaoImpl(String table) {
		this.table = table;
		this.model = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public V getById(Integer id) {
		V value = null;
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
					value = (V) cons.newInstance(rs);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | SecurityException | NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
	
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
					V value = (V) cons.newInstance(rs);
					list.add(value);
				}	
			}
			conn.close();
		} catch (SQLException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void insert(V value) {
		LinkedHashMap<String, Object> map = mapper(value);
		ArrayList<String> index = new ArrayList<String>(map.keySet());
		ArrayList<Object> values = new ArrayList<Object>(map.values());
		String cols = index.toString();
		cols = cols.substring(1, cols.length() - 1);
		
		String params = parseParams(values);

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
				value.setId(id);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(V value) {
		LinkedHashMap<String, Object> map = mapper(value);
		ArrayList<String> index = new ArrayList<String>(map.keySet());
		ArrayList<Object> values = new ArrayList<Object>(map.values());
		
		String params = "";
		for(int i = 1; i < index.size(); i++) {
			if(i > 1) {
				params += ", " + index.get(i) + " = ?";
			} else {
				params += index.get(i) + " = ?";
			}
		}
		
		String query = "UPDATE " + table
				+ " SET " + params
				+ " WHERE " + table + ".id = " + value.getId();
		
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
	
	public void delete(V v) {
		Integer id = v.getId();
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
	
	private String parseParams(ArrayList values) {
		StringBuilder params = new StringBuilder();
		int i = 0;
		for(Object value : values) {
			if(i > 0) params.append(", ");
			try {
				params.append(value.toString().replaceAll("([^?!null].*$)", "?"));
			} catch(NullPointerException e) {
				params.append("null");
			}
			
			i++;
		}
		return params.toString();
	}
	
	private LinkedHashMap<String, Object> mapper(V value) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Class<?> model = value.getClass();
		for(Field field : model.getDeclaredFields()) {
			field.setAccessible(true);
			DBTable column = (field.getAnnotation(DBTable.class) != null) ? field.getAnnotation(DBTable.class) : null;
			if(column != null) {
				try {
					String col = column.columnName();
					/** Get values from model **/
					Object val = model.getMethod(column.func(), null).invoke(value, null);
					map.put(col, val);
				} catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}	
}
