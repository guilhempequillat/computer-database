package org.cdb.connectionPool;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfigHikari {
    	
	private static HikariDataSource hikariDataSource;
	
	@Autowired
	private DataSource dataSource;
	
	public static HikariDataSource getHikariDataSource() {
		return hikariDataSource;
	}
	
//	@Bean
//	public static DataSource getDataSource() {
//		if(dataSource == null) {
//			HikariConfig config = new HikariConfig(PROPERTIES_FILE);
//			dataSource = new HikariDataSource(config);
//		}
//		return dataSource; 
//	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}
}
