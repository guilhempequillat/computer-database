package org.cdb.service.serviceInterface;

import java.util.ArrayList;

import org.cdb.model.Company;

public interface CompanyService {
	
	ArrayList<Company>  findAll();
	
	void create(Company company);
	
	void delete(Long id);
}
