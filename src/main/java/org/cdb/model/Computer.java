package org.cdb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;;

@Entity
@Table(name = "computer")
public class Computer{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "introduced")
	private LocalDate introduced;
	
	@Column(name = "discontinued")
	private LocalDate discontinued;
	
//	@Column(name = "company_id")
	private Long company_id;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Company company;
	
	public Computer() {}
	
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
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
	
	public boolean equals(Computer computer) {
		if(this.id == computer.getId()
		&& this.name.equals(computer.getName())
		&& this.introduced.equals(computer.getIntroduced())
		&& this.discontinued.equals(computer.getDiscontinued())
		&& this.company_id == computer.getCompany_id()) {
			return true;
		}
		return false;
	}
}