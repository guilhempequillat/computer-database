package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;;

public class Computer {
	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Long company_id;
	private Company company;
	
	public Computer(String name, Timestamp introduced, Timestamp discontinued, Long company_id) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}
	
	public Computer(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getLong("id");
		if(resultSet.getString("name") != null) {
			this.name = resultSet.getString("name");
		}
		if(resultSet.getTimestamp("introduced") != null) {
			this.introduced = resultSet.getTimestamp("introduced");
		}
		if(resultSet.getTimestamp("discontinued") != null) {
			this.discontinued = resultSet.getTimestamp("discontinued");
		}
		if((Long) resultSet.getLong("company_id") != null) {
			this.company_id = resultSet.getLong("company_id");
		}
		if(resultSet.getString("company.name") != null) {
			this.company = new Company(resultSet.getString("company.name"));
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	
	public String toString() {
		String strName = "NULL";
		String strIntroduced = "NULL";
		String strDiscontinued = "NULL";
		String strCompanyId = "NULL";
		String strCompanyName = "NULL";
		if(this.name != null) {
			strName = this.name;
		}
		if(this.introduced != null) {
			strIntroduced = this.introduced.toString();
		}
		if(this.discontinued != null) {
			strDiscontinued = this.discontinued.toString();
		}
		if(this.company_id != null) {
			strCompanyId = this.company_id.toString();
		}
		if(this.company != null) {
			strCompanyName = this.company.toString();
		}
		return ""+id+"/"+strName+"/"+strIntroduced+"/"+strDiscontinued+"/"+strCompanyId+"/"+strCompanyName;
	}
}