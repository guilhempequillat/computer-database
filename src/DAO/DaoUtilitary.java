package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.LoggerFactory;
import com.mysql.jdbc.PreparedStatement;
import ch.qos.logback.classic.Logger;

public class DaoUtilitary {
	
	private static Logger logger = (Logger) LoggerFactory.getLogger("DaoUtilitary");
	
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
	public static PreparedStatement initializePreparedRequest(Connection connection, 
	    	String sql, boolean returnGeneratedKeys, Object... objects)throws SQLException{
    	PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
    	for(int i=0 ; i < objects.length ; i++) {
    		preparedStatement.setObject(i + 1, objects[i]);
    	}
    	return preparedStatement;
    }
	
	public static void closeDao(Connection connection) {
		if ( connection != null ) {
	        try {
	        	connection.close();
	        } catch ( SQLException e ) {
	            logger.warn("Connection close failed");
	        }
	    }
	}
	
	public static void closeDao(ResultSet resultSet) {
		if ( resultSet != null ) {
			try {
				resultSet.close();
			} catch ( SQLException e ) {
				logger.warn("ResultSet close failed");
		    }
		}
	}
	
	public static void closeDao(PreparedStatement statement) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	        	logger.warn("PreparedStatement close failed");
	        }
	    }
	}
	
	public static void closeDao(PreparedStatement statement, Connection connection) {
		closeDao(statement);
		closeDao(connection);
	}
	
	public static void closeDao(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		closeDao(resultSet);
		closeDao(statement);
		closeDao(connection);
	}
}
