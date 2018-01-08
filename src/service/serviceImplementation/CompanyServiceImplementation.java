package service.serviceImplementation;

import model.Company;
import service.serviceInterface.CompanyService;

public class CompanyServiceImplementation implements CompanyService{

	private static CompanyServiceImplementation companyServiceImplementation = new CompanyServiceImplementation();
	
	@Override
	public Company findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CompanyServiceImplementation getInstance() {
		return companyServiceImplementation;
	}

}
