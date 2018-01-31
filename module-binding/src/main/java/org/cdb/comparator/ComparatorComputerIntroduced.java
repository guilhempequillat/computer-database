package org.cdb.comparator;

import java.util.Comparator;

import org.cdb.model.Computer;

public class ComparatorComputerIntroduced implements Comparator<Computer>{

	@Override
	public int compare(Computer c1, Computer c2) {
		try {
			return c1.getIntroduced().compareTo(c2.getIntroduced());
		}catch(NullPointerException e) {
			if(c1.getIntroduced() != null) {
				return 1;
			}else if(c2.getIntroduced() != null){
				return -1;
			}else {
				return 0;
			}
		}
	}
}
