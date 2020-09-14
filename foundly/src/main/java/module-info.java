module foundly {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires mysql.connector.java;
	requires commons.dbcp2;
	requires javafx.base;
	requires javafx.graphics;
	requires java.sql.rowset;
	requires java.management;
	requires java.desktop;

	exports foundly.database;
	exports foundly.core.containers;
	exports foundly.ui;
	
	opens foundly.core.containers to javafx.base;
    opens foundly.ui.controller to javafx.fxml;
    
}