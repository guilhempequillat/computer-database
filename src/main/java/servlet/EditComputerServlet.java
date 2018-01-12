package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import dao.daoImplementation.ComputerDaoImplementation;
import dao.daoInterface.ComputerDao;
import model.Computer;
import service.serviceImplementation.ComputerServiceImplementation;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
	
	private Long computerId;
	private static Logger logger = (Logger) LoggerFactory.getLogger("EditComputerServlet");
	   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(request.getRemoteAddr());
		loadParameter(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/editComputer.jsp" ).forward( request, response );
	}  
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		computerId = (Long) request.getSession().getAttribute("idComputer");
		parseComputer(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/editComputer.jsp" ).forward( request, response );
	}
	
	public void updateDb(HttpServletRequest request) {
		Computer computer = parseComputer(request);
		ComputerDaoImplementation computerDao
		ComputerServiceImplementation computerService =ComputerServiceImplementation.getInstance(computerDao);
	}
	
	public Computer parseComputer(HttpServletRequest request) {
		Computer computer = new Computer();
		computer.setId(getComputerId());
		if( getComputerId() != (-1L)) {
			if( request.getParameter("computerName") != "" ) {
				computer.setName(request.getParameter("computerName"));
				logger.debug(computer.toString());
			}
			if( request.getParameter("introduced") != "" ) {
				computer.setIntroduced( LocalDate.parse(request.getParameter("introduced")));
				logger.debug(computer.toString());
			}
			if( request.getParameter("discontinued") != "" ) {
				computer.setDiscontinued(LocalDate.parse(request.getParameter("discontinued")));
				logger.debug(computer.toString());
			}
			if( request.getParameter("companyId") != "" ) {
				computer.setCompany_id(Long.parseLong(request.getParameter("companyId")));
				logger.debug(computer.toString());
			}
		}
		return null;
	}
	
	public void loadParameter(HttpServletRequest request) {
		Long id = loadComputerId(request);
		request.getSession().setAttribute("idComputer", id );
		request.getSession().setAttribute("computer", getComputerEdited(request, id));
	}
	
	public Computer getComputerEdited(HttpServletRequest request, Long idComputer) {
		if( idComputer != -1L ) {
			ArrayList<Computer> listComputer;
			try {
				listComputer = getListComputer(request);
			} catch (NullPointerException e) {
				return null;
			}
			if( listComputer != null ) {
				for(Computer computer : listComputer) {
					if(computer.getId().equals(idComputer)) {
						logger.info("Computer found");
						return computer;
					}
				}
				logger.info("Computer not found");
			}
			return null;
		}else {
			return null;
		}
	}
	
	public Long loadComputerId(HttpServletRequest request) {
		try {
			Long computerId = Long.parseLong(request.getParameter("id"));
			return computerId;
		}catch( NumberFormatException e ) {
			logger.error("The id type mismatch");
		}
		return -1L;		
	}
	
	public ArrayList<Computer> getListComputer(HttpServletRequest request){
		HttpSession session = request.getSession();
		ArrayList<Computer> listComputer = (ArrayList<Computer>) session.getAttribute("listComputer");
		if(listComputer.size() == 0) {
			logger.info("No computer in session");
		}
		return listComputer;
	}
	
	public Long getComputerId() {
		return computerId;
	}
}
