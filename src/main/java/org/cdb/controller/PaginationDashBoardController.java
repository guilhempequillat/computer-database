package org.cdb.controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.cdb.service.UtilitaryService;
import org.cdb.service.serviceImplementation.CompanyServiceImplementation;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.qos.logback.classic.Logger;

@Component
public class PaginationDashBoardController {

	@Autowired
	private ComputerServiceImplementation computerService;
	
	@Autowired
	private CompanyServiceImplementation companyService;
	
	private ArrayList<Computer> listComputer;
	private ArrayList<Company> listCompany;
	private int nbComputerIndex;
	private int nbToShow;
	private String orderType = "name";
	private boolean orderIsReverse = false;
	private int countComputer;
	private boolean filterState = false;
	private String filter="";
	private String filterName="";
	private String filterIntroduced="";
	private String filterDiscontined="";
	private String filterCompany="";
	private Logger logger = (Logger) LoggerFactory.getLogger("PaginationDashBoardController");
	
	public void loadComputer() {
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
	
	public void refreshComputerCountFilter() {
		countComputer = computerService.countFilter(filterName, filterIntroduced, filterDiscontined, filterCompany);
	}

	public void selectAComputerListAscFilter() {
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
	
	public void selectAComputerListDescFilter() {
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
	
	public void selectAComputerListAsc() {
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
	
	public void selectAComputerListDesc() {
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
	
	public void loadCompany() {
		listCompany = companyService.findAll();
	}
	
	public ArrayList<Company> getListCompany(){
		return listCompany;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean isOrderIsReverse() {
		return orderIsReverse;
	}
	
	public void refreshComputerCount() {
		countComputer = computerService.count();
	}
	
	public int getCountComputerCount() {
		return countComputer;
	}

	public void setOrderIsReverse(boolean orderIsReverse) {
		this.orderIsReverse = orderIsReverse;
	}
	
	public ArrayList<Computer> getListComputer(){
		return listComputer;
	}

	public int getNbComputerIndex() {
		return nbComputerIndex;
	}

	public void setNbComputerIndex(int nbComputerIndex) {
		this.nbComputerIndex = nbComputerIndex;
	}

	public int getNbToShow() {
		return nbToShow;
	}

	public void setNbToShow(int nbToShow) {
		this.nbToShow = nbToShow;
	}

	public void setListComputer(ArrayList<Computer> listComputer) {
		this.listComputer = listComputer;
	}
	
	public void inverseOrder() {
		orderIsReverse = orderIsReverse ? false : true ;
	}
	
	public void setFilter(String filter1) {
		filter = filter1;
	}

	public boolean isFilterState() {
		return filterState;
	}

	public void setFilterState(boolean filterState) {
		this.filterState = filterState;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getFilterIntroduced() {
		return filterIntroduced;
	}

	public void setFilterIntroduced(String filterIntroduced) {
		this.filterIntroduced = filterIntroduced;
	}

	public String getFilterDiscontined() {
		return filterDiscontined;
	}

	public void setFilterDiscontined(String filterDiscontined) {
		this.filterDiscontined = filterDiscontined;
	}

	public String getFilterCompany() {
		return filterCompany;
	}

	public void setFilterCompany(String filterCompany) {
		this.filterCompany = filterCompany;
	}
}
