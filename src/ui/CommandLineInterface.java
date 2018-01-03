package ui;

import java.util.Scanner;

import com.mysql.jdbc.Connection;

import DAO.CompanyDao;
import DAO.CompanyDaoImplementation;
import DAO.DAOFactory;

public class CommandLineInterface {
	
	private DAOFactory daoFactory;
	private CompanyDaoImplementation companyDaoImplementation;
	
	public CommandLineInterface() {
		this.daoFactory = DAOFactory.getInstance();
		this.companyDaoImplementation = (CompanyDaoImplementation) this.daoFactory.getCompanyDao();
		this.companyDaoImplementation.findAll();
	}
	
	public void menuPresentation() {
		System.out.println("What do you want to do?");
		System.out.println("1) List computers");
		System.out.println("2) List companies");
		System.out.println("3) Show computer details");
		System.out.println("4) Update a computer");
		System.out.println("5) Delete a computer"); 
		System.out.println("6) Quit");
	}
	
	public String readUserInput() {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		return input;
	}
	
	public void commandLineInterfaceWorking() {
		while(true) {
			menuPresentation();
			String input = readUserInput();
		}
	}
	
	public void executeChoise(String input) {
		switch (input) {
			case "1" :
				listComputer();
				break;
			case "2" :
				break;
			case "3" :
				break;
			case "4" :
				break;
			case "5" :
				break;
			case "6" :
				break;
		}
	}
	
	public void listComputer() {
		
	}
}
