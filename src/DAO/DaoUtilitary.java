package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class DaoUtilitary {
	
	
	public static PreparedStatement initializePreparedRequest(Connection connection, 
	    	String sql, boolean returnGeneratedKeys, Object... objects)throws SQLException{
	    	PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
	    	for(int i=0 ; i < objects.length ; i++) {
	    		preparedStatement.setObject(i + 1, objects[i]);
	    	}
	    	return preparedStatement;
	    }
}
