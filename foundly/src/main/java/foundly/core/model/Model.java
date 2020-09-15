package foundly.core.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Class Model.
 * Abstract class for all objects stored in database
 */
public abstract class Model {
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public abstract void setId(Integer id);
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public abstract Integer getId();
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public abstract void setName(String name);
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public abstract String getName();
	
	/**
	 * The Interface DBTable. Points each declaration in a Model-object to their respective
	 * column inside the database, along with a get-function in order to retrieve the
	 * value of the declaration.
	 * 
	 * For example:
	 * <p>
	 * 
	 * 	@DBTable(columnName="id", func="getId") </br>
	 * 	protected Integer id;</br>
	 *	</br>
	 *
	 * 	public Integer getId() {
	 *		return id;
	 *	}
	 * 
	 * </p>
	 */
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DBTable {
		
		/**
		 * Column name.
		 * @apiNote refers to the specific column in the database
		 * @return the string
		 */
		public String columnName();

		/**
		 * Func.
		 * @apiNote refers to the get-function used to retrieve value for this specific column
		 * @return the string
		 */
		public String func();
	}
}
