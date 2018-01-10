package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import model.Company;
import model.Computer;
import ch.qos.logback.classic.Logger;
import dao.DaoFactory;
import dao.daoInterface.CompanyDao;
import dao.daoInterface.ComputerDao;
import service.UtilitaryService;
import service.serviceImplementation.CompanyServiceImplementation;
import service.serviceImplementation.ComputerServiceImplementation;

/**
 * Servlet implementation class ShowResultServlet
 */
@WebServlet("/ShowResultServlet")
public class ShowResultServlet extends HttpServlet {
	
	private ComputerServiceImplementation computerServiceImplementation;
	private CompanyServiceImplementation companyServiceImplementation;
	private UtilitaryService utilitaryService = new UtilitaryService();
	private Logger logger = (Logger) LoggerFactory.getLogger("ShowResultServlet");

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = (String) request.getParameter("action");
		logger.debug(action);
		actionChoice(action, request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public void actionChoice(String action,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		switch(action) {
			case "listComputers":
				listComputer(request, response);
				break;
			case "listCompanies":
				listCompany(request, response);
				break;
			case "computerDetails":

				break;
			case "updateComputer":
				
				break;
			case "deleteComputer":
				
				break;
			case "createComputer":
				
				break;
			default:
				//error : bad user input
		}
	}
	
	public void listComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.computerServiceImplementation = utilitaryService.getInstanceComputerService();
		ArrayList<Computer> computers = this.computerServiceImplementation.findAll();
		request.getSession().setAttribute("listComputer", computers);
		request.getSession().setAttribute("action", "listCompany");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/result.jsp" ).forward( request, response );
	}
	
	public void listCompany(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		this.companyServiceImplementation = utilitaryService.getInstanceCompanyService();
		request.getSession().setAttribute("listCompany", this.companyServiceImplementation.findAll());
		request.getSession().setAttribute("action", "listCompany");
		this.getServletContext().getRequestDispatcher( "/WEB-INF/result.jsp" ).forward( request, response );
	}
	
	public void servicesInistalisation() {
		DaoFactory daoFactory;
		CompanyDao companyDao;
		ComputerDao computerDao;
	}

}
