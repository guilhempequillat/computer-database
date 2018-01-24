package org.cdb.service.serviceImplementation;

import java.util.ArrayList;

import org.cdb.dao.daoInterface.CompanyDao;
import org.cdb.model.Company;
import org.cdb.service.serviceInterface.CompanyService;

public class CompanyServiceImplementation implements CompanyService{

	private static CompanyServiceImplementation companyServiceImplementation = new CompanyServiceImplementation();
	private static CompanyDao companyDao;
	
	@Override
	public ArrayList<Company> findAll() {
		ArrayList<Company> companies = companyDao.findAll();
		return companies;
	}
	
	@Override
	public void create(Company company) {
		String name = company.getName();
		companyDao.create(name);
	}
	
	@Override
	public void delete(Long id) {
		companyDao.delete(id);
	}
	
	public static CompanyServiceImplementation getInstance(CompanyDao companyDaoLoaded) {
		companyServiceImplementation.setCompanyDao(companyDaoLoaded);
		return companyServiceImplementation;
	}
	
	public void setCompanyDao(CompanyDao companyDaoLoaded) {
		companyDao = companyDaoLoaded;
	}
}
