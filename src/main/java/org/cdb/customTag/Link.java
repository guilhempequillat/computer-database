package org.cdb.customTag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.cdb.model.Computer;

public class Link extends SimpleTagSupport {
	
	private Computer computer = new Computer();
	
	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String introduced="", discontinued="", company="";
		if(computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		}
		if(computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		}
		if(computer.getCompany() != null) {
			company = computer.getCompany().getName();
		}
		out.print("<tr>\n"
				+ "<td class=\"editMode\">\n" + 
"		                            <input type=\"checkbox\" name=\"cb\" class=\"cb\" value="+ Integer.parseInt(computer.getId().toString())+">\n" + 
"		                        </td>" + 
"					       	<td>\n" + 
"						       	<a href=\"edit-computer?id="+computer.getId()+"\" >\n" + 
"						       		"+computer.getName() + 
"						       	<a/>\n" + 
"					       	</td>\n" + 
"					       </a>\n" + 
"					       <td>"+ introduced +"</td>\n" + 
"					       <td>"+ discontinued +"</td>\n" + 
"					       <td>"+ company +"</td>\n" + 
"					   </tr>");
	}
	
}

