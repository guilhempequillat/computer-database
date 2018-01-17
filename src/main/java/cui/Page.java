package cui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import dao.DaoFactory;
import dao.daoInterface.CompanyDao;
import dao.daoInterface.ComputerDao;
import model.Company;
import model.Computer;
import service.serviceImplementation.CompanyServiceImplementation;
import service.serviceImplementation.ComputerServiceImplementation;
import service.serviceInterface.CompanyService;

public class Page {
	
	private CommandLineInput commandLineInput;
	private DaoFactory daoFactory;
	private CompanyDao companyDao;
	private ComputerDao computerDao;
	private boolean notQuit = true; 
	private Logger logger = (Logger) LoggerFactory.getLogger("Page");
	private CompanyServiceImplementation companyService;
	private ComputerServiceImplementation computerService;
	
	public Page () {
		this.daoFactory = DaoFactory.getInstance();
		this.commandLineInput = new CommandLineInput();
		this.companyDao = (CompanyDao) this.daoFactory.getCompanyDao();
		this.computerDao = (ComputerDao) this.daoFactory.getComputerDao();
		this.companyService = CompanyServiceImplementation.getInstance(companyDao);
		this.computerService = ComputerServiceImplementation.getInstance(computerDao);
		commandLineInterfaceWorking();
	}
	
	public void menu() {
		System.out.println("-------------------------------------");
		System.out.println("|                           	    |");
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
			executeChoice(input);
		}
	}
	
	public void executeChoice(String input) {
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
		ArrayList<Computer> computers = this.computerService.findAll();
		for(int i = 0 ; i < computers.size() ; i++) {
			System.out.println(computers.get(i));
		}
	}
	
	public void listCompanies() {
		ArrayList<Company> companies = this.companyService.findAll();
		for(int i = 0 ; i < companies.size() ; i++) {
			System.out.println(companies.get(i));
		}
	}
	
	public void showComputerDetails() {
		System.out.println("\nEntrer the computer's id you want to show : ");
		int inputInt = commandLineInput.readUserInputInt();
		Computer computer = this.computerService.find(inputInt);
		if(computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("\nThere is no computer for this id");
		}
	}
	
	public void updateAComputer() {
		System.out.println("\nEntrer the computer's id you want to update : ");
		int inputInt = commandLineInput.readUserInputInt();
		Computer computer = this.computerService.find(inputInt);
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
		this.computerService.updateName(idComputer, name);
	}
	
	public void updateIntroduced(int idComputer) {
		LocalDate localDate = commandLineInput.readUserInputDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		this.computerService.updateIntroduced(idComputer, localDate);
	}
	
	
	
	public void updateDiscontinued(int idComputer) {
		LocalDate localDate = commandLineInput.readUserInputDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		this.computerService.updateDiscontinued(idComputer, localDate);
	}
	
	public void updateCompanyId(int idComputer) {
		System.out.println("\nEnter the company id :");
		int idCompany = commandLineInput.readUserInputInt();
		this.computerService.updateCompany(idComputer, idCompany);
	}
	
	public void deleteAComputer() {
		System.out.println("\nPlease entrer the computer's id : ");
		int idComputer = commandLineInput.readUserInputInt();
		this.computerService.delete(idComputer);
	}
	
	public void createAComputer() {
		boolean inputCorrect = false;
		java.sql.Date introducedDate, discontinuedDate;
		String name;
		int idCompany;
		System.out.println("\nPlease enter the Computer's name :");
		LocalDate introducedLocalDate;
		LocalDate discontinuedLocalDate;
		name = commandLineInput.readUserInput();
		do {
			System.out.println("\nPlease enter the Introduced date : ");
			
			introducedLocalDate = commandLineInput.readUserInputDate();
			introducedDate = java.sql.Date.valueOf(introducedLocalDate);
			System.out.println("\nPlease enter the Discontined date : ");
			discontinuedLocalDate = commandLineInput.readUserInputDate();
			discontinuedDate = java.sql.Date.valueOf(discontinuedLocalDate);
			System.out.println("\nPlease enter the Company's id : ");
			if(introducedLocalDate.compareTo(discontinuedLocalDate) == -1) {
				inputCorrect = true;
			} else {
				inputCorrect = false;
			}
		}while( !inputCorrect );
		idCompany = commandLineInput.readUserInputInt();
		this.computerService.create( name, introducedLocalDate, discontinuedLocalDate, idCompany );
	}
	
	public void quit() {
		this.notQuit = false;
		this.commandLineInput.closeScanner();
	}
}
