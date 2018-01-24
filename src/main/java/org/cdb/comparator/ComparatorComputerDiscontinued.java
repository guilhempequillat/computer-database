package org.cdb.comparator;

import java.util.Comparator;

import org.cdb.model.Computer;

public class ComparatorComputerDiscontinued implements Comparator<Computer>{

	@Override
	public int compare(Computer c1, Computer c2) {
		try {
			return c1.getDiscontinued().compareTo(c2.getDiscontinued());
		}catch(NullPointerException e) {
			if(c1.getDiscontinued() != null) {
				return 1;
			}else if(c2.getDiscontinued() != null){
				return -1;
			}else {
				return 0;
			}
		}
	}
}
