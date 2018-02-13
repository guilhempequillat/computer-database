package org.cdb.service.serviceImplementation;

import java.util.ArrayList;

import org.cdb.dao.daoInterface.CompanyDao;
import org.cdb.model.Company;
import org.cdb.service.serviceInterface.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImplementation implements CompanyService{

	@Autowired
	private CompanyDao companyDao;
	
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
	
	public void setCompanyDao(CompanyDao companyDaoLoaded) {
		companyDao = companyDaoLoaded;
	}
}
