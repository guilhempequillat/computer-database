package dao.daoImplementation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;

import ch.qos.logback.classic.Logger;
import connectionPool.DataSource;
import dao.DaoFactory;
import dao.DaoUtilitary;
import dao.daoInterface.ComputerDao;
import exception.DAOException;
import mapper.ComputerMapper;
import model.Computer;

public class ComputerDaoImplementation implements ComputerDao {


	private static final String SQL_FIND_ALL                          = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name  FROM computer LEFT JOIN company ON computer.company_id = company.id";
	private static final String SQL_FIND                              = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_UPDATE_NAME                       = "UPDATE computer SET name = ? WHERE id = ?";
	private static final String SQL_UPDATE_COMPANY                    = "UPDATE computer SET company_id = ? WHERE id = ?";
	private static final String SQL_UPDATE_INTRODUCED                 = "UPDATE computer SET introduced = ? WHERE id = ?";
	private static final String SQL_UPDATE_DISCONTINUED               = "UPDATE computer SET discontinued = ? WHERE id = ?";
	private static final String SQL_CREATE                            = "INSERT INTO computer ( name , discontinued ,introduced , company_id ) VALUES (?,?,?,?) ";
	private static final String SQL_DELETE                            = "DELETE FROM computer WHERE id = ?";
	private static final String SQL_COUNT                             = "SELECT COUNT(id) FROM computer AS nb";
	private static final String SQL_FIND_PAGINATION_ASC_NAME          = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.name ASC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_DESC_NAME         = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.name DESC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_ASC_INTRODUCED    = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.introduced ASC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_DESC_INTRODUCED   = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.introduced DESC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_ASC_DISCONTINUED  = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.discontinued ASC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_DESC_DISCONTINUED = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.discontinued DESC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_ASC_COMPANY       = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY company.name ASC LIMIT ?,?";
	private static final String SQL_FIND_PAGINATION_DESC_COMPANY      = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY company.name DESC LIMIT ?,?";
	private static final String SQL_COUNT_FILTER					  = "SELECT COUNT(*) FROM computer LEFT JOIN company ON computer.company_id = company.id "
																    + " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? ";
	
	private static final String SQL_FIND_PAGINATION_ASC_NAME_FILTER  = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name,''"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? "
																	+ " ORDER BY computer.name ASC LIMIT ?,? ";
	private static final String SQL_FIND_PAGINATION_DESC_NAME_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ?"
																	+ " ORDER BY computer.name DESC LIMIT ?,? ";
	
	private static final String SQL_FIND_PAGINATION_ASC_INTRODUCED_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? "
																	+ " ORDER BY computer.introduced ASC LIMIT ?,? ";
	private static final String SQL_FIND_PAGINATION_DESC_INTRODUCED_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? "
																	+ " ORDER BY computer.introduced DESC LIMIT ?,? ";

	private static final String SQL_FIND_PAGINATION_ASC_DISCONTINUED_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? "
																	+ " ORDER BY computer.discontinued ASC LIMIT ?,? ";
	private static final String SQL_FIND_PAGINATION_DESC_DISCONTINUED_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? "
																	+ " ORDER BY computer.discontinued DESC LIMIT ?,? ";

	private static final String SQL_FIND_PAGINATION_ASC_COMPANY_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ?"
																	+ " ORDER BY company.name ASC LIMIT ?,? ";
	private static final String SQL_FIND_PAGINATION_DESC_COMPANY_FILTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name"
																	+ " FROM computer LEFT JOIN company ON computer.company_id = company.id "
																	+ " WHERE computer.name LIKE ? AND IFNULL(computer.introduced,'') LIKE ? AND IFNULL(computer.discontinued,'') LIKE ? AND IFNULL(company.name,'') LIKE ? "
																	+ " ORDER BY company.name DESC LIMIT ?,? ";
	private DaoFactory daoFactory;
	private DaoUtilitary daoUtilitary= DaoUtilitary.getInstance();
	private static Logger logger = (Logger) LoggerFactory.getLogger("ComputerDao");
	private ComputerMapper computerMapper = ComputerMapper.getComputerMapper();
	private HikariDataSource hikariDataSource = DataSource.getHikariDataSource();
	
