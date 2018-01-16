package view;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import dto.Dto;

public class PageWeb {
	
	private static PageWeb pageWeb = new PageWeb();
	private Logger logger = (Logger) LoggerFactory.getLogger("PageWeb");
	private static Dto dto;
	
	public static PageWeb getInstance(HttpServletRequest request) {
		dto = Dto.getInstance(request);
		return pageWeb;
	}
	
	public void changeDisplay(HttpServletRequest request) {
		if(request.getParameter("beginComputerDisplay") != "" ) {
			try {
				int beginComputerDisplay = Integer.parseInt(request.getParameter("beginComputerDisplay"));
				request.getSession().setAttribute("beginComputerDisplay", beginComputerDisplay);
			}catch(NumberFormatException e) {
				logger.warn("beginComputerDisplay can't be parsed");
			}
		}
		if(request.getParameter("numberComputerToShow") != "" ) {
			try {
				int numberComputerToShow = Integer.parseInt(request.getParameter("numberComputerToShow"));
				request.getSession().setAttribute("numberComputerToShow", numberComputerToShow);
			}catch(NumberFormatException e) {
				logger.warn("numberComputerToShow can't be parsed");
			}
		}
	}
	
	public void initDisplay(HttpServletRequest request) {
		request.getSession().setAttribute("beginComputerDisplay", 0);
		request.getSession().setAttribute("numberComputerToShow", 10);	
	}
	
	public void updateDb(HttpServletRequest request) {
		
	}
}
