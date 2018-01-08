package dao.daoImplementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dao.DaoFactory;
import dao.DaoUtilitary;
import dao.daoInterface.CompanyDao;
import exception.DAOException;
import model.Company;

public class CompanyDaoImplementation implements CompanyDao {

	private DaoFactory daoFactory;
	private static final String SQL_SELECT = "SELECT * FROM company";
	
	public CompanyDaoImplementation(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public ArrayList<Company> findAll() throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	    	connection = (Connection) daoFactory.getConnection();
	        preparedStatement = DaoUtilitary.initializePreparedRequest( connection, SQL_SELECT, true );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Company> companies = new ArrayList<>();
	        while (resultSet.next()) {
	        	Company company = new Company(resultSet.getString("name"));
	        	companies.add(company);
	        }
	        return companies;
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement,connection);
	    }
	}
}
