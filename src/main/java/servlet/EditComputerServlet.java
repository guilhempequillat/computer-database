package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import dao.DaoFactory;
import dao.daoImplementation.ComputerDaoImplementation;
import dao.daoInterface.ComputerDao;
import model.Computer;
import security.PasswordVerification;
import service.serviceImplementation.ComputerServiceImplementation;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
	
	private Long computerId;
	private Computer computer;
	private static Logger logger = (Logger) LoggerFactory.getLogger("EditComputerServlet");
	private PasswordVerification passwordVerification = PasswordVerification.getInstance();
	   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(request.getRemoteAddr());
		loadParameter(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/editComputer.jsp" ).forward( request, response );
	}  
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		computerId = (Long) request.getSession().getAttribute("idComputer");
		computer = parseComputer(request);
		updateDb(request);
		this.getServletContext().getRequestDispatcher( "/dashboard" ).forward( request, response );
	}
	
	public void updateDb(HttpServletRequest request) {
		Computer computer = parseComputer(request);
		if (getComputerId() != (-1L) && inputAreValide() && passwordVerification.passwordIsCorrect(request.getParameter("password"))) {
			ComputerDaoImplementation computerDao = new ComputerDaoImplementation(DaoFactory.getInstance());
			ComputerServiceImplementation computerService =ComputerServiceImplementation.getInstance(computerDao);
			Pattern pattern = Pattern.compile("<");
			//System.out.println(computer.getName().matches("<"));
//			if(computer.getName() != null && !(Pattern.matches("<", computer.getName()) ) ) {
//				System.out.println(Pattern.matches("#<#", computer.getName()));
//				computerService.updateName(Integer.parseInt(computer.getId().toString()), computer.getName());
//			}
			if(computer.getIntroduced() != null) {
				computerService.updateIntroduced(Integer.parseInt(computer.getId().toString()), computer.getIntroduced());
			}
			if(computer.getDiscontinued() != null) {
				computerService.updateDiscontinued(Integer.parseInt(computer.getId().toString()), computer.getDiscontinued());
			}
			if(computer.getCompany_id() != null) {
				computerService.updateCompany(Integer.parseInt(computer.getId().toString()), Integer.parseInt(computer.getCompany_id().toString()));
			}
		}else if( !passwordVerification.passwordIsCorrect(request.getParameter("password"))) {
			logger.info("Password invalide");
		}
	}
	
	public boolean inputAreValide() {
		if(computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			logger.debug(computer.getIntroduced().compareTo(computer.getDiscontinued()) +"&&&&&&&&&");
			if(computer.getIntroduced().compareTo(computer.getDiscontinued()) == -10) {
				return true;
			} else {
				logger.info("The dates are invalide");
				return false;
			}
		}
		return true;
	}
	
	public Computer parseComputer(HttpServletRequest request) {
		Computer computer = new Computer();
		computer.setId(getComputerId());
		if( ! computer.getId().equals( (Long) (-1L))) {
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
			return computer;
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
		if( listComputer != null ) {
			return listComputer;
		}else {
			logger.info("No computer in session");
			return null;
		}
	}
	
	public Long getComputerId() {
		return computerId;
	}
}
