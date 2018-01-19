package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import comparator.ComparatorComputerCompany;
import comparator.ComparatorComputerDiscontinued;
import comparator.ComparatorComputerIntroduced;
import comparator.ComparatorComputerName;
import dto.Dto;
import model.Computer;

public class DashboardPaginationController {
	
	private static DashboardPaginationController pageWeb = new DashboardPaginationController();
	private static String filter="";
	private Logger logger = (Logger) LoggerFactory.getLogger("PageWeb");
	private static Dto dto;
	private boolean reverseOrder = false;
	
	public static DashboardPaginationController getInstance(HttpServletRequest request) {
		dto = Dto.getInstance(filter,request);
		return pageWeb;
	}
	
	public static DashboardPaginationController searchFilter(String filter, HttpServletRequest request) {
		Dto.setUseFilter(true);
		setFilter(filter);
		dto = Dto.getInstance(filter,request);
		return pageWeb;
	}
	
	public void changeDisplay(HttpServletRequest request) {
		if(request.getParameter("beginComputerDisplay") != null ) {
			try {
				int beginComputerDisplay = Integer.parseInt(request.getParameter("beginComputerDisplay"));
				request.getSession().setAttribute("beginComputerDisplay", beginComputerDisplay);
			}catch(NumberFormatException e) {
				logger.warn("beginComputerDisplay can't be parsed");
			}
		}
		if(request.getParameter("numberComputerToShow") != null ) {
			try {
				int numberComputerToShow = Integer.parseInt(request.getParameter("numberComputerToShow"));
				request.getSession().setAttribute("numberComputerToShow", numberComputerToShow);
			}catch(NumberFormatException e) {
				logger.warn("numberComputerToShow can't be parsed");
			}
		}
	}
	
	public void initDisplay(HttpServletRequest request) {
		request.getSession().setAttribute("beginComputerDisplay", 0);
		request.getSession().setAttribute("numberComputerToShow", 10);	
	}
	
	public void orderComputers(String orderType,HttpServletRequest request) {
		logger.debug("orderType : "+ orderType);
		logger.debug("orderType : "+ request.getParameter("order"));
		switch(orderType) {
			case "name":
				orderByName(request);
				break;
			case "introduced":
				orderByIntroduced(request);
				break;
			case "discontinued":
				orderByDiscontinued(request);
				break;
			case "company":
				orderByCompany(request);
				break;
			default:
		}
	}
	
	public boolean orderIsDefined(HttpServletRequest request) {
		return (request.getSession().getAttribute("order") != null && request.getParameter("order") != null);
	}
	
	public boolean mustInverseOrder(HttpServletRequest request , String orderType) {
		return (request.getSession().getAttribute("order").toString().equals(orderType) && request.getParameter("order").equals(orderType) && !reverseOrder);
	}
	
	public void orderByName(HttpServletRequest request) {
		
		if( orderIsDefined(request) ) {
			if(mustInverseOrder(request , "name" ) ) {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerName().reversed());
				request.getSession().setAttribute("order", "name");
				reverseOrder = true;
			} else {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerName());
				request.getSession().setAttribute("order", "name");
				reverseOrder = false;
			}
		} else {
			ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
			Collections.sort(listComputer, new ComparatorComputerName());
			request.getSession().setAttribute("order", "name");
			reverseOrder = false;
		}
	}
	
	public void orderByIntroduced(HttpServletRequest request) {
		if( orderIsDefined(request) ) {
			logger.debug("order defined");
			if(mustInverseOrder(request , "introduced" ) ) {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerIntroduced().reversed());
				request.getSession().setAttribute("order", "introduced");
				reverseOrder = true;
			} else {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerIntroduced());
				request.getSession().setAttribute("order", "introduced");
				reverseOrder = false;
			}
		} else {
			logger.debug("Not defined");
			ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
			Collections.sort(listComputer, new ComparatorComputerIntroduced());
			request.getSession().setAttribute("order", "introduced");
			reverseOrder = false;
		}
	}
	
	public void orderByDiscontinued(HttpServletRequest request) {
		if( orderIsDefined(request) ) {
			if(mustInverseOrder(request , "discontinued" ) ) {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerDiscontinued().reversed());
				request.getSession().setAttribute("order", "discontinued");
				reverseOrder = true;
			} else {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerIntroduced());
				request.getSession().setAttribute("order", "discontinued");
				reverseOrder = false;
			}
		} else {
			ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
			Collections.sort(listComputer, new ComparatorComputerIntroduced());
			request.getSession().setAttribute("order", "discontinued");
			reverseOrder = false;
		}
	}
	
	public void orderByCompany(HttpServletRequest request) {
		if( orderIsDefined(request) ) {
			if(mustInverseOrder(request , "company" ) ) {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerCompany().reversed());
				request.getSession().setAttribute("order", "company");
				reverseOrder = true;
			} else {
				ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
				Collections.sort(listComputer, new ComparatorComputerCompany());
				request.getSession().setAttribute("order", "company");
				reverseOrder = false;
			}
		} else {
			ArrayList<Computer> listComputer = (ArrayList<Computer>) request.getSession().getAttribute("listComputer");
			Collections.sort(listComputer, new ComparatorComputerCompany());
			request.getSession().setAttribute("order", "company");
			reverseOrder = false;
		}
	}
	
	public static void setFilter(String filter1) {
		filter = filter1;
	}
	
}
