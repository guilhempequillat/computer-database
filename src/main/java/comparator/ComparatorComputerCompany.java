package comparator;

import java.util.Comparator;
import model.Computer;

public class ComparatorComputerCompany implements Comparator<Computer>{

	@Override
	public int compare(Computer c1, Computer c2) {
		try {
			return c1.getCompany().getName().toUpperCase().compareTo(c2.getCompany().getName().toUpperCase());
		}catch(NullPointerException e) {
			if(c1.getCompany() != null) {
				return 1;
			}else if(c2.getCompany() != null){
				return -1;
			}else {
				return 0;
			}
		}
	}
}
