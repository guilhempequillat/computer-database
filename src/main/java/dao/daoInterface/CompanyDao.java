package dao.daoInterface;

import java.util.ArrayList;

import exception.DAOException;
import model.Company;

public interface CompanyDao {
		
	ArrayList<Company> findAll() throws DAOException;
	void delete(Long id) throws DAOException;
	void create(String name) throws DAOException;
	
	
}
