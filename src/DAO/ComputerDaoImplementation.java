package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import ch.qos.logback.classic.Logger;
import exception.DAOException;
import model.Computer;

public class ComputerDaoImplementation implements ComputerDao {


	private static final String SQL_FIND_ALL            = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private static final String SQL_FIND                = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_UPDATE_NAME         = "UPDATE computer SET name = ? WHERE id = ?";
	private static final String SQL_UPDATE_COMPANY      = "UPDATE computer SET company_id = ? WHERE id = ?";
	private static final String SQL_UPDATE_INTRODUCED   = "UPDATE computer SET introduced = ? WHERE id = ?";
	private static final String SQL_UPDATE_DISCONTINUED = "DELETE computer SET discontinued = ? WHERE id = ?";
	private static final String SQL_CREATE              = "INSERT INTO computer ( name , discontinued ,introduced , company_id ) VALUES (?,?,?,?) ";
	private static final String SQL_DELETE              = "DELETE FROM computer WHERE id = ?";
	private DAOFactory daoFactory;
	private static Logger logger = (Logger) LoggerFactory.getLogger("ComputerDao");
	
	public ComputerDaoImplementation(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public ArrayList<Computer> findAll() {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	    	connection = (Connection) daoFactory.getConnection();
	        preparedStatement = DaoUtilitary.initializePreparedRequest( connection, SQL_FIND_ALL, true );
	        resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = new Computer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeDao(resultSet,preparedStatement, connection);
	    }
	}
	
	@Override
	public Computer find(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Computer computer =null;
		ResultSet resultSet = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { id };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_FIND, true, objects);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				computer = new Computer(resultSet);
			}
			return computer;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(resultSet,preparedStatement, connection);
	    }
	}
	
	@Override 
	public void updateName(int id, String name) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { name , id };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_NAME, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void updateIntroduced(int id, Date introduced) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { introduced , id };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_INTRODUCED, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void updateDiscontinued(int id, Date discontinued) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { discontinued , id };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_DISCONTINUED, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void updateCompany(int idComputer, int idCompany) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { idCompany , idComputer };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_COMPANY, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void create(String name, Date introduced, Date discontinued , int idCompany) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { name, discontinued, introduced, idCompany };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_CREATE, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void delete(int idComputer) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { idComputer };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_DELETE, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeDao(preparedStatement, connection);
	    }
	}
	
	public void closeDao(Connection connection) {
		if ( connection != null ) {
	        try {
	        	connection.close();
	        } catch ( SQLException e ) {
	            logger.warn("Connection close failed");
	        }
	    }
	}
	
	public void closeDao(ResultSet resultSet) {
		if ( resultSet != null ) {
			try {
				resultSet.close();
			} catch ( SQLException e ) {
				logger.warn("ResultSet close failed");
		    }
		}
	}
	
	public void closeDao(PreparedStatement statement) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	        	logger.warn("PreparedStatement close failed");
	        }
	    }
	}
	
	public void closeDao(PreparedStatement statement, Connection connection) {
		closeDao(statement);
		closeDao(connection);
	}
	
	public void closeDao(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		closeDao(resultSet);
		closeDao(statement);
		closeDao(connection);
	}
}
