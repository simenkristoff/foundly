package foundly.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 * The Class ConnectionHandler. Manages all connections to the database-server in a pool of connections.
 */
public class ConnectionHandler {

	private static final String DB_USERNAME = "db.username";
	private static final String DB_PASSWORD = "db.password";
	private static final String DB_URL = "db.url";
	private static final String DB_DRIVER = "driver.class.name";
	
	private static Properties properties = null;
	private static BasicDataSource dataSource = new BasicDataSource();
	
	/**
	 * Self initializing method.
	 * Setup connection pool with configuration from file ("database.properties")
	 */
	static {
		try {
			properties = new Properties();	
			properties.load(ConnectionHandler.class.getResourceAsStream("database.properties"));

			dataSource.setDriverClassName(properties.getProperty(DB_DRIVER));
			dataSource.setUrl(properties.getProperty(DB_URL));
			dataSource.setUsername(properties.getProperty(DB_USERNAME));
			dataSource.setPassword(properties.getProperty(DB_PASSWORD));
			dataSource.setMaxIdle(3);
		    dataSource.setMaxWaitMillis(20000); //wait 10 seconds to get new connection
		    dataSource.setMaxTotal(6);
		    dataSource.setMinIdle(0);
		    dataSource.setInitialSize(3);
		    dataSource.setTestOnBorrow(true);
		    dataSource.setValidationQuery("select 1"); //TODO redo validation query
		    dataSource.setValidationQueryTimeout(10); //The value is in seconds
	
		    dataSource.setTimeBetweenEvictionRunsMillis(600000); // 10 minutes wait to run evictor process
		    dataSource.setSoftMinEvictableIdleTimeMillis(600000); // 10 minutes wait to run evictor process
		    dataSource.setMinEvictableIdleTimeMillis(60000); // 60 seconds to wait before idle connection is evicted
		    dataSource.setMaxConnLifetimeMillis(600000); // 10 minutes is max life time
		    dataSource.setNumTestsPerEvictionRun(10);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close connection pool.
	 */
	public static void closePool() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves an idle connection from the connection pool
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	@SuppressWarnings("exports")
	public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
	
	/**
	 * Instantiates a new connection handler.
	 */
	private ConnectionHandler() {}
}
