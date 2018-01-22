package controller;

import model.Computer;
import service.UtilitaryService;
import service.serviceImplementation.ComputerServiceImplementation;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class PaginationDashBoardController {
	private static ArrayList<Computer> listComputer;
	private static int nbComputerIndex;
	private static int nbToShow;
	private static String orderType = "name";
	private static PaginationDashBoardController paginationDashBoardController = initalisation();
	private static boolean orderIsReverse = false;
	private static ComputerServiceImplementation computerService;
	private static int countComputer;
	private static boolean filterState = false;
	private static String filter="";
	private static String filterName="";
	private static String filterIntroduced="";
	private static String filterDiscontined="";
	private static String filterCompany="";
	private static Logger logger = (Logger) LoggerFactory.getLogger("PaginationDashBoardController");
	
	public static PaginationDashBoardController getInstance() {
		return paginationDashBoardController;
	}
	
	public static PaginationDashBoardController initalisation() {
		PaginationDashBoardController paginationDashBoardController = new PaginationDashBoardController();
		UtilitaryService utilitaryService = UtilitaryService.getInstance();
		computerService = utilitaryService.getInstanceComputerService();
		return paginationDashBoardController;
	}
	
	public static void loadComputer() {
		if( !filterState ) {
			refreshComputerCount();
			if( orderIsReverse ) {
				selectAComputerListDesc();
			} else {
				selectAComputerListAsc();
			}
		} else {
			logger.debug("Filter Activate");
			refreshComputerCountFilter();
			if( orderIsReverse ) {
				selectAComputerListDescFilter();
			} else {
				selectAComputerListAscFilter();
			}
		}
	}
	
	public static void refreshComputerCountFilter() {
		countComputer = computerService.countFilter(filterName, filterIntroduced, filterDiscontined, filterCompany);
	}

	public static void selectAComputerListAscFilter() {
		switch(orderType) {
			case "name" : 
				listComputer = computerService.findPaginationAscFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			case "introduced" :
				listComputer = computerService.findPaginationAscFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			case "discontinued" :
				listComputer = computerService.findPaginationAscFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			case "company" :
				listComputer = computerService.findPaginationAscFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			default:
				listComputer = computerService.findPaginationAscFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
		}
	}
	
	public static void selectAComputerListDescFilter() {
		switch(orderType) {
			case "name" : 
				listComputer = computerService.findPaginationDescFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			case "introduced" :
				listComputer = computerService.findPaginationDescFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			case "discontinued" :
				listComputer = computerService.findPaginationDescFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			case "company" :
				listComputer = computerService.findPaginationDescFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
				break;
			default:
				listComputer = computerService.findPaginationDescFilter(orderType, nbComputerIndex, nbToShow, filterName, filterIntroduced, filterDiscontined, filterCompany);
		}
	}
	
	public static void selectAComputerListAsc() {
		switch(orderType) {
			case "name" : 
				listComputer = computerService.findPaginationAsc(orderType, nbComputerIndex, nbToShow);
				break;
			case "introduced" :
				listComputer = computerService.findPaginationAsc(orderType, nbComputerIndex, nbToShow);
				break;
			case "discontinued" :
				listComputer = computerService.findPaginationAsc(orderType, nbComputerIndex, nbToShow);
				break;
			case "company" :
				listComputer = computerService.findPaginationAsc(orderType, nbComputerIndex, nbToShow);
				break;
			default:
				listComputer = computerService.findPaginationAsc(orderType, nbComputerIndex, nbToShow);
		}
	}
	
	public static void selectAComputerListDesc() {
		switch(orderType) {
			case "name" : 
				listComputer = computerService.findPaginationDesc(orderType, nbComputerIndex, nbToShow);
				break;
			case "introduced" :
				listComputer = computerService.findPaginationDesc(orderType, nbComputerIndex, nbToShow);
				break;
			case "discontinued" :
				listComputer = computerService.findPaginationDesc(orderType, nbComputerIndex, nbToShow);
				break;
			case "company" :
				listComputer = computerService.findPaginationDesc(orderType, nbComputerIndex, nbToShow);
				break;
		}
	}

	public static String getOrderType() {
		return orderType;
	}

	public static void setOrderType(String orderType) {
		PaginationDashBoardController.orderType = orderType;
	}

	public static boolean isOrderIsReverse() {
		return orderIsReverse;
	}
	
	public static void refreshComputerCount() {
		countComputer = computerService.count();
	}
	
	public static int getCountComputerCount() {
		return countComputer;
	}

	public static void setOrderIsReverse(boolean orderIsReverse) {
		PaginationDashBoardController.orderIsReverse = orderIsReverse;
	}
	
	public static ArrayList<Computer> getListComputer(){
		return listComputer;
	}

	public static int getNbComputerIndex() {
		return nbComputerIndex;
	}

	public static void setNbComputerIndex(int nbComputerIndex) {
		PaginationDashBoardController.nbComputerIndex = nbComputerIndex;
	}

	public static int getNbToShow() {
		return nbToShow;
	}

	public static void setNbToShow(int nbToShow) {
		PaginationDashBoardController.nbToShow = nbToShow;
	}

	public static void setListComputer(ArrayList<Computer> listComputer) {
		PaginationDashBoardController.listComputer = listComputer;
	}
	
	public static void inverseOrder() {
		orderIsReverse = orderIsReverse ? false : true ;
	}
	
	public static void setFilter(String filter1) {
		filter = filter1;
	}

	public static boolean isFilterState() {
		return filterState;
	}

	public static void setFilterState(boolean filterState) {
		PaginationDashBoardController.filterState = filterState;
	}

	public static String getFilterName() {
		return filterName;
	}

	public static void setFilterName(String filterName) {
		PaginationDashBoardController.filterName = filterName;
	}

	public static String getFilterIntroduced() {
		return filterIntroduced;
	}

	public static void setFilterIntroduced(String filterIntroduced) {
		PaginationDashBoardController.filterIntroduced = filterIntroduced;
	}

	public static String getFilterDiscontined() {
		return filterDiscontined;
	}

	public static void setFilterDiscontined(String filterDiscontined) {
		PaginationDashBoardController.filterDiscontined = filterDiscontined;
	}
	
	
}
