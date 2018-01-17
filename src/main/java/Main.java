import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.regex.Pattern;

import com.zaxxer.hikari.HikariDataSource;

import connectionPool.DataSource;
import cui.Page;
import security.PasswordVerification;

public class Main {

	public static void main(String[] args) {
		HikariDataSource h = DataSource.getHikariDataSource();
		h.setAutoCommit(false);
		Page page = new Page();
	}
}
