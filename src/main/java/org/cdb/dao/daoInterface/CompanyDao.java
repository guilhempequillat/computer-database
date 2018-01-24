package org.cdb.dao.daoInterface;

import java.util.ArrayList;

import org.cdb.exception.DAOException;
import org.cdb.model.Company;

public interface CompanyDao {
		
	ArrayList<Company> findAll() throws DAOException;
	void delete(Long id) throws DAOException;
	void create(String name) throws DAOException;
	
	
}
