package org.cdb.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.cdb.security.PasswordVerification;
import org.cdb.service.UtilitaryService;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Component
public class DeleteController {
	
	@Autowired
	private ComputerServiceImplementation computerService;
	@Autowired
	private PasswordVerification passwordVerification;
	
	private ArrayList<Integer> listIdComputer = new ArrayList<>();
	private UtilitaryService utilitaryService;
	private Logger logger = (Logger) LoggerFactory.getLogger("DeleteController");
	private String password;
	
	public void loadListId(HttpServletRequest request) {
		String[] listComputerIdString = request.getParameter("selection").split(",");
		for(String computerIdString : listComputerIdString ) {
			listIdComputer.add(Integer.parseInt(computerIdString));
		}
	}
	
	public void loadPassword(HttpServletRequest request) {
		password = request.getParameter("password");
	}
	
	
	public void deleteComputerDb() {
		if( passwordVerification.passwordIsCorrect(password)) {
			for( Integer id : listIdComputer ) {
				computerService.delete(id);
				logger.debug(id.toString());
			}
		} else {
			logger.info("The password is not correct");
		}	
	}
}
