package org.cdb.service;

import org.cdb.dao.DaoFactory;
import org.cdb.dao.daoInterface.CompanyDao;
import org.cdb.dao.daoInterface.ComputerDao;
import org.cdb.service.serviceImplementation.CompanyServiceImplementation;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UtilitaryService {
	
	private DaoFactory daoFactory;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ComputerDao computerDao;
	private static CompanyServiceImplementation companyService;
	private static ComputerServiceImplementation computerService;
	private static UtilitaryService utilitaryService = new UtilitaryService();

	public UtilitaryService() {
//		this.daoFactory = DaoFactory.getInstance();
//		this.companyDao = (CompanyDao) this.daoFactory.getCompanyDao();
//		this.computerDao = (ComputerDao) this.daoFactory.getComputerDao();
//		this.companyService = CompanyServiceImplementation.getInstance(companyDao);
//		this.computerService = ComputerServiceImplementation.getInstance(computerDao);
	}
	
	public static UtilitaryService getInstance() {
		return utilitaryService;
	}
	

	public CompanyServiceImplementation getInstanceCompanyService() {
		return companyService;
	}
	
	
	public ComputerServiceImplementation getInstanceComputerService() {
		return computerService;
	}
}