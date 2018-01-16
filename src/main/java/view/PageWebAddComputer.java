package view;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import dao.DaoFactory;
import dao.daoImplementation.ComputerDaoImplementation;
import dto.Dto;
import model.Computer;
import security.PasswordVerification;
import service.serviceImplementation.ComputerServiceImplementation;

public class PageWebAddComputer {
	
	private static PageWebAddComputer pageWebAddComputer = new PageWebAddComputer();
	private static Logger logger = (Logger) LoggerFactory.getLogger("PageWebAddComputer");
	private static Dto dto;
	private PasswordVerification passwordVerification = PasswordVerification.getInstance();
	
	public static PageWebAddComputer getInstance(HttpServletRequest request) {
		dto = Dto.getInstance(request);
		return pageWebAddComputer;
	}
	
	public boolean addComputerDb(HttpServletRequest request) {
		Computer computer = parseComputer(request);
		ComputerDaoImplementation computerDao = new ComputerDaoImplementation(DaoFactory.getInstance());
		ComputerServiceImplementation computerService =ComputerServiceImplementation.getInstance(computerDao);
		if(computer != null && passwordVerification.passwordIsCorrect(request.getParameter("password"))) {
			if(computer.getName()!=null && computer.getIntroduced()!=null && computer.getDiscontinued()!=null && computer.getCompany_id() !=null ) {
				computerService.create(computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), Integer.parseInt(computer.getCompany_id().toString()));
				return true;
			}
		}else if( passwordVerification.passwordIsCorrect(request.getParameter("password"))) {
			logger.info("Password invalide");
		}
		return false;
	}
	
	public Computer parseComputer(HttpServletRequest request) {
		Computer computer = new Computer();
		if( request.getParameter("computerName") != "" ) {
			computer.setName(request.getParameter("computerName"));
			logger.debug(computer.toString());
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
	
	public boolean inputIsCorrect(HttpServletRequest request) {
		return false;
	}
}
