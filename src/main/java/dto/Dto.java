package dto;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
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
	private static boolean useFilter = false; 
	private static Logger logger = (Logger) LoggerFactory.getLogger("Dto");
	
	
	public static Dto getInstance(String filter ,HttpServletRequest request) {
		if(!useFilter || filter.equals("")) {
			loadData(request);
		} else {
			loadDataFilter(filter, request);
		}
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
	
	public static void loadDataFilter(String filter , HttpServletRequest request) {
		companyServiceImplementation = utilitaryService.getInstanceCompanyService();
		ArrayList<Company> companies = companyServiceImplementation.findAll();
		request.getSession().setAttribute("listCompany", companies);
		
		Optional<ArrayList<Computer>> optionalListComputer = searchBarComputer(request, filter);
				
		ArrayList<Computer> computers = new ArrayList<>();
		if(optionalListComputer.isPresent()) {
			computers = optionalListComputer.get();
		}
		request.getSession().setAttribute("listComputer", computers);
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
	
	public static void setUseFilter(boolean useFilter1) {
		useFilter = useFilter1;
	}
	
	public static Optional<ArrayList<Computer>> searchBarComputer(HttpServletRequest request, String input){
			
		Pattern pattern = Pattern.compile(input.toUpperCase());
		
		computerServiceImplementation = utilitaryService.getInstanceComputerService();
		ArrayList<Computer> listComputer = computerServiceImplementation.findAll();
		
		Optional<ArrayList<Computer>> optionalListComputer;
		
		ArrayList<Computer> listResult = new ArrayList<>(); 
		boolean match;
		for(int i = 0 ; i<listComputer.size() ; i++) {
			match = false;
			if(listComputer.get(i).getName() != null) {
				Matcher matcher = pattern.matcher(listComputer.get(i).getName().toUpperCase());
				match = matcher.find();
			}
			if(!match && listComputer.get(i).getCompany() != null) {
				if( listComputer.get(i).getCompany().getName() != null) {
					Matcher matcher = pattern.matcher(listComputer.get(i).getCompany().getName().toUpperCase());
					match = matcher.find();
				}
			}			
			if( match ) {
				logger.debug(listComputer.get(i).toString());
				listResult.add(listComputer.get(i));
			}
		}
		if(listResult.isEmpty()) {
			optionalListComputer = Optional.empty();
		} else {
			optionalListComputer = Optional.of(listResult);
			System.out.println(listResult);
		}
		return optionalListComputer;
	}
}
