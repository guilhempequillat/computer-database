package service;

import dao.DaoFactory;
import dao.daoInterface.CompanyDao;
import dao.daoInterface.ComputerDao;
import service.serviceImplementation.CompanyServiceImplementation;
import service.serviceImplementation.ComputerServiceImplementation;

public class UtilitaryService {
	
	private DaoFactory daoFactory;
	private CompanyDao companyDao;
	private ComputerDao computerDao;
	private static CompanyServiceImplementation companyService;
	private static ComputerServiceImplementation computerService;

	public UtilitaryService() {
		this.daoFactory = DaoFactory.getInstance();
		this.companyDao = (CompanyDao) this.daoFactory.getCompanyDao();
		this.computerDao = (ComputerDao) this.daoFactory.getComputerDao();
		this.companyService = CompanyServiceImplementation.getInstance(companyDao);
		this.computerService = ComputerServiceImplementation.getInstance(computerDao);
	}
	
	public CompanyServiceImplementation getInstanceCompanyService() {
		return companyService;
	}
	
	public ComputerServiceImplementation getInstanceComputerService() {
		return computerService;
	} 
}
