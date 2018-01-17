package service.serviceInterface;

import model.Computer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ComputerService {
	ArrayList<Computer> findAll();
	Computer find(int id);
	void updateName(int id, String Name);
	void updateIntroduced(int id, LocalDate introduced);
	void updateDiscontinued(int id, LocalDate discontinued);
	void updateCompany(int idComputer, int idCompany);
	void create(Computer computer);
	void delete(int idComputer);
}
