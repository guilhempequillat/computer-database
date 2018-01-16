package dto;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import model.Company;
import model.Computer;
import service.UtilitaryService;
import service.serviceImplementation.CompanyServiceImplementation;
import service.serviceImplementation.ComputerServiceImplementation;

public class Dto {
	private static ArrayList<Computer> computers = new ArrayList<>();
	private static ArrayList<Company> companies = new ArrayList<>();
	private static Dto dto = new Dto();
	private static CompanyServiceImplementation companyServiceImplementation;
	private static ComputerServiceImplementation computerServiceImplementation;
	private static UtilitaryService utilitaryService = UtilitaryService.getInstance();
	
	
	public static Dto getInstance(HttpServletRequest request) {
		loadData(request);
		return dto;
	}
	
	public static void loadData(HttpServletRequest request) {
		computerServiceImplementation = utilitaryService.getInstanceComputerService();
		ArrayList<Computer> computers = computerServiceImplementation.findAll();
		request.getSession().setAttribute("listComputer", computers);
		companyServiceImplementation = utilitaryService.getInstanceCompanyService();
		ArrayList<Company> companies = companyServiceImplementation.findAll();
		request.getSession().setAttribute("listCompany", companies);
	}
	
	public ArrayList<Computer> getComputers() {
		return computers;
	}
	public void setComputers(ArrayList<Computer> computers) {
		this.computers = computers;
	}
	public ArrayList<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(ArrayList<Company> companies) {
		this.companies = companies;
	}
}
