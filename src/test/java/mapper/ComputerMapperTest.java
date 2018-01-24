package mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.cdb.mapper.ComputerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ComputerMapperTest {
	
	@Mock
	private ComputerMapper computerMapper;
	
	private Logger logger = (Logger) LoggerFactory.getLogger("CompanyMapperTest");

	@Test
	public void mapCompanyTest() {
		try {
			ResultSet resultSet = mock(ResultSet.class);
			Mockito.when(computerMapper.mapComputer(resultSet)).thenCallRealMethod();
			LocalDateTime dateTimeI = LocalDateTime.of(2000, 10, 10, 0, 0);
			LocalDate dateI = LocalDate.of(2000, 10, 10);
			LocalDateTime dateTimeD = LocalDateTime.of(2000, 10, 12, 0, 0);
			LocalDate dateD = LocalDate.of(2000, 10, 12);
			Mockito.when(resultSet.getLong("id")).thenReturn((long) 1);
			Mockito.when(resultSet.getString("name")).thenReturn("Test");
			Mockito.when(resultSet.getTimestamp("introduced")).thenReturn(Timestamp.valueOf(dateTimeI));
			Mockito.when(resultSet.getTimestamp("discontinued")).thenReturn(Timestamp.valueOf(dateTimeD));
			Mockito.when(resultSet.getLong("company_id")).thenReturn((long) 2);
			Mockito.when(resultSet.getString("company.name")).thenReturn("Company");

			assertEquals((Long)computerMapper.mapComputer(resultSet).getId() ,(Long) resultSet.getLong("id"));
			assertEquals((Long)computerMapper.mapComputer(resultSet).getCompany_id() ,(Long) resultSet.getLong("company_id"));
			assertEquals(computerMapper.mapComputer(resultSet).getName() , resultSet.getString("name"));
			assertEquals(computerMapper.mapComputer(resultSet).getIntroduced(),dateI);
			assertEquals(computerMapper.mapComputer(resultSet).getDiscontinued(),dateD);
			assertEquals(computerMapper.mapComputer(resultSet).getCompany().getName(),resultSet.getString("company.name"));
			
		} catch (SQLException e) {
			logger.error("Error in the sql result set");
			e.printStackTrace();
		}
	}
}
