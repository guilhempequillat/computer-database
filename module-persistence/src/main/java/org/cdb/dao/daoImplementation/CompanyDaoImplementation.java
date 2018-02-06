package org.cdb.dao.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.cdb.connectionPool.DataSourceConfigHikari;
import org.cdb.dao.DaoUtilitary;
import org.cdb.dao.daoInterface.CompanyDao;
import org.cdb.exception.DAOException;
import org.cdb.mapper.CompanyMapper;
import org.cdb.mapper.RowMapperCompany;
import org.cdb.model.Company;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zaxxer.hikari.pool.HikariProxyConnection;

import ch.qos.logback.classic.Logger;

@Transactional
@Repository
public class CompanyDaoImplementation implements CompanyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DataSource dataSource;
	
	private static final String SQL_SELECT          = "SELECT id , name FROM company";
	private static final String SQL_CREATE          = "INSERT INTO company ( name ) VALUES (?) ";
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ? ";
	private static final String SQL_DELETE_COMPANY  = "DELETE FROM company WHERE  id = ? ";
	private CompanyMapper companyMapper = CompanyMapper.getCompanyMapper();
	private Logger logger = (Logger) LoggerFactory.getLogger("CompanyDaoImplementation");
	private RowMapperCompany rowMapperCompany = RowMapperCompany.getInstance();
	
	
	@Override
	public ArrayList<Company> findAll() throws DAOException {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Company.class);
		return (ArrayList<Company>) cr.list();
	}

	@Override
	public void delete(Long id) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatementDeleteCompany = null;
	    PreparedStatement preparedStatementDeleteComputer = null;
	    DaoUtilitary daoUtilitary = DaoUtilitary.getInstance();
	    try {
	    	connection = (Connection) dataSource.getConnection();
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
		Object[] objects = { name };
		jdbcTemplate.update(SQL_CREATE, objects);
	}
}