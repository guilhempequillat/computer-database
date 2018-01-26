package org.cdb.dao.daoImplementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.cdb.connectionPool.DataSourceConfigHikari;
import org.cdb.dao.DaoFactory;
import org.cdb.dao.DaoUtilitary;
import org.cdb.dao.daoInterface.CompanyDao;
import org.cdb.exception.DAOException;
import org.cdb.mapper.CompanyMapper;
import org.cdb.mapper.RowMapperCompany;
import org.cdb.model.Company;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;

import java.sql.Connection;

import ch.qos.logback.classic.Logger;

import java.sql.PreparedStatement;

@Repository
public class CompanyDaoImplementation implements CompanyDao {

//	private DaoFactory daoFactory;
//	private DaoUtilitary daoUtilitary= DaoUtilitary.getInstance();
	private static final String SQL_SELECT          = "SELECT id , name FROM company";
	private static final String SQL_CREATE          = "INSERT INTO company ( name ) VALUES (?) ";
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ? ";
	private static final String SQL_DELETE_COMPANY  = "DELETE FROM company WHERE  id = ? ";
	private CompanyMapper companyMapper = CompanyMapper.getCompanyMapper();
	private Logger logger = (Logger) LoggerFactory.getLogger("CompanyDaoImplementation");
//	private HikariDataSource hikariDataSource = DataSourceConfigHikari.getHikariDataSource();
	private RowMapperCompany rowMapperCompany = RowMapperCompany.getInstance();
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	public CompanyDaoImplementation(DaoFactory daoFactory) {
//		this.daoFactory = daoFactory;
//	}
	
	@Override
	public ArrayList<Company> findAll() throws DAOException {
//	    HikariProxyConnection connection = null;
//	    PreparedStatement preparedStatement = null;
//	    try {
//	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
//	    	connection.setAutoCommit(true);
//	        preparedStatement = getPreparedStatement(connection);
//	        ResultSet resultSet = preparedStatement.executeQuery();
//	        ArrayList<Company> companies = new ArrayList<>();
//	        while (resultSet.next()) {
//	        	Company company = companyMapper.mapCompany(resultSet);
//	        	companies.add(company);
//	        }
//	        
//	        return companies;
//	    } catch ( SQLException e ) {
//	        throw new DAOException( e );
//	    } finally {
//	    	DaoUtilitary.closeDao(preparedStatement,connection);
//	    }
		ArrayList<Company> companies = (ArrayList<Company>) jdbcTemplate.query(SQL_SELECT, rowMapperCompany);
		return companies;
	}
	
//	public PreparedStatement getPreparedStatement(Connection connection) {
//		try {
//			return daoUtilitary.initializePreparedRequest( connection, SQL_SELECT, true );
//		} catch (SQLException e) {
//			logger.error("The connection can't be reach" + e);
//		}
//		return null;
//	}
//	
//	public Connection getDaoFactoryConnection() {
//		try {
//			return (Connection) daoFactory.getConnection();
//		} catch (SQLException e) {
//			logger.error("The connection can't be reach" + e);
//		};
//		return null;
//	}

	@Override
	public void delete(Long id) throws DAOException {
		HikariProxyConnection connection = null;
	    PreparedStatement preparedStatementDeleteCompany = null;
	    PreparedStatement preparedStatementDeleteComputer = null;
	    DaoUtilitary daoUtilitary = DaoUtilitary.getInstance();
	    try {
	    	connection = (HikariProxyConnection) DataSourceConfigHikari.getDataSource().getConnection();
	    	connection.setAutoCommit(false);
	    	Object[] objects = {id};
	    	preparedStatementDeleteComputer = daoUtilitary.initializePreparedRequest(connection, SQL_DELETE_COMPUTER , false , objects);
	    	preparedStatementDeleteComputer.executeUpdate();
	    	preparedStatementDeleteCompany = daoUtilitary.initializePreparedRequest( connection, SQL_DELETE_COMPANY , false , objects);
	    	preparedStatementDeleteCompany.executeUpdate();
	    	connection.commit();
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatementDeleteCompany,connection);
	    	DaoUtilitary.closeDao(preparedStatementDeleteComputer,connection);
	    }
	}

	@Override
	public void create(String name) throws DAOException {
//		HikariProxyConnection connection = null;
//	    PreparedStatement preparedStatement = null;
//	    try {
//	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
//	    	connection.setAutoCommit(true);
//	    	Object[] objects = { name };
//	    	preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_CREATE, true , objects);
//	        preparedStatement.executeUpdate();
//	    } catch ( SQLException e ) {
//	        throw new DAOException( e );
//	    } finally {
//	    	DaoUtilitary.closeDao(preparedStatement,connection);
//	    }
		Object[] objects = { name };
		jdbcTemplate.update(SQL_CREATE, objects);
	}
}
