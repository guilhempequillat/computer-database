package org.cdb.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Component
public class PasswordVerification {
	
	private byte[] hash = new byte[32];
	Logger logger = (Logger) LoggerFactory.getLogger("PasswordVerification");
	private final String HASH_ALGO = "SHA-256";
	
	public void createHash() {
		//Password hash for SHA-256
		hash[0] = 93;
		hash[1] = 45;
		hash[2] = 60;
	    hash[3] = -21;
	    hash[4] = 122;
	    hash[5] = -66;
	    hash[6] = 85;
	    hash[7] = 35;
	    hash[8] = 68;
	    hash[9] = 39;
	    hash[10] = 109;
	    hash[11] = 71;
	    hash[12] = -45;
	    hash[13] = 106;
	    hash[14] = -127;
	    hash[15] = 117;
	    hash[16] = -73;
	    hash[17] = -82;
	    hash[18] = -78;
	    hash[19] = 80;
	    hash[20] = -87;
	    hash[21] = -65;
	    hash[22] = 11;
	    hash[23] = -16;
	    hash[24] = 14;
	    hash[25] = -123;
	    hash[26] = 12;
	    hash[27] = -46;
	    hash[28] = 62;
	    hash[29] = -49;
	    hash[30] = 46;
	    hash[31] = 67;
	}
	
	public boolean passwordIsCorrect(String password) {
		MessageDigest digest;
		createHash();
		try {
			digest = MessageDigest.getInstance(HASH_ALGO);
			byte[] hash = digest.digest(password.getBytes());
			for(int i = 0 ; i < hash.length ; i++) {
				if(hash[i] != this.hash[i]) {
					return false;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("Used hash algo isn't valid : "+e);
		}
		return true;
	}
}
