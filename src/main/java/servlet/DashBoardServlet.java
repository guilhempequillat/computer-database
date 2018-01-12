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

/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet("/dashboard")
public class DashBoardServlet extends HttpServlet {
	
	private ComputerServiceImplementation computerServiceImplementation;
	private CompanyServiceImplementation companyServiceImplementation;
	private UtilitaryService utilitaryService = new UtilitaryService();
	private Logger logger = (Logger) LoggerFactory.getLogger("DashBoardServlet");
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listComputer(request, response);
		listCompany(request, response);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/dashboard.jsp" ).forward( request, response );
	}

	public void listComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.computerServiceImplementation = utilitaryService.getInstanceComputerService();
		ArrayList<Computer> computers = this.computerServiceImplementation.findAll();
		request.getSession().setAttribute("listComputer", computers);
		//request.getSession().setAttribute("action", "listCompany");
	}
	
	public void listCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.companyServiceImplementation = utilitaryService.getInstanceCompanyService();
		ArrayList<Company> companies = this.companyServiceImplementation.findAll();
		request.getSession().setAttribute("listCompany", companies);
	}
	
}
