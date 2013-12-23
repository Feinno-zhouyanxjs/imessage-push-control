/**
 * 
 */
package com.sunny.imessage.push.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.pzoom.database.ConnectionProvider;

/**
 * 
 * 连接提供器
 * 
 * Create on Dec 21, 2013 10:19:25 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class MyConnectionProvider implements ConnectionProvider {

	private final DBServer db;

	/**
	 * @param db
	 */
	public MyConnectionProvider(DBServer db) {
		super();
		this.db = db;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pzoom.database.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return db.getConn();
	}

}
