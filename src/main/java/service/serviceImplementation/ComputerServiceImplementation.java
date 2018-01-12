package service.serviceImplementation;

import java.sql.Date;
import java.util.ArrayList;

import dao.daoImplementation.ComputerDaoImplementation;
import dao.daoInterface.CompanyDao;
import dao.daoInterface.ComputerDao;
import model.Computer;
import service.serviceInterface.ComputerService;

public class ComputerServiceImplementation implements ComputerService {
	
	private static ComputerServiceImplementation computerService = new ComputerServiceImplementation();
	private static ComputerDao computerDao;

	public static ComputerServiceImplementation getInstance(ComputerDao computerDao) {
		computerService.setComputerDao(computerDao);
		return computerService;
	}
	
	@Override
	public ArrayList<Computer> findAll() {
		ArrayList<Computer> computers  = computerDao.findAll();
		return computers;
	}
	
	@Override
	public Computer find(int id) {
		Computer computer = computerDao.find(id);
		return computer;
	}
	
	@Override
	public void updateName(int id, String name) {
		computerDao.updateName(id, name);
	}
	
	@Override
	public void updateIntroduced(int id, Date introduced) {
		computerDao.updateIntroduced(id, introduced);
	}
	
	@Override
	public void updateDiscontinued(int id, Date discontinued) {
		computerDao.updateDiscontinued(id, discontinued);
		
	}

	@Override
	public void updateCompany(int idComputer, int idCompany) {
		computerDao.updateCompany(idComputer, idCompany);
	}

	@Override
	public void create(String name, Date introduced, Date discontinued, int idCompany) {
		computerDao.create(name, introduced, discontinued, idCompany);
		
	}

	@Override
	public void delete(int idComputer) {
		computerDao.delete(idComputer);
	}
	
	public void setComputerDao(ComputerDao computerDaoLoaded) {
		computerDao = computerDaoLoaded;	
	}
}
