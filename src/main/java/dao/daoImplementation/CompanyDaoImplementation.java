package dao.daoImplementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import java.sql.Connection;

import ch.qos.logback.classic.Logger;

import java.sql.PreparedStatement;
import dao.DaoFactory;
import dao.DaoUtilitary;
import dao.daoInterface.CompanyDao;
import exception.DAOException;
import mapper.CompanyMapper;
import model.Company;

public class CompanyDaoImplementation implements CompanyDao {

	private DaoFactory daoFactory;
	private DaoUtilitary daoUtilitary= DaoUtilitary.getInstance();
	private static final String SQL_SELECT = "SELECT * FROM company";
	private CompanyMapper companyMapper = CompanyMapper.getCompanyMapper();
	private Logger logger = (Logger) LoggerFactory.getLogger("CompanyDaoImplementation");
	
	public CompanyDaoImplementation(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public ArrayList<Company> findAll() throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    try {
	    	connection = (Connection) getDaoFactoryConnection();
	        preparedStatement = getPreparedStatement(connection);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Company> companies = new ArrayList<>();
	        while (resultSet.next()) {
	        	Company company = companyMapper.mapCompany(resultSet);
	        	companies.add(company);
	        }
	        return companies;
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement,connection);
	    }
	}
	
	public PreparedStatement getPreparedStatement(Connection connection) {
		try {
			return daoUtilitary.initializePreparedRequest( connection, SQL_SELECT, true );
		} catch (SQLException e) {
			logger.error("The connection can't be reach");
			e.printStackTrace();
		}
		return null;
	}
	
	public Connection getDaoFactoryConnection() {
		try {
			return (Connection) daoFactory.getConnection();
		} catch (SQLException e) {
			logger.error("The connection can't be reach");
			e.printStackTrace();
		};
		return null;
	}
}
