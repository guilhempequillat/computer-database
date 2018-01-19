package controller;

import model.Computer;
import service.UtilitaryService;
import service.serviceImplementation.ComputerServiceImplementation;

import java.util.ArrayList;

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
		refreshComputerCount();
		if( !filterState ) {
			if( orderIsReverse ) {
				selectAComputerListDesc();
			} else {
				selectAComputerListAsc();
			}
		} else {
			loadComputerFilter();
		}
	}
	
	public static void loadComputerFilter() {
		switch(orderType) {
			case "name" : 
				listComputer = computerService.findPaginationAsc(orderType, 0, countComputer);
				break;
			case "introduced" :
				listComputer = computerService.findPaginationAsc(orderType, 0, countComputer);
				break;
			case "discontinued" :
				listComputer = computerService.findPaginationAsc(orderType, 0, countComputer);
				break;
			case "company" :
				listComputer = computerService.findPaginationAsc(orderType, 0, countComputer);
				break;
			default:
				listComputer = computerService.findPaginationAsc(orderType, 0, countComputer);
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
}
