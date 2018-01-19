import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cui.Page;
import dao.DaoUtilitary;
import dao.daoImplementation.ComputerDaoImplementation;
import service.UtilitaryService;
import service.serviceImplementation.ComputerServiceImplementation;

public class Main {

	public static void main(String[] args) {
		
		UtilitaryService c = UtilitaryService.getInstance();
		ComputerServiceImplementation c1 = c.getInstanceComputerService();
		c1.count();
		System.out.println(c1.findPaginationDesc("name", 10, 50));
		System.out.println(c1.findPaginationAsc("name", 10, 50));
		System.out.println(c1.findPaginationAsc("introduced", 10, 50));
		System.out.println(c1.findPaginationDesc("introduced", 10, 50));
		System.out.println(c1.findPaginationAsc("discontinued", 10, 50));
		System.out.println(c1.findPaginationAsc("company", 10, 50));
		
		Page page = new Page();
	}
}
