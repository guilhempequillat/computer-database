package service.serviceImplementation;

import java.util.ArrayList;

import dao.daoInterface.CompanyDao;
import model.Company;
import service.serviceInterface.CompanyService;

public class CompanyServiceImplementation implements CompanyService{

	private static CompanyServiceImplementation companyServiceImplementation = new CompanyServiceImplementation();
	private static CompanyDao companyDao;
	
	@Override
	public ArrayList<Company> findAll() {
		ArrayList<Company> companies = companyDao.findAll();
		return companies;
	}
	
	public static CompanyServiceImplementation getInstance(CompanyDao companyDaoLoaded) {
		companyServiceImplementation.setCompanyDao(companyDaoLoaded);
		return companyServiceImplementation;
	}
	
	public void setCompanyDao(CompanyDao companyDaoLoaded) {
		companyDao = companyDaoLoaded;
	}
}
