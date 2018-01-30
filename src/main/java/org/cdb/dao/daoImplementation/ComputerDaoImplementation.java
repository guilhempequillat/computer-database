package org.cdb.dao.daoImplementation;

import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.criteria.CriteriaUpdate;

import org.cdb.dao.daoInterface.ComputerDao;
import org.cdb.mapper.RowMapperComputer;
import org.cdb.model.Computer;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Transactional
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
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	private RowMapperComputer rowMapperComputer = RowMapperComputer.getInstance();

	@Override
	public ArrayList<Computer> findAll() {
        ArrayList<Computer> computers = (ArrayList<Computer>) jdbcTemplate.query(SQL_FIND_ALL,rowMapperComputer);
        return computers;
	}
	
	@Override
	public Computer find(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Computer.class);
		cr.add(Restrictions.eq("id", (Long) Long.parseLong(id+"")));
		return (Computer) cr.uniqueResult();
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
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(id) from Computer");
		logger.debug(query.getSingleResult().toString());
		return (int) Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public ArrayList<Computer> findPaginationAsc(String orderType, int nbComputerIndex, int nbToShow) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Computer.class);
		cr.addOrder( Order.asc(orderType) );
		cr.setFirstResult(nbComputerIndex);
		cr.setMaxResults(nbToShow);
		return (ArrayList<Computer>) cr.list();
	}

	@Override
	public ArrayList<Computer> findPaginationDesc(String orderType, int nbComputerIndex, int nbToShow) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Computer.class);
		cr.addOrder( Order.desc(orderType) );
		cr.setFirstResult(nbComputerIndex);
		cr.setMaxResults(nbToShow);
		return (ArrayList<Computer>) cr.list();
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
