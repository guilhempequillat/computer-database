package service.serviceInterface;

import model.Computer;

import java.sql.Date;
import java.util.ArrayList;

public interface ComputerService {
	ArrayList<Computer> findAll();
	Computer find(int id);
	void updateName(int id, String Name);
	void updateIntroduced(int id, Date introduced);
	void updateDiscontinued(int id, Date discontinued);
	void updateCompany(int idComputer, int idCompany);
	void create(String name, Date introduced, Date discontinued, int idCompany);
	void delete(int idComputer);
}
