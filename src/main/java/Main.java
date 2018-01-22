import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cui.Page;
import dao.DaoUtilitary;
import dao.daoImplementation.ComputerDaoImplementation;
import service.UtilitaryService;
import service.serviceImplementation.ComputerServiceImplementation;

public class Main {

	public static void main(String[] args) {
		UtilitaryService u = UtilitaryService.getInstance();
		ComputerServiceImplementation cs = u.getInstanceComputerService();
		
		System.out.println(cs.findPaginationDescFilter("name", 1, 10,"a","","","ap"));
		
		Page page = new Page();
	}
}
