package customTag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HelloTag extends SimpleTagSupport {

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		//out.println("Hello cusom tag !");
		out.print("<tr>\n" + 
				"					       	<td>\n" + 
				"						       	<a href=\"editComputer?id=${ computer.id }\" onclick=\"\">\n" + 
				"						       		${ computer.name }\n" + 
				"						       	<a/>\n" + 
				"					       	</td>\n" + 
				"					       </a>\n" + 
				"					       <td>${ computer.introduced }</td>\n" + 
				"					       <td>${ computer.discontinued }</td>\n" + 
				"					       <td>${ computer.company }</td>\n" + 
				"					   </tr>");
	}
}