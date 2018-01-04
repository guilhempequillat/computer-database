package ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

import DAO.CompanyDaoImplementation;
import DAO.ComputerDaoImplementation;
import DAO.DAOFactory;
import ch.qos.logback.classic.Logger;
import model.Company;
import model.Computer;

public class Page {
	
	private static final int NUMBER_IMAGES_PER_PAGES = 10;
	private CommandLineInterface commandLineInterface;
	private CommandLineInput commandLineInput;
	private DAOFactory daoFactory;
	private CompanyDaoImplementation companyDaoImplementation;
	private ComputerDaoImplementation computerDaoImplementation;
	private boolean notQuit = true; 
	private Logger logger = (Logger) LoggerFactory.getLogger("Page");
	
	public Page () {
		this.daoFactory = DAOFactory.getInstance();
		this.commandLineInput = new CommandLineInput();
		this.companyDaoImplementation = (CompanyDaoImplementation) this.daoFactory.getCompanyDao();
		this.computerDaoImplementation = (ComputerDaoImplementation) this.daoFactory.getComputerDao();
		commandLineInterfaceWorking();
		
	}
	
	public void menu() {
		System.out.println("-------------------------------------");
		System.out.println("|               MENU           	    |");
		System.out.println("|                            	    |");
		System.out.println("|   1- List computers        	    |");
		System.out.println("|   2- List companies        	    |");
		System.out.println("|   3- Show computer details 	    |");
		System.out.println("|   4- Update a computer     	    |");
		System.out.println("|   5- Delete a computer     	    |");
		System.out.println("|   6- Create a computer     	    |");
		System.out.println("|   7- Quit                  	    |");
		System.out.println("|                           	    |");
		System.out.println("-------------------------------------");
	}
	
	public void commandLineInterfaceWorking() {
		while(this.notQuit) {
			menu();
			String input = commandLineInput.readUserInput();
			executeChoise(input);
			try {
			Runtime.getRuntime().exec("clear" );
			}catch(IOException e) {
				logger.error("IOExption");
			}
		}
	}
	
	public void executeChoise(String input) {
		switch (input) {
			case "1" :
				listComputer();
				break;
			case "2" :
				listCompanies();
				break;
			case "3" :
				showComputerDetails();
				break;
			case "4" :
				updateAComputer();
				break;
			case "5" :
				deleteAComputer();
				break;
			case "6" :
				createAComputer();
				break;
			case "7" :
				quit();
				break;
			default : 
				System.out.println("\nInsert a number between 1 and 7");
		}
	}
	
	public void listComputer() {
		ArrayList<Computer> computers = this.computerDaoImplementation.findAll();
		for(int i = 0 ; i < computers.size() ; i++) {
			System.out.println(computers.get(i));
		}
	}
	
	public void listCompanies() {
		ArrayList<Company> companies = this.companyDaoImplementation.findAll();
		for(int i = 0 ; i < companies.size() ; i++) {
			System.out.println(companies.get(i));
		}
	}
	
	public void showComputerDetails() {
		System.out.println("\nEntrer the computer's id you want to show : ");
		int inputInt = commandLineInput.readUserInputInt();
		Computer computer = this.computerDaoImplementation.find(inputInt);
		if(computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("\nThere is no computer for this id");
		}
	}
	
	public void updateAComputer() {
		System.out.println("\nEntrer the computer's id you want to update : ");
		int inputInt = commandLineInput.readUserInputInt();
		Computer computer = this.computerDaoImplementation.find(inputInt);
		System.out.println("\n"+computer);
		chooseParameterToUpdate(inputInt);		
	}
	
	public void chooseParameterToUpdate(int idComputer) {
		System.out.println("Which parameter you want to update ?");
		System.out.println("	1) Name"); 
		System.out.println("	2) Introduced"); 
		System.out.println("	3) Discontinued"); 
		System.out.println("	4) Company id"); 
		int inputInt = commandLineInput.readUserInputInt();
		determineTheUpdateChoise(inputInt, idComputer);
		
	}
	
	public void determineTheUpdateChoise(int input, int idComputer) {
		switch (input) {
			case 1 :
				updateName(idComputer);
				break;
			case 2 :
				updateIntroduced(idComputer);
				break;
			case 3 :
				updateDiscontinued(idComputer);
				break;
			case 4 :
				updateCompanyId(idComputer);
				break;
			default : 
				System.out.println("\nInsert a number between 1 and 4");
		}
	}
	
	public void updateName(int idComputer) {
		System.out.println("Enter the new name : ");
		String name = commandLineInput.readUserInput();
		this.computerDaoImplementation.updateName(idComputer, name);
	}
	
	public void updateIntroduced(int idComputer) {
		LocalDate localDate = commandLineInput.readUserInputDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		this.computerDaoImplementation.updateIntroduced(idComputer, sqlDate);
	}
	
	
	
	public void updateDiscontinued(int idComputer) {
		LocalDate localDate = commandLineInput.readUserInputDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		this.computerDaoImplementation.updateDiscontinued(idComputer, sqlDate);
	}
	
	public void updateCompanyId(int idComputer) {
		System.out.println("\nEnter the company id :");
		int idCompany = commandLineInput.readUserInputInt();
		this.computerDaoImplementation.updateCompany(idComputer, idCompany);
	}
	
	public void deleteAComputer() {
		System.out.println("\nPlease entrer the computer's id : ");
		int idComputer = commandLineInput.readUserInputInt();
		this.computerDaoImplementation.delete(idComputer);
	}
	
	public void createAComputer() {
		System.out.println("\nPlease enter the Computer's name :");
		String name = commandLineInput.readUserInput();
		System.out.println("\nPlease enter the Introduced date : ");
		LocalDate introducedLocalDate = commandLineInput.readUserInputDate();
		java.sql.Date introducedDate = java.sql.Date.valueOf(introducedLocalDate);
		System.out.println("\nPlease enter the Discontined date : ");
		LocalDate discontinuedLocalDate = commandLineInput.readUserInputDate();
		java.sql.Date discontinuedDate = java.sql.Date.valueOf(discontinuedLocalDate);
		System.out.println("\nPlease enter the Company's id : ");
		int idCompany = commandLineInput.readUserInputInt();
		this.computerDaoImplementation.create(name, introducedDate, discontinuedDate, idCompany);
	}
	
	public void quit() {
		this.notQuit = false;
	}
}
