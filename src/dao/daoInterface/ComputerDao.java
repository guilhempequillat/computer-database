package dao.daoInterface;

import java.sql.Date;
import java.util.ArrayList;
import model.Computer;

public interface ComputerDao {
	
	ArrayList<Computer> findAll();
	
	Computer find(int id);
	
	void updateName(int id, String name);
	
	void updateIntroduced(int id, Date introduced);
	
	void updateDiscontinued(int id, Date discontinued);
	
	void updateCompany(int idComputer, int idCompany);
	
	void create( String name, Date introduced, Date discontinued, int idCompany);
	
	void delete( int idComputer );
}
