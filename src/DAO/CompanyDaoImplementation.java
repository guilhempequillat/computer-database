package DAO;

import exception.DAOException;
import model.Company;

public class CompanyDaoImplementation implements CompanyDao {

	private DAOFactory daoFactory;
	
	public CompanyDaoImplementation(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void create(Company company) throws DAOException {
	}

	@Override
	public Company find(String name) throws DAOException {
		return null;
	}

}
