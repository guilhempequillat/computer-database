package org.cdb.service.serviceImplementation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.cdb.dao.daoImplementation.ComputerDaoImplementation;
import org.cdb.dao.daoInterface.CompanyDao;
import org.cdb.dao.daoInterface.ComputerDao;
import org.cdb.model.Computer;
import org.cdb.service.serviceInterface.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerServiceImplementation implements ComputerService {
	
	//private static ComputerServiceImplementation computerService = new ComputerServiceImplementation();
	@Autowired
	private ComputerDao computerDao;


	
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
	public void updateIntroduced(int id, LocalDate introduced) {
		Date date = Date.valueOf(introduced);
		computerDao.updateIntroduced(id, date);
	}
	
	@Override
	public void updateDiscontinued(int id, LocalDate discontinued) {
		Date date = Date.valueOf(discontinued);
		computerDao.updateDiscontinued(id, date);
		
	}

	@Override
	public void updateCompany(int idComputer, int idCompany) {
		computerDao.updateCompany(idComputer, idCompany);
	}

	@Override
	public void create(Computer computer) {
		String name = computer.getName();
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		int idCompany = Integer.parseInt(computer.getCompany_id().toString());
		Date dateIntroduced = Date.valueOf(introduced);
		Date dateDiscontinued = Date.valueOf(discontinued);
		computerDao.create(name, dateIntroduced, dateDiscontinued, idCompany);
	}

	@Override
	public void delete(int idComputer) {
		computerDao.delete(idComputer);
	}
	
	@Override
	public int count() {
		return computerDao.count();
	}
	
	public void setComputerDao(ComputerDao computerDaoLoaded) {
		computerDao = computerDaoLoaded;	
	}
	
	@Override
	public ArrayList<Computer> findPaginationAsc(String orderType, int nbComputerIndex, int nbToShow){
		return computerDao.findPaginationAsc(orderType, nbComputerIndex, nbToShow);
	}

	@Override
	public ArrayList<Computer> findPaginationDesc(String orderType, int nbComputerIndex, int nbToShow) {
		return computerDao.findPaginationDesc(orderType, nbComputerIndex, nbToShow);
	}
	@Override
	public ArrayList<Computer> findPaginationDescFilter(String orderType, int nbComputerIndex, int nbToShow,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
		return computerDao.findPaginationDescFilter(orderType, nbComputerIndex, nbToShow, nameFilter ,introduedFilter,discontinuedFilter,companyFilter);
	}
	@Override
	public ArrayList<Computer> findPaginationAscFilter(String orderType, int nbComputerIndex, int nbToShow,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
		return computerDao.findPaginationAscFilter(orderType, nbComputerIndex, nbToShow, nameFilter ,introduedFilter,discontinuedFilter,companyFilter);
	}
	
	@Override
	public int countFilter(String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter) {
		return computerDao.countFilter(nameFilter, introduedFilter, discontinuedFilter, companyFilter);
	}
}
