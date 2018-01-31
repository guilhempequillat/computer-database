package org.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.cdb.model.Company;
import org.cdb.model.Computer;

import org.springframework.jdbc.core.RowMapper;

public class RowMapperComputer implements RowMapper<Computer>{
	
	private static RowMapperComputer rowMapperComputer = new RowMapperComputer();
	
	public Computer mapRow(ResultSet resultSet, int rn) throws SQLException {
		Long id = null, company_id = null;
		String name = null;
		Timestamp discontinued = null , introduced = null;
		Company company = null;
		Computer computer = new Computer();
		id = resultSet.getLong("id");
		computer.setId(id);
		if(resultSet.getString("name") != null) {
			name = resultSet.getString("name");
			computer.setName(name);
		}
		if(resultSet.getTimestamp("introduced") != null) {
			introduced = resultSet.getTimestamp("introduced");
			computer.setIntroduced(introduced.toLocalDateTime().toLocalDate());
		}
		if(resultSet.getTimestamp("discontinued") != null) {
			discontinued = resultSet.getTimestamp("discontinued");
			computer.setDiscontinued(discontinued.toLocalDateTime().toLocalDate());
		}
		if((Long) resultSet.getLong("company_id") != null) {
			company_id = resultSet.getLong("company_id");
			computer.setCompany_id(company_id);
		}
		if(resultSet.getString("company.name") != null) {
			company = new Company(resultSet.getString("company.name"));
			computer.setCompany(company);
		}
		return computer;
	}
	
	public static RowMapperComputer getInstance() {
		return rowMapperComputer;
	}
}
