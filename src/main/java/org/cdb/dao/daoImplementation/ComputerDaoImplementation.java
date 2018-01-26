package org.cdb.dao.daoImplementation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cdb.connectionPool.DataSourceConfigHikari;
import org.cdb.dao.DaoFactory;
import org.cdb.dao.DaoUtilitary;
import org.cdb.dao.daoInterface.ComputerDao;
import org.cdb.exception.DAOException;
import org.cdb.mapper.ComputerMapper;
import org.cdb.mapper.RowMapperComputer;
import org.cdb.model.Computer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;

import ch.qos.logback.classic.Logger;

@Repository
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
	private static Logger logger = (Logger) LoggerFactory.getLogger("ComputerDao");

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapperComputer rowMapperComputer = RowMapperComputer.getInstance();

	@Override
	public ArrayList<Computer> findAll() {
        ArrayList<Computer> computers = (ArrayList<Computer>) jdbcTemplate.query(SQL_FIND_ALL,rowMapperComputer);
        return computers;
	}
	
	@Override
	public Computer find(int id) {
		try {
			Computer computer2 = (Computer) jdbcTemplate.queryForObject(SQL_FIND, rowMapperComputer, new Object[] {id});
			return computer2;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			logger.error("Computer not found "+e);
		}
		return null;
	}
	
	@Override 
	public void updateName(int id, String name) {
		Object[] objects = { name , id };
		jdbcTemplate.update(SQL_UPDATE_NAME, objects);
	}
	
	@Override
	public void updateIntroduced(int id, Date introduced) {
		Object[] objects = { introduced , id };
		jdbcTemplate.update(SQL_UPDATE_INTRODUCED,objects);
	}
	
	@Override
	public void updateDiscontinued(int id, Date discontinued) {
		Object[] objects = { discontinued , id };
		jdbcTemplate.update(SQL_UPDATE_DISCONTINUED,objects);
	}
	
	@Override
	public void updateCompany(int idComputer, int idCompany) {
		Object[] objects = { idCompany , idComputer };
		jdbcTemplate.update(SQL_UPDATE_COMPANY,objects);
	}
	
	@Override
	public void create(String name, Date introduced, Date discontinued , int idCompany) {
		Object[] objects = { name , discontinued, introduced, idCompany };
		jdbcTemplate.update(SQL_CREATE,objects);
	}
	
	@Override
	public void delete(int idComputer) {
		Object[] objects = { idComputer };
		jdbcTemplate.update(SQL_DELETE,objects);
	}
	
	@Override
	public int count() {
		int nbComputer = jdbcTemplate.queryForObject(SQL_COUNT, Integer.class);
		return nbComputer;
	}

	@Override
	public ArrayList<Computer> findPaginationAsc(String orderType, int nbComputerIndex, int nbToShow) {
		Object[] objects = { nbComputerIndex, (nbToShow) };
		return (ArrayList<Computer>) jdbcTemplate.query(getRequestFindPaginationAsc(orderType),rowMapperComputer,objects);
	}

	@Override
	public ArrayList<Computer> findPaginationDesc(String orderType, int nbComputerIndex, int nbToShow) {
		Object[] objects = { nbComputerIndex, nbToShow };
		return (ArrayList<Computer>) jdbcTemplate.query(getRequestFindPaginationDesc(orderType),rowMapperComputer,objects);
	}
	@Override
	public ArrayList<Computer> findPaginationDescFilter(String orderType, int nbComputerIndex, int nbToShow 
			,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
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
		Object[] objects = {nameFilter, introduedFilter, discontinuedFilter, companyFilter ,nbComputerIndex, nbToShow };
		return (ArrayList<Computer>) jdbcTemplate.query(getRequestFindPaginationDescFilter(orderType),rowMapperComputer,objects);
	}
	
	@Override
	public ArrayList<Computer> findPaginationAscFilter(String orderType, int nbComputerIndex, int nbToShow 
			,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
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
		Object[] objects = {nameFilter, introduedFilter, discontinuedFilter, companyFilter ,nbComputerIndex, nbToShow };
		return (ArrayList<Computer>) jdbcTemplate.query(getRequestFindPaginationAscFilter(orderType),rowMapperComputer,objects);
	}
	
	@Override
	public int countFilter(String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
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
		Object[] objects = {nameFilter, introduedFilter, discontinuedFilter, companyFilter };
		return (Integer) jdbcTemplate.queryForObject(SQL_COUNT_FILTER,objects,Integer.class);
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
