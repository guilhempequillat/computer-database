package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import model.Company;
import model.Computer;
import service.UtilitaryService;
import service.serviceImplementation.CompanyServiceImplementation;
import service.serviceImplementation.ComputerServiceImplementation;
import view.PageWeb;

/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	
	private ComputerServiceImplementation computerServiceImplementation;
	private CompanyServiceImplementation companyServiceImplementation;
	private UtilitaryService utilitaryService = UtilitaryService.getInstance();
	private Logger logger = (Logger) LoggerFactory.getLogger("DashBoardServlet");
    private PageWeb pageWeb;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageWeb = PageWeb.getInstance(request);
		if(request.getSession().getAttribute("beginComputerDisplay") != null) {
			if(request.getParameter("beginComputerDisplay") != "" || request.getParameter("numberComputerToShow") != "") {
				changeDisplay(request);
			}
		} else {
			initDisplay(request);
		}
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/dashboard.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void initDisplay(HttpServletRequest request) {
		pageWeb.initDisplay(request);
	}
	
	public void changeDisplay(HttpServletRequest request) {
		pageWeb.changeDisplay(request);
	}
}
