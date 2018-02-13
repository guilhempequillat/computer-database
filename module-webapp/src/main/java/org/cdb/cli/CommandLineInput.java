package org.cdb.cli;

import java.time.LocalDate;
import java.util.Scanner;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Component
public class CommandLineInput {
	
	private final int MIN_YEAR  = 1970;
	private final int MAX_YEAR  = 2038;
	private final int MIN_MONTH = 1;
	private final int MAX_MONTH = 12;
	private final int MIN_DAY   = 1;
	private final int MAX_DAY   = 31;
	private Scanner sc= new Scanner(System.in);
	private static Logger logger = (Logger) LoggerFactory.getLogger("CommandLineInput");
	
	public String readUserInput() {
		String input = getInputScanner();
		return input;
	}
	
	public String getInputScanner() {
		return sc.nextLine();
	}
	
	public int readUserInputInt() {
		boolean correctInput = true;
		int input=0;
		int nbTries= 0;
		do {
			try {
				System.out.println(input);
				input = Integer.parseInt(readUserInput());
				correctInput = true;
			}catch( NumberFormatException e ) {
				correctInput = false;
				nbTries++;
				System.out.println("\nPlease insert a number");
			}
		} while ( !correctInput && nbTries < 3 );
		if( nbTries > 2 ) {
			logger.error("Bad input");
			return -1;
		}
		return input;
	}

	public int readUserYear() {
		boolean correctInput = true;
		int nbTries= 0;
		int input = 1;
		do {
			input = readUserInputInt();
			if(input > MIN_YEAR && input < MAX_YEAR) {
				correctInput = true;
			} else {
				nbTries++;
				correctInput = false;
				System.out.println("Invalid Year");
			}
		} while(!correctInput && nbTries < 3);
		if( nbTries > 2 ) {
			logger.error("Bad input");
			return -1;
		}
		return input;
	}
	
	public int readUserMonth() {
		boolean correctInput = true;
		int input = 1;
		int nbTries= 0;
		do {
			input = readUserInputInt();
			if(input >= MIN_MONTH && input <= MAX_MONTH) {
				correctInput = true;
			} else {
				nbTries++;
				correctInput = false;
				System.out.println("Invalid Month");
			}
		} while(!correctInput && nbTries < 3);
		if( nbTries > 2 ) {
			logger.error("Bad input");
			return -1;
		}
		return input;
	}
	
	public int readUserDay() {
		boolean correctInput = true;
		int input = 1;
		int nbTries = 0;
		do {
			input = readUserInputInt();
			if(input >= MIN_DAY && input <= MAX_DAY) {
				correctInput = true;
			} else {
				nbTries++;
				correctInput = false;
				System.out.println("Invalid Day");
			}
		} while(!correctInput && nbTries < 3);
		if( nbTries > 2 ) {
			logger.error("Bad input");
			return -1;
		}
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
