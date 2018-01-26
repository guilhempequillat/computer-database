package org.cdb.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cdb.model.Computer;
import org.cdb.security.PasswordVerification;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.qos.logback.classic.Logger;

/**
 * Servlet implementation class EditComputerServlet
 */
@Controller
@WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
	
	private Long computerId;
	private Computer computer;
	private static Logger logger = (Logger) LoggerFactory.getLogger("EditComputerServlet");
	@Autowired
	private PasswordVerification passwordVerification;
	@Autowired
	private ComputerServiceImplementation computerService;
	   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getRemoteAddr().toString().equals("10.0.1.16")) {
			this.getServletContext().getRequestDispatcher( "/WEB-INF/view/walid.jsp" ).forward( request, response );
		}else {
			loadParameter(request);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/view/editComputer.jsp" ).forward( request, response );
		}
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
			if(computer.getName() != null ) {
				computerService.updateName(Integer.parseInt(computer.getId().toString()), computer.getName());
			}
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
			if(computer.getIntroduced().compareTo(computer.getDiscontinued()) < 0) {
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
		if(getComputerEdited(request, id).isPresent()) {
			request.getSession().setAttribute("computer", getComputerEdited(request, id).get());
		}
	}
	
	public Optional<Computer> getComputerEdited(HttpServletRequest request, Long idComputer) {
		if( idComputer != -1L ) {
			Optional<ArrayList<Computer>> listComputer;
			try {
				listComputer = getListComputer(request);
			} catch (NullPointerException e) {
				return Optional.empty();
			}
			if( listComputer != null ) {
				for(Computer computer : listComputer.get()) {
					if(computer.getId().equals(idComputer)) {
						logger.info("Computer found");
						return Optional.of(computer);
					}
				}
				logger.info("Computer not found");
			}
			return Optional.empty();
		}else {
			return Optional.empty();
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
	
	public Optional<ArrayList<Computer>> getListComputer(HttpServletRequest request){
		HttpSession session = request.getSession();
		ArrayList<Computer> listComputer = (ArrayList<Computer>) session.getAttribute("listComputer");
		if( listComputer != null ) {
			return Optional.of(listComputer);
		}else {
			logger.info("No computer in session");
			return Optional.empty();
		}
	}
	
	public Long getComputerId() {
		return computerId;
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
