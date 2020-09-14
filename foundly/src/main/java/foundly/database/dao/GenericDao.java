package foundly.database.dao;

import java.util.List;

public interface GenericDao<V> {

	List<V> getAll();
	V getById(Integer id);
	void insert(V obj);
	void delete(V obj);
	void update(V obj);
	
}
