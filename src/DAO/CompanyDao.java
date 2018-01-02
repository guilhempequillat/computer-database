package DAO;

import exception.DAOException;
import model.Company;

public interface CompanyDao {
	
	void create(Company company) throws DAOException;
	
	Company find(String name) throws DAOException;
}
