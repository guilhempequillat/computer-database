package ui;

import java.time.LocalDate;
import java.util.Scanner;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class CommandLineInput {
	
	private Scanner sc;
	private static Logger logger = (Logger) LoggerFactory.getLogger("CommandLineInput");
	
	public CommandLineInput() {
		logger.info("Scanner creation");
		sc = new Scanner(System.in);
	}
	
	public String readUserInput() {
		String input = sc.nextLine();
		return input;
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
	
	public void closeScanner() {
		sc.close();
		logger.info("Scanner close");
	}
}
