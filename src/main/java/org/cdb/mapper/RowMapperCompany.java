package org.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperCompany implements RowMapper<Company>{
	
	private static RowMapperCompany rowMapperCompany = new RowMapperCompany();
	
	public Company mapRow(ResultSet resultSet, int rn) throws SQLException {
		Company company = new Company();
		Long id = null;
		String name = null;
		if(resultSet.getString("name") != null) {
			name = resultSet.getString("name");
			company.setName(name);
		}
		
		if((Long) resultSet.getLong("id") != null) {
			id = resultSet.getLong("id");
			company.setId(id);
		}
		return company;
	}
	
	public static RowMapperCompany getInstance() {
		return rowMapperCompany;
	}
}