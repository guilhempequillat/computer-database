package ui;

import java.text.DateFormat;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import com.mysql.jdbc.Connection;
import DAO.CompanyDao;
import DAO.CompanyDaoImplementation;
import DAO.ComputerDao;
import DAO.ComputerDaoImplementation;
import DAO.DAOFactory;
import model.Company;
import model.Computer;

public class CommandLineInterface {
	
	private DAOFactory daoFactory;
	private CompanyDaoImplementation companyDaoImplementation;
	private ComputerDaoImplementation computerDaoImplementation;
	private boolean notQuit = true; 
	private Logger logger = (Logger) LoggerFactory.getLogger("Commande line interface");
	
	public CommandLineInterface() {
		logger.info("test");
		this.daoFactory = DAOFactory.getInstance();
		this.companyDaoImplementation = (CompanyDaoImplementation) this.daoFactory.getCompanyDao();
		this.computerDaoImplementation = (ComputerDaoImplementation) this.daoFactory.getComputerDao();
		commandLineInterfaceWorking();
	}
	
	public void menuPresentation() {
		System.out.println("\nWhat do you want to do?");
		System.out.println("	1) List computers");
		System.out.println("	2) List companies");
		System.out.println("	3) Show computer details");
		System.out.println("	4) Update a computer");
		System.out.println("	5) Delete a computer");
		System.out.println("	6) Create a computer");
		System.out.println("	7) Quit");
	}
	
	public String readUserInput() {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		return input;
	}
	
	public void commandLineInterfaceWorking() {
		while(this.notQuit) {
			menuPresentation();
			String input = readUserInput();
			executeChoise(input);
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
		int inputInt = readUserInputInt();
		Computer computer = this.computerDaoImplementation.find(inputInt);
		if(computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("\nThere is no computer for this id");
		}
	}
	
	public void updateAComputer() {
		System.out.println("\nEntrer the computer's id you want to update : ");
		int inputInt = readUserInputInt();
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
		int inputInt = readUserInputInt();
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
		String name = readUserInput();
		this.computerDaoImplementation.updateName(idComputer, name);
	}
	
	public void updateIntroduced(int idComputer) {
		LocalDate localDate = readUserInputDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		this.computerDaoImplementation.updateIntroduced(idComputer, sqlDate);
	}
	
	public LocalDate readUserInputDate() {
		System.out.println("\nEnter the Year : ");
		int year = readUserYear();
		System.out.println("\nEnter the Month : ");
		int month = readUserMonth();
		System.out.println("\nEnter the Day : ");
		int day = readUserDay();
		LocalDate localDate = LocalDate.of(year, month, day);
		return localDate;
		
	}
	
	public void updateDiscontinued(int idComputer) {
		LocalDate localDate = readUserInputDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		this.computerDaoImplementation.updateDiscontinued(idComputer, sqlDate);
	}
	
	public void updateCompanyId(int idComputer) {
		System.out.println("\nEnter the company id :");
		int idCompany = readUserInputInt();
		this.computerDaoImplementation.updateCompany(idComputer, idCompany);
	}
	
	public void deleteAComputer() {
		System.out.println("\nPlease entrer the computer's id : ");
		int idComputer = readUserInputInt();
		this.computerDaoImplementation.delete(idComputer);
	}
	
	public void createAComputer() {
		System.out.println("\nPlease enter the Computer's name :");
		String name = readUserInput();
		System.out.println("\nPlease enter the Introduced date : ");
		LocalDate introducedLocalDate = readUserInputDate();
		java.sql.Date introducedDate = java.sql.Date.valueOf(introducedLocalDate);
		System.out.println("\nPlease enter the Discontined date : ");
		LocalDate discontinuedLocalDate = readUserInputDate();
		java.sql.Date discontinuedDate = java.sql.Date.valueOf(discontinuedLocalDate);
		System.out.println("\nPlease enter the Company's id : ");
		int idCompany = readUserInputInt();
		this.computerDaoImplementation.create(name, introducedDate, discontinuedDate, idCompany);
	}
	
	public void quit() {
		this.notQuit = false;
	}
	
	public int readUserInputInt() {
		boolean correctInput = true;
		int input=0;
		do {
			try {
				input = Integer.parseInt(readUserInput());
				correctInput = true;
			}catch( NumberFormatException e ) {
				correctInput = false;
				System.out.println("\nPlease insert a number");
			}
		} while ( !correctInput );
		return input;
	}

	public int readUserYear() {
		boolean correctInput = true;
		int input = 1;
		do {
			input = readUserInputInt();
			if(input > 1970 && input < 2038) {
				correctInput = true;
			} else {
				correctInput = false;
				System.out.println("Invalid Year");
			}
		} while(!correctInput);
		return input;
	}
	
	public int readUserMonth() {
		boolean correctInput = true;
		int input = 1;
		do {
			input = readUserInputInt();
			if(input > 0 && input < 13) {
				correctInput = true;
			} else {
				correctInput = false;
				System.out.println("Invalid Month");
			}
		} while(!correctInput);
		return input;
	}
	
	public int readUserDay() {
		boolean correctInput = true;
		int input = 1;
		do {
			input = readUserInputInt();
			if(input > 0 && input < 32) {
				correctInput = true;
			} else {
				correctInput = false;
				System.out.println("Invalid Day");
			}
		} while(!correctInput);
		return input;
	}
}
