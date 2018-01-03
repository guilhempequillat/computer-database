package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import exception.DAOException;
import model.Computer;

public class ComputerDaoImplementation implements ComputerDao {

	private DAOFactory daoFactory;
	private static final String SQL_FIND_ALL = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private static final String SQL_FIND = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_UPDATE_NAME = "UPDATE computer SET name = ? WHERE id = ?";
	private static final String SQL_UPDATE_COMPANY = "UPDATE computer SET company_id = ? WHERE id = ?";
	private static final String SQL_UPDATE_INTRODUCED = "UPDATE computer SET introduced = ? WHERE id = ?";
	private static final String SQL_UPDATE_DISCONTINUED = "UPDATE computer SET discontinued = ? WHERE id = ?";
	
	public ComputerDaoImplementation(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public ArrayList<Computer> findAll() {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = (Connection) daoFactory.getConnection();
	        preparedStatement = DaoUtilitary.initializePreparedRequest( connection, SQL_FIND_ALL, true );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = new Computer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        //fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public Computer find(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Computer computer =null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { id };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_FIND, true, objects);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				computer = new Computer(resultSet);
			}
			return computer;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        //fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override 
	public void updateName(int id, String name) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Computer computer =null;
		try {
			connection = (Connection) daoFactory.getConnection();
			Object[] objects = { name , id };
			preparedStatement = DaoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_NAME, true, objects);
			int result = preparedStatement.executeUpdate();
			System.out.println(result);
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        //fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
}
