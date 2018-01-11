package mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

@RunWith(MockitoJUnitRunner.class)
public class CompanyMapperTest {
	
	@Mock
	private CompanyMapper companyMapper;
	
	private Logger logger = (Logger) LoggerFactory.getLogger("CompanyMapperTest");

	@Test
	public void mapCompanyTest() {
		try {
			ResultSet resultSet = mock(ResultSet.class);
			Mockito.when(resultSet.getLong("id")).thenReturn((long) 1);
			Mockito.when(resultSet.getString("name")).thenReturn("Test");
			System.out.println(resultSet.getLong("id"));
			System.out.println(resultSet.getLong("id"));
			Mockito.when(companyMapper.mapCompany(resultSet)).thenCallRealMethod();
			assertEquals((Long)companyMapper.mapCompany(resultSet).getId() ,(Long) resultSet.getLong("id"));
			
			
		} catch (SQLException e) {
			logger.error("Error in the sql result set");
			e.printStackTrace();
		}
	}
}
