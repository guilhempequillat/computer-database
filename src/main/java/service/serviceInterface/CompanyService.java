package service.serviceInterface;

import java.util.ArrayList;

import model.Company;

public interface CompanyService {
	
	ArrayList<Company>  findAll();
	
	void create(Company company);
	
	void delete(Long id);
}