	public ComputerDaoImplementation(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public ArrayList<Computer> findAll() {
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    HikariProxyConnection connection = null;
	    try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
	        preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_FIND_ALL, true );
	        resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = computerMapper.mapComputer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(resultSet,preparedStatement, connection);
	    }
	}
	
	@Override
	public Computer find(int id) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		Computer computer =null;
		ResultSet resultSet = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { id };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_FIND, true, objects);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				computer = computerMapper.mapComputer(resultSet);
			}
			return computer;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(resultSet,preparedStatement, connection);
	    }
	}
	
	@Override 
	public void updateName(int id, String name) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { name , id };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_NAME, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void updateIntroduced(int id, Date introduced) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { introduced , id };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_INTRODUCED, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void updateDiscontinued(int id, Date discontinued) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { discontinued , id };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_DISCONTINUED, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void updateCompany(int idComputer, int idCompany) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { idCompany , idComputer };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_UPDATE_COMPANY, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void create(String name, Date introduced, Date discontinued , int idCompany) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { name , discontinued, introduced, idCompany };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_CREATE, true, objects);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public void delete(int idComputer) {
	    HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { idComputer };
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_DELETE, false);
			preparedStatement.executeUpdate();
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public int count() {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
	    	connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_COUNT, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}

	@Override
	public ArrayList<Computer> findPaginationAsc(String orderType, int nbComputerIndex, int nbToShow) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = { nbComputerIndex, (nbToShow) };
	        preparedStatement = daoUtilitary.initializePreparedRequest( connection, getRequestFindPaginationAsc(orderType), true, objects );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = computerMapper.mapComputer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}

	@Override
	public ArrayList<Computer> findPaginationDesc(String orderType, int nbComputerIndex, int nbToShow) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
	    	String order = "computer."+orderType;
			Object[] objects = { nbComputerIndex, nbToShow };
	        preparedStatement = daoUtilitary.initializePreparedRequest( connection, getRequestFindPaginationDesc(orderType), true, objects );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = computerMapper.mapComputer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	@Override
	public ArrayList<Computer> findPaginationDescFilter(String orderType, int nbComputerIndex, int nbToShow 
			,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		if(nameFilter != null) {
			nameFilter = "%"+nameFilter+"%";
		}else {
			nameFilter = "%%";
		}
		if(introduedFilter != null) {
			introduedFilter = "%"+introduedFilter+"%";
		}else {
			introduedFilter = "%%";
		}
		if(discontinuedFilter != null) {
			discontinuedFilter = "%"+discontinuedFilter+"%";
		}else {
			discontinuedFilter = "%%";
		}
		if(companyFilter != null) {
			companyFilter = "%"+companyFilter+"%";
		}else {
			companyFilter = "%%";
		}
		
		try {
			connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
	    	String order = "computer."+orderType;
			Object[] objects = {nameFilter, introduedFilter, discontinuedFilter, companyFilter ,nbComputerIndex, nbToShow };
	        preparedStatement = daoUtilitary.initializePreparedRequest( connection, getRequestFindPaginationDescFilter(orderType), true, objects );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = computerMapper.mapComputer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public ArrayList<Computer> findPaginationAscFilter(String orderType, int nbComputerIndex, int nbToShow 
			,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		if(nameFilter != null) {
			nameFilter = "%"+nameFilter+"%";
		}else {
			nameFilter = "%%";
		}
		if(introduedFilter != null) {
			introduedFilter = "%"+introduedFilter+"%";
		}else {
			introduedFilter = "%%";
		}
		if(discontinuedFilter != null) {
			discontinuedFilter = "%"+discontinuedFilter+"%";
		}else {
			discontinuedFilter = "%%";
		}
		if(companyFilter != null) {
			companyFilter = "%"+companyFilter+"%";
		}else {
			companyFilter = "%%";
		}
		
		try {
			connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
	    	String order = "computer."+orderType;
			Object[] objects = {nameFilter, introduedFilter, discontinuedFilter, companyFilter ,nbComputerIndex, nbToShow };
	        preparedStatement = daoUtilitary.initializePreparedRequest( connection, getRequestFindPaginationAscFilter(orderType), true, objects );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        ArrayList<Computer> computers = new ArrayList<>();
	        while (resultSet.next()) {
	        	Computer computer = computerMapper.mapComputer(resultSet);
	        	computers.add(computer);
	        }
	        return computers;
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}
	
	@Override
	public int countFilter(String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
		HikariProxyConnection connection = null;
		PreparedStatement preparedStatement = null;
		if(nameFilter != null) {
			nameFilter = "%"+nameFilter+"%";
		}else {
			nameFilter = "%%";
		}
		if(introduedFilter != null) {
			introduedFilter = "%"+introduedFilter+"%";
		}else {
			introduedFilter = "%%";
		}
		if(discontinuedFilter != null) {
			discontinuedFilter = "%"+discontinuedFilter+"%";
		}else {
			discontinuedFilter = "%%";
		}
		if(companyFilter != null) {
			companyFilter = "%"+companyFilter+"%";
		}else {
			companyFilter = "%%";
		}
		try {
			connection = (HikariProxyConnection) hikariDataSource.getConnection();
	    	connection.setAutoCommit(true);
			Object[] objects = {nameFilter, introduedFilter, discontinuedFilter, companyFilter };
	        preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_COUNT_FILTER, true, objects );
	        ResultSet resultSet = preparedStatement.executeQuery();
	        resultSet.next();
			return resultSet.getInt(1);
		} catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement, connection);
	    }
	}

	public String getRequestFindPaginationAsc(String orderType) {
		switch (orderType) {
			case "name" : 
				return SQL_FIND_PAGINATION_ASC_NAME;
			case "introduced" : 
				return SQL_FIND_PAGINATION_ASC_INTRODUCED;
			case "discontinued" : 
				return SQL_FIND_PAGINATION_ASC_DISCONTINUED;
			case "company" : 
				return SQL_FIND_PAGINATION_ASC_COMPANY;
		}
		return SQL_FIND_PAGINATION_ASC_NAME;
	}
	
	public String getRequestFindPaginationDesc(String orderType) {
		switch (orderType) {
			case "name" : 
				return SQL_FIND_PAGINATION_DESC_NAME;
			case "introduced" : 
				return SQL_FIND_PAGINATION_DESC_INTRODUCED;
			case "discontinued" : 
				return SQL_FIND_PAGINATION_DESC_DISCONTINUED;
			case "company" : 
				return SQL_FIND_PAGINATION_DESC_COMPANY;
		}
		return SQL_FIND_PAGINATION_DESC_NAME;
	}

	public String getRequestFindPaginationAscFilter(String orderType) {
		switch (orderType) {
			case "name" : 
				return SQL_FIND_PAGINATION_ASC_NAME_FILTER;
			case "introduced" : 
				return SQL_FIND_PAGINATION_ASC_INTRODUCED_FILTER;
			case "discontinued" : 
				return SQL_FIND_PAGINATION_ASC_DISCONTINUED_FILTER;
			case "company" : 
				return SQL_FIND_PAGINATION_ASC_COMPANY_FILTER;
		}
		return SQL_FIND_PAGINATION_ASC_NAME;
	}
	
	public String getRequestFindPaginationDescFilter(String orderType) {
		switch (orderType) {
			case "name" : 
				return SQL_FIND_PAGINATION_DESC_NAME_FILTER;
			case "introduced" : 
				return SQL_FIND_PAGINATION_DESC_INTRODUCED_FILTER;
			case "discontinued" : 
				return SQL_FIND_PAGINATION_DESC_DISCONTINUED_FILTER;
			case "company" : 
				return SQL_FIND_PAGINATION_DESC_COMPANY_FILTER;
		}
		return SQL_FIND_PAGINATION_DESC_NAME;
	}
}
