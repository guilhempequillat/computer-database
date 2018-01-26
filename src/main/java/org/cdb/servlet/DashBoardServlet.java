package org.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cdb.controller.PaginationDashBoardController;
import org.cdb.springConfiguration.MainConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.qos.logback.classic.Logger;


@Controller
@ComponentScan("org.cdb.controller") 
@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	
	private Logger logger = (Logger) LoggerFactory.getLogger("DashBoardServlet");
    
	@Autowired
    private PaginationDashBoardController pagination;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		managePagination(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/dashboard.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loadFilterParameter(request);
		managePagination(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/dashboard.jsp" ).forward( request, response );
	}
	
	public void loadFilterParameter(HttpServletRequest request) {
		boolean filterIsUsed = false;
		if(request.getParameter("filterName") != null && request.getParameter("filterName") != "") {
			pagination.setFilterName(request.getParameter("filterName"));
			request.getSession().setAttribute("filterName", request.getParameter("filterName"));
			pagination.setFilterState(true);
			filterIsUsed = true;
		}
		if(request.getParameter("filterIntroduced") != null && request.getParameter("filterIntroduced") != "") {
			pagination.setFilterIntroduced(request.getParameter("filterIntroduced"));
			request.getSession().setAttribute("filterIntroduced", request.getParameter("filterIntroduced"));
			pagination.setFilterState(true);
			filterIsUsed = true;
		}
		if(request.getParameter("filterDiscontinued") != null && request.getParameter("filterDiscontinued") != "") {
			pagination.setFilterDiscontined(request.getParameter("filterDiscontinued"));
			request.getSession().setAttribute("filterDiscontinued", request.getParameter("filterDiscontinued"));
			pagination.setFilterState(true);
			filterIsUsed = true;
		}
		if(request.getParameter("filterCompany") != null && request.getParameter("filterCompany") != "") {
			pagination.setFilterCompany(request.getParameter("filterCompany"));
			request.getSession().setAttribute("filterCompany", request.getParameter("filterCompany"));
			pagination.setFilterState(true);
			filterIsUsed = true;
		}
		if( !filterIsUsed ) {
			pagination.setFilterState(false);
			pagination.setFilterDiscontined(request.getParameter("filterDiscontinued"));
			request.getSession().setAttribute("filterDiscontinued", "");
			request.getSession().setAttribute("filterCompany", "");
			request.getSession().setAttribute("filterIntroduced", "");
			request.getSession().setAttribute("filterName", "");
			pagination.setFilterName("");
			pagination.setFilterIntroduced("");
			pagination.setFilterDiscontined("");
			pagination.setFilterCompany("");
		}
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
				loadCompany(request);
			}
		} else {
			logger.debug("Init Pagination");
			request.getSession().setAttribute("beginComputerDisplay", 0);
			pagination.setNbComputerIndex(0);
			request.getSession().setAttribute("numberComputerToShow", 10);
			pagination.setNbToShow(10);
			loadComputer(request);
			loadCompany(request);
		}
	}
	
	public void loadComputer(HttpServletRequest request) {
		logger.debug("Load Computer");
		pagination.loadComputer();
		request.getSession().setAttribute("count", pagination.getCountComputerCount());
		request.getSession().setAttribute("listComputer", pagination.getListComputer());
	}
	
	public void loadCompany(HttpServletRequest request) {
		logger.debug("Load Company");
		pagination.loadCompany();
		request.getSession().setAttribute("listCompany", pagination.getListCompany());
	}
	
	public void changeAttributs(HttpServletRequest request) {
		if(request.getParameter("beginComputerDisplay") != null ) {
			try {
				int beginComputerDisplay = Integer.parseInt(request.getParameter("beginComputerDisplay"));
				pagination.setNbComputerIndex(beginComputerDisplay);
				request.getSession().setAttribute("beginComputerDisplay", beginComputerDisplay);
			}catch(NumberFormatException e) {
				logger.warn("beginComputerDisplay can't be parsed" + e);
			}
		}
		if(request.getParameter("numberComputerToShow") != null ) {
			try {
				int numberComputerToShow = Integer.parseInt(request.getParameter("numberComputerToShow"));
				pagination.setNbToShow(numberComputerToShow);
				request.getSession().setAttribute("numberComputerToShow", numberComputerToShow);
			}catch(NumberFormatException e) {
				logger.warn("numberComputerToShow can't be parsed"+ e);
			}
		}
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}
}
