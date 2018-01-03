package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import exception.DAOException;
import model.Company;

public class CompanyDaoImplementation implements CompanyDao {

	private DAOFactory daoFactory;
	private static final String SQL_SELECT = "SELECT * FROM company";
	
	public CompanyDaoImplementation(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void findAll() throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = (Connection) daoFactory.getConnection();
	        preparedStatement = DaoUtilitary.initializePreparedRequest( connection, SQL_SELECT, true );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	String companyName = resultSet.getString("name");
	        	System.out.println(companyName);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        //fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}
	
	@Override
	public void create(Company company) throws DAOException {
	}
	
	@Override
	public Company find(String name) throws DAOException {
		return null;
	}
	
	private static Company map( ResultSet resultSet ) throws SQLException {
	    Company company = new Company();
	    company.setId( resultSet.getLong( "id" ) );
	    company.setName( resultSet.getString( "name" ) );
	    return company;

	}

}
