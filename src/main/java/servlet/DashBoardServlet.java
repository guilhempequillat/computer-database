package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import comparator.ComparatorComputerCompany;
import comparator.ComparatorComputerDiscontinued;
import comparator.ComparatorComputerIntroduced;
import comparator.ComparatorComputerName;
import controller.DashboardPaginationController;
import controller.PaginationDashBoardController;
import model.Company;
import model.Computer;
import service.UtilitaryService;
import service.serviceImplementation.CompanyServiceImplementation;
import service.serviceImplementation.ComputerServiceImplementation;

@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	
	private ComputerServiceImplementation computerServiceImplementation;
	private CompanyServiceImplementation companyServiceImplementation;
	private UtilitaryService utilitaryService = UtilitaryService.getInstance();
	private Logger logger = (Logger) LoggerFactory.getLogger("DashBoardServlet");
    private DashboardPaginationController pageWeb;
    private PaginationDashBoardController pagination = PaginationDashBoardController.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		managePagination(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/dashboard.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageWeb = DashboardPaginationController.searchFilter(request.getParameter("filter"), request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/dashboard.jsp" ).forward( request, response );
	}
	
	public void managePagination(HttpServletRequest request) {
		request.getSession().setAttribute("count",pagination.getCountComputerCount());
		if(request.getParameter("order") != null) {
			pagination.setOrderType(request.getParameter("order"));
			pagination.inverseOrder();
		}
		if(request.getSession().getAttribute("beginComputerDisplay") != null) {
			if(request.getParameter("beginComputerDisplay") != "" || request.getParameter("numberComputerToShow") != "") {
				changeAttributs(request);
				loadComputer(request);
			}
		} else {
			logger.debug("Init Pagination");
			request.getSession().setAttribute("beginComputerDisplay", 0);
			PaginationDashBoardController.setNbComputerIndex(0);
			request.getSession().setAttribute("numberComputerToShow", 10);
			PaginationDashBoardController.setNbToShow(10);
			loadComputer(request);
		}
	}
	
	public void loadComputer(HttpServletRequest request) {
		logger.debug("Load Computer");
		pagination.loadComputer();
		request.getSession().setAttribute("count", pagination.getCountComputerCount());
		request.getSession().setAttribute("listComputer", pagination.getListComputer());
	}
	
	public void changeAttributs(HttpServletRequest request) {
		if(request.getParameter("beginComputerDisplay") != null ) {
			try {
				int beginComputerDisplay = Integer.parseInt(request.getParameter("beginComputerDisplay"));
				PaginationDashBoardController.setNbComputerIndex(beginComputerDisplay);
				request.getSession().setAttribute("beginComputerDisplay", beginComputerDisplay);
			}catch(NumberFormatException e) {
				logger.warn("beginComputerDisplay can't be parsed" + e);
			}
		}
		if(request.getParameter("numberComputerToShow") != null ) {
			try {
				int numberComputerToShow = Integer.parseInt(request.getParameter("numberComputerToShow"));
				PaginationDashBoardController.setNbToShow(numberComputerToShow);
				request.getSession().setAttribute("numberComputerToShow", numberComputerToShow);
			}catch(NumberFormatException e) {
				logger.warn("numberComputerToShow can't be parsed"+ e);
			}
		}
	}
	
	
	public void manageOrder(HttpServletRequest request) {
		if(request.getParameter("order") != null) {
			pageWeb.orderComputers(request.getParameter("order"), request);
		} else {
			if(request.getSession().getAttribute("order") != null) {
				pageWeb.orderComputers(request.getSession().getAttribute("order").toString(), request);
			}else {
				pageWeb.orderComputers( "name" , request);
			}
		}
	}
	
	public void initDisplay(HttpServletRequest request) {
		pageWeb.initDisplay(request);
	}
	
	public void changeDisplay(HttpServletRequest request) {
		pageWeb.changeDisplay(request);
	}
}
