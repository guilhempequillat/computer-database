package org.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class DaoUtilitary {
	
	private static Logger logger = (Logger) LoggerFactory.getLogger("DaoUtilitary");
	private static DaoUtilitary daoUtilitary = new DaoUtilitary();
	
	public static DaoUtilitary getInstance() {
		return daoUtilitary;
	}
	
	/**
	 * Return a preparedStatement with the parameter which are in the Object... 
	 * 
	 * @param connection
	 * @param sql
	 * @param returnGeneratedKeys
	 * @param objects
	 * @return preparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement initializePreparedRequest(Connection connection, 
	    String sql, boolean returnGeneratedKeys, Object... objects)throws SQLException{
    	PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
    	for(int i=0 ; i < objects.length ; i++) {
    		preparedStatement.setObject(i + 1, objects[i]);
    	}
    	return preparedStatement;
    }
	
//	public PreparedStatement initializePreparedRequestFilter(Connection connection, 
//	    String sql, boolean returnGeneratedKeys, Object... objects)throws SQLException{
//		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
//    	for(int i=0 ; i < objects.length ; i++) {
//    		preparedStatement.setObject(i + 1, objects[i]);
//    	}
//    	return preparedStatement;
//	}
	
	public static void closeDaoConnection(Connection connection) {
		if ( connection != null ) {
	        try {
	        	connection.close();
	        } catch ( SQLException e ) {
	            logger.warn("Connection close failed");
	            logger.error(e.getMessage());
	        }
	    }
	}
	
	public static void closeDaoResultSet(ResultSet resultSet) {
		if ( resultSet != null ) {
			try {
				resultSet.close();
			} catch ( SQLException e ) {
				logger.warn("ResultSet close failed");
				logger.error(e.getMessage());
		    }
		}
	}
	
	public static void closeDaoPreparedStatement(PreparedStatement statement) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	        	logger.warn("PreparedStatement close failed");
	        	logger.error(e.getMessage());
	        }
	    }
	}
	
	public static void closeDao(PreparedStatement statement, Connection connection) {
		closeDaoPreparedStatement(statement);
		closeDaoConnection(connection);
	}
	
	public static void closeDao(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		closeDaoResultSet(resultSet);
		closeDaoPreparedStatement(statement);
		closeDaoConnection(connection);
	}
}
