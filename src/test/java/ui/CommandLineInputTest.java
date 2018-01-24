package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.Scanner;

import org.cdb.cui.CommandLineInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineInputTest {
	
	@Mock
	private CommandLineInput commandLineInput;

	@Test
	public void testReadUserInputInt() {
		Mockito.when(commandLineInput.readUserInputInt()).thenCallRealMethod();
		Mockito.when(commandLineInput.readUserInput()).thenReturn("1");
		assertEquals(commandLineInput.readUserInputInt(),1);
		Mockito.when(commandLineInput.readUserInput()).thenReturn("Bob");
		assertEquals(commandLineInput.readUserInputInt(),-1);
		Mockito.when(commandLineInput.readUserInput()).thenReturn("11");
		assertNotEquals(commandLineInput.readUserInputInt(),12);
	}
	
	@Test
	public void testReadUserYear() {
		Mockito.when(commandLineInput.readUserYear()).thenCallRealMethod();
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(1960);
		assertEquals(commandLineInput.readUserYear(),-1);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(1972);
		assertEquals(commandLineInput.readUserYear(),1972);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(2600);
		assertNotEquals(commandLineInput.readUserYear(),2600);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(2600);
		assertEquals(commandLineInput.readUserYear(),-1);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(1980);
		assertNotEquals(commandLineInput.readUserYear(),1978);
	}
	
	@Test
	public void testReadUserMonth() {
		Mockito.when(commandLineInput.readUserMonth()).thenCallRealMethod();
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(33);
		assertEquals(commandLineInput.readUserMonth(),-1);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(11);
		assertEquals(commandLineInput.readUserMonth(),11);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(40);
		assertNotEquals(commandLineInput.readUserMonth(),40);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(1970);
		assertEquals(commandLineInput.readUserMonth(),-1);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(12);
		assertNotEquals(commandLineInput.readUserMonth(),11);
	}
	
	@Test
	public void testReadUserInput() {
		Mockito.when(commandLineInput.readUserInput()).thenCallRealMethod();
	
		Mockito.when(commandLineInput.getInputScanner()).thenReturn("Test1");
		assertEquals(commandLineInput.readUserInput(),"Test1");
		
		Mockito.when(commandLineInput.getInputScanner()).thenReturn("(-è_ççfdgvb$*ù⁾àà)");
		assertEquals(commandLineInput.readUserInput(),"(-è_ççfdgvb$*ù⁾àà)");
		
		Mockito.when(commandLineInput.getInputScanner()).thenReturn("TEST");
		assertNotEquals(commandLineInput.readUserInput(),"test");
	}
	
	@Test
	public void testReadUserDay() {
		Mockito.when(commandLineInput.readUserDay()).thenCallRealMethod();
	
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(1);
		assertEquals(commandLineInput.readUserDay(),1);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(32);
		assertEquals(commandLineInput.readUserDay(),-1);

		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(12);
		assertNotEquals(commandLineInput.readUserDay(),-1);
		
		Mockito.when(commandLineInput.readUserInputInt()).thenReturn(12);
		assertNotEquals(commandLineInput.readUserDay(),11);
	}
	
	@Test
	public void readUserInputDate() {
		Mockito.when(commandLineInput.readUserInputDate()).thenCallRealMethod();

		Mockito.when(commandLineInput.readUserYear()).thenReturn(1980);
		Mockito.when(commandLineInput.readUserMonth()).thenReturn(1);
		Mockito.when(commandLineInput.readUserDay()).thenReturn(1);
		assertEquals(commandLineInput.readUserInputDate(),LocalDate.of(1980, 1, 1));
		
		Mockito.when(commandLineInput.readUserYear()).thenReturn(1990);
		Mockito.when(commandLineInput.readUserMonth()).thenReturn(12);
		Mockito.when(commandLineInput.readUserDay()).thenReturn(5);
		assertNotEquals(commandLineInput.readUserInputDate(),LocalDate.of(1980, 12, 5));
	}
}
