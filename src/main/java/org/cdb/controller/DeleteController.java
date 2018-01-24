package org.cdb.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.cdb.security.PasswordVerification;
import org.cdb.service.UtilitaryService;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class DeleteController {

	private static DeleteController deleteController = new DeleteController();
	private ArrayList<Integer> listIdComputer = new ArrayList<>();
	private UtilitaryService utilitaryService;
	private ComputerServiceImplementation computerService;
	private Logger logger = (Logger) LoggerFactory.getLogger("DeleteController");
	private String password;
	private PasswordVerification passwordVerification = PasswordVerification.getInstance();
	
	public static DeleteController getInstance() {
		return deleteController;
	}
	
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
		utilitaryService = UtilitaryService.getInstance();
		computerService = utilitaryService.getInstanceComputerService();
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
