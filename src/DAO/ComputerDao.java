package DAO;

import java.util.ArrayList;
import model.Computer;

public interface ComputerDao {
	
	ArrayList<Computer> findAll();
	
	Computer find(int id);
	
	void updateName(int id, String name);
}
