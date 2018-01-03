package ui;

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
	
	public CommandLineInterface() {
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
		System.out.println("	6) Quit");
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
				quit();
				break;
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
		String input = readUserInput();
		int inputInt = Integer.parseInt(input);
		Computer computer = this.computerDaoImplementation.find(inputInt);
		System.out.println(computer);
	}
	
	public void updateAComputer() {
		System.out.println("\nEntrer the computer's id you want to update : ");
		String input = readUserInput();
		int inputInt = Integer.parseInt(input);
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
		String input = readUserInput();
		int inputInt = Integer.parseInt(input);
		determineTheUpdateChoise(inputInt, idComputer);
		
	}
	
	public void determineTheUpdateChoise(int input, int idComputer) {
		switch (input) {
			case 1 :
				updateName(idComputer);
				break;
			case 2 :
				updateIntroduced();
				break;
			case 3 :
				updateDiscontinued();
				break;
			case 4 :
				upDateCompanyId();
				break;
		}
	}
	
	public void updateName(int idComputer) {
		System.out.println("Enter the new name : ");
		String name = readUserInput();
		this.computerDaoImplementation.updateName(idComputer, name);
	}
	
	public void updateIntroduced() {
		
	}
	
	public void updateDiscontinued() {
		
	}
	
	public void upDateCompanyId() {
		
	}
	
	public void deleteAComputer() {
		
	}
	
	public void quit() {
		this.notQuit = false;
	}
}
