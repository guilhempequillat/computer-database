package org.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.cdb.model.Company;
import org.cdb.model.Computer;

public class CompanyMapper {
	
	private static CompanyMapper companyMapper = new CompanyMapper();
	
	public static CompanyMapper getCompanyMapper() {
		return companyMapper;
	}
	
	public Company mapCompany(ResultSet resultSet) throws SQLException {
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
}
