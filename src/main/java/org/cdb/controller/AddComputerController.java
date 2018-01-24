package org.cdb.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.cdb.dao.DaoFactory;
import org.cdb.dao.daoImplementation.ComputerDaoImplementation;
import org.cdb.dto.Dto;
import org.cdb.model.Computer;
import org.cdb.security.PasswordVerification;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class AddComputerController {
	
	private static AddComputerController pageWebAddComputer = new AddComputerController();
	private static Logger logger = (Logger) LoggerFactory.getLogger("PageWebAddComputer");
	private static Dto dto;
	private PasswordVerification passwordVerification = PasswordVerification.getInstance();
	
	public static AddComputerController getInstance(HttpServletRequest request) {
		dto = Dto.getInstance("",request);
		return pageWebAddComputer;
	}
	
	public boolean addComputerDb(HttpServletRequest request) {
		Optional<Computer> computer = parseComputer(request);
		ComputerDaoImplementation computerDao = new ComputerDaoImplementation(DaoFactory.getInstance());
		ComputerServiceImplementation computerService =ComputerServiceImplementation.getInstance(computerDao);
		if(computer.isPresent() && passwordVerification.passwordIsCorrect(request.getParameter("password"))) {
			if(allFieldsAreCompleted(computer.get()) ) {
				computerService.create(computer.get());
				return true;
			}
		}else if( !passwordVerification.passwordIsCorrect(request.getParameter("password"))) {
			logger.info("Password invalide");
		}
		return false;
	}
	
	public boolean allFieldsAreCompleted(Computer computer) {
		return (computer.getName()!=null && computer.getIntroduced()!=null && computer.getDiscontinued()!=null && computer.getCompany_id() !=null);
	}
	
	public Optional<Computer> parseComputer(HttpServletRequest request) {
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
			return Optional.of(computer);
		}
		return Optional.empty();
	}
	
	public boolean inputIsCorrect(HttpServletRequest request) {
		return false;
	}
}
