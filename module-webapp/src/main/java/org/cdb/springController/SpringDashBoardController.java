package org.cdb.springController;

import java.util.ArrayList;
import java.util.Locale;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.cdb.service.serviceImplementation.CompanyServiceImplementation;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.Logger;

@Controller
public class SpringDashBoardController {
	
	@Autowired
	private ComputerServiceImplementation computerService;
	
	@Autowired
	private CompanyServiceImplementation companyService;
	
	@Autowired
    private MessageSource messageSource;
	
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
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String getDashBoard(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "order", required = false) String order, 
    		@RequestParam(value = "beginComputerDisplay", required = false) String beginComputerDisplay, 
    		Model model, Locale locale) {
		managePagination(numberComputerToShow,order,beginComputerDisplay,model);
        return "dashboard";
    }

	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String getDashBoardFilter(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "order", required = false) String order, @RequestParam(value = "beginComputerDisplay", required = false) String beginComputerDisplay, 
    		@RequestParam(value = "filterName", required = false) String filterName, 
    		@RequestParam(value = "filterCompany", required = false) String filterCompany, 
    		@RequestParam(value = "filterIntroduced", required = false) String filterIntroduced, 
    		@RequestParam(value = "filterDiscontinued", required = false) String filterDiscontinued, 
    		Model model, Locale locale) {
		loadFilterParameter(model,filterName, filterCompany, filterIntroduced, filterDiscontinued);
		managePagination(numberComputerToShow,order,beginComputerDisplay,model);
        return "dashboard";
    }
	
	
	public void loadFilterParameter(Model model, String filterName, String filterCompany,
			String filterIntroduced, String filterDiscontinued) {
		boolean filterIsUsed = false;
		if(filterName != null && filterName != "") {
			setFilterName(filterName);
			model.addAttribute("filterName", filterName);
			setFilterState(true);
			filterIsUsed = true;
		}
		if(filterIntroduced != null && filterIntroduced != "") {
			setFilterIntroduced(filterIntroduced);
			model.addAttribute("filterIntroduced", filterIntroduced);
			setFilterState(true);
			filterIsUsed = true;
		}
		if(filterDiscontinued != null && filterDiscontinued != "") {
			setFilterDiscontined(filterDiscontinued);
			model.addAttribute("filterDiscontinued", filterDiscontinued);
			setFilterState(true);
			filterIsUsed = true;
		}
		if(filterCompany != null && filterCompany != "") {
			setFilterCompany(filterCompany);
			model.addAttribute("filterCompany", filterCompany);
			setFilterState(true);
			filterIsUsed = true;
		}
		if( !filterIsUsed ) {
			setFilterState(false);
			
			model.addAttribute("filterName", "");
			model.addAttribute("filterIntroduced", "");
			model.addAttribute("filterDiscontinued", "");
			model.addAttribute("filterCompany", "");
	
			setFilterName("");
			setFilterIntroduced("");
			setFilterDiscontined("");
			setFilterCompany("");
		}
	}
	
	public void managePagination(String numberComputerToShow, String order, String beginComputerDisplay,Model model) {
		refreshComputerCount();
		model.addAttribute("count",getCountComputerCount());
		if(order!= null) {
			model.addAttribute("order", order);
			setOrderType(order);
			inverseOrder();
		}
		if(beginComputerDisplay != null) {
			if(beginComputerDisplay != "" || numberComputerToShow != "") {
				changeAttributs(beginComputerDisplay, numberComputerToShow,  model);
				loadComputerController(model);
				loadCompanyController(model);
				loadParameter(model);
			}
		} else {
			logger.debug("Init Pagination");
			model.addAttribute("beginComputerDisplay", 0);
			setNbComputerIndex(0);
			model.addAttribute("numberComputerToShow", 10);
			setNbToShow(10);
			loadComputerController(model);
			loadCompanyController(model);
		}
	}
	
	public void loadParameter(Model model) {
		model.addAttribute("beginComputerDisplay",nbComputerIndex);
		model.addAttribute("numberComputerToShow", nbToShow);
	}
	
	public void loadComputerController(Model model) {
		logger.debug("Load Computer");
		loadComputer();
		model.addAttribute("count", getCountComputerCount());
		model.addAttribute("listComputer",getListComputer());
	}
	
	public void loadCompanyController(Model model) {
		loadCompany();
		model.addAttribute("listCompany", getListCompany());
	}
	
	public void changeAttributs(String beginComputerDisplay, String numberComputerToShow, Model model) {
		if(beginComputerDisplay != null ) {
			try {
				logger.debug("nomberComputerToShow : "+ numberComputerToShow);
				int beginComputerDisplay2 = Integer.parseInt(beginComputerDisplay);
				setNbComputerIndex(beginComputerDisplay2);
				model.addAttribute("beginComputerDisplay", beginComputerDisplay);
			}catch(NumberFormatException e) {
				logger.warn("beginComputerDisplay can't be parsed" + e);
			}
		}
		if(numberComputerToShow != null ) {
			try {
				int numberComputerToShow2 = Integer.parseInt(numberComputerToShow);
				setNbToShow(numberComputerToShow2);
				model.addAttribute("numberComputerToShow",numberComputerToShow2);
			}catch(NumberFormatException e) {
				logger.warn("numberComputerToShow can't be parsed"+ e);
			}
		}
	}
	
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
