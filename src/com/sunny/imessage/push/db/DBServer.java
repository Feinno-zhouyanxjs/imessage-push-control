/**
 * 
 */
package com.sunny.imessage.push.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pzoom.database.DBConnectionManager;
import com.pzoom.database.Database;
import com.sunny.imessage.push.utils.StringUtils;

/**
 * 
 * 数据库启动
 * 
 * Create on Dec 21, 2013 8:05:01 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class DBServer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public void init() throws SQLException, ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection con = getConn();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
		HashSet<String> tabName = new HashSet<String>();
		while (res.next()) {
			tabName.add(res.getString("TABLE_NAME"));
		}
		res.close();
		con.close();

		Database db = getDatabase();
		logger.info(tabName.toString());
		if (!tabName.contains("Task")) {

		}
		if (!tabName.contains("Result")) {

		}
	}

	public Connection getConn() throws SQLException {
		String local = StringUtils.getLocation();
		logger.info("produc path = " + local);
		return DriverManager.getConnection("jdbc:derby:" + local + "data/data.bin;create=true");
	}

	public Database getDatabase() {
		return DBConnectionManager.getInstance().getDatabase(new MyConnectionProvider(this));
	}
}
