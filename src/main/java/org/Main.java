package org;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cdb.cui.Page;
import org.cdb.dao.DaoUtilitary;
import org.cdb.dao.daoImplementation.ComputerDaoImplementation;
import org.cdb.service.UtilitaryService;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;

public class Main {

	public static void main(String[] args) {
		UtilitaryService u = UtilitaryService.getInstance();
		ComputerServiceImplementation cs = u.getInstanceComputerService();
		
		System.out.println(cs.findPaginationDescFilter("name", 1, 10,"a","","","ap"));
		
		Page page = new Page();
	}
}
