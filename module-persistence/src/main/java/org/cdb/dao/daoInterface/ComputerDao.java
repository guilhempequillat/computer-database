package org.cdb.dao.daoInterface;

import java.sql.Date;
import java.util.ArrayList;

import org.cdb.model.Computer;

public interface ComputerDao {
	
	ArrayList<Computer> findAll();
	
	Computer find(int id);
	
	void updateName(int id, String name);
	
	void updateIntroduced(int id, Date introduced);
	
	void updateDiscontinued(int id, Date discontinued);
	
	void updateCompany(int idComputer, int idCompany);
	
	void create( String name, Date introduced, Date discontinued, int idCompany);
	
	void delete( int idComputer );
	
	int count();
	
	int countFilter(String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter);
	ArrayList<Computer> findPaginationAsc(String orderType, int nbComputer, int nbToShow);
	ArrayList<Computer> findPaginationDesc(String orderType, int nbComputerIndex, int nbToShow);	
	ArrayList<Computer> findPaginationDescFilter(String orderType, int nbComputerIndex, int nbToShow,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter);
	ArrayList<Computer> findPaginationAscFilter(String orderType, int nbComputerIndex, int nbToShow,String nameFilter ,String introduedFilter,String discontinuedFilter, String companyFilter);
	
}
