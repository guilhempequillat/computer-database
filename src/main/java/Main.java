import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import security.PasswordVerification;
import ui.Page;

public class Main {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("^$");
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(new String("security").getBytes());
			System.out.println(hash.toString());
			System.out.println(new String(hash));
			StringBuilder sb = new StringBuilder();
		    for(int i=0 ; i < hash.length ; i++) {
		    	System.out.print("|"+hash[i]+"|"+i+"\n");
		        sb.append(String.format("%02X ", hash[i]));
		    }
		    byte[] theHash = new byte[20];
		    theHash[0] = 104;
		    theHash[1] = 104;
		    theHash[2] = 104;
		    theHash[3] = 104;
		    theHash[4] = 104;
		    theHash[5] = 104;
		    theHash[6] = 104;
		    theHash[7] = 104;
		    theHash[8] = 104;
		    theHash[9] = 104;
		    theHash[10] = 104;
		    theHash[11] = 104;
		    theHash[12] = 104;
		    theHash[13] = 104;
		    theHash[14] = 104;
		    theHash[15] = 104;
		    theHash[16] = 104;
		    theHash[17] = 104;
		    theHash[18] = 104;
		    theHash[19] = 104;
		    System.out.println(hash[0]+"");
		    if(theHash[0]==(hash[0])) {
		    	System.out.println("Coucou");
		    }
		    System.out.println();
		    System.out.println(sb);
		    PasswordVerification passwordVerification = PasswordVerification.getInstance();
		    System.out.println(passwordVerification.passwordIsCorrect("security"));
		    System.out.println(passwordVerification.passwordIsCorrect("securiy"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Page page = new Page();
	}

}
