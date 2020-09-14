package foundly.core.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Model {
	
	public abstract void setId(Integer id);
	public abstract Integer getId();
	
	public abstract void setName(String name);
	public abstract String getName();
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DBTable {
		public String columnName();

		public String func();
	}
}
