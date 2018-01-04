package DAO;

import java.util.ArrayList;

import exception.DAOException;
import model.Company;

public interface CompanyDao {
		
	ArrayList<Company> findAll() throws DAOException;
	
}
