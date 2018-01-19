package comparator;

import java.util.Comparator;

import model.Computer;

public class ComparatorComputerName implements Comparator<Computer>{

	@Override
	public int compare(Computer c1, Computer c2) {
		try {
			return c1.getName().toUpperCase().compareTo(c2.getName().toUpperCase());
		}catch(NullPointerException e) {
			if(c1.getName() != null) {
				return 1;
			}else if(c2.getName() != null){
				return -1;
			}else {
				return 0;
			}
		}
	}
}
