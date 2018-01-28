package org.cdb.connectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.cdb.exception.DAOConfigurationException;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfigHikari {
	
	private final static String PROPERTIES_FILE                      = "/home/guilhem/eclipse-workspace/computer-database/src/main/java/org/cdb/connectionPool/db.properties";
	private static final String PROPERTY_URL                         = "url";
    private static final String PROPERTY_NOM_UTILISATEUR             = "username";
    private static final String PROPERTY_MOT_DE_PASSE                = "password";
    private static final String PROPERTY_CACHE_PREP_STMTS            = "cachePrepStmts";
    private final static String PROPERTY_PREP_STMT_CACHE_SIZE        = "prepStmtCacheSize";
    private final static String PROPERTIY_PREP_STMT_CACHE_SQL_LIMITE = "prepStmtCacheSqlLimit";
    
    
//	private static HikariConfig hikariConfig = new HikariConfig();
//	
	private static HikariDataSource hikariDataSource;// = initialisationHikariDataSource();
	
	private static DataSource dataSource;
	
	public static HikariDataSource getHikariDataSource() {
		return hikariDataSource;
	}
	
//	public static HikariDataSource initialisationHikariDataSource() {
//
//		Properties properties = new Properties();
//        String url;
//        String userName;
//        String password;
//        String cachePrepStmts;
//        String prepStmtCacheSize;
//        String prepStmtCacheSqlLimit;
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        
//        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTIES_FILE );
//        if ( fichierProperties == null ) {
//            throw new DAOConfigurationException( "Properties file : " + PROPERTIES_FILE + " not found." );
//        }
//        try {
//            properties.load( fichierProperties );
//            url = properties.getProperty( PROPERTY_URL );
//            userName = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
//            password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
//            cachePrepStmts = properties.getProperty( PROPERTY_CACHE_PREP_STMTS );
//            prepStmtCacheSize = properties.getProperty( PROPERTY_PREP_STMT_CACHE_SIZE );
//            prepStmtCacheSqlLimit = properties.getProperty( PROPERTIY_PREP_STMT_CACHE_SQL_LIMITE );
//            hikariConfig.setJdbcUrl( url );
//            hikariConfig.setUsername( userName );
//            hikariConfig.setPassword( password );
//            hikariConfig.addDataSourceProperty( "cachePrepStmts" , cachePrepStmts );
//            hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , prepStmtCacheSize );
//            hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , prepStmtCacheSqlLimit );
//            hikariConfig.setAutoCommit(true);
//            hikariConfig.setTransactionIsolation("TRANSACTION_SERIALIZABLE");
//        } catch ( IOException e  ) {
//            throw new DAOConfigurationException( "Can't load properties file " + PROPERTIES_FILE, e );
//        }
//		
//		hikariDataSource = new HikariDataSource(hikariConfig);
//		return hikariDataSource; 
//	}
	
	@Bean
	public static DataSource getDataSource() {
		if(dataSource == null) {
			HikariConfig config = new HikariConfig(PROPERTIES_FILE);
			dataSource = new HikariDataSource(config);
		}
		return dataSource; 
	}
//	@Bean
//	public static DataSource getDataSource() {
//		Properties properties = new Properties();
//        String url;
//        String userName;
//        String password;
//        String cachePrepStmts;
//        String prepStmtCacheSize;
//        String prepStmtCacheSqlLimit;
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        
//        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTIES_FILE );
//        if ( fichierProperties == null ) {
//            throw new DAOConfigurationException( "Properties file : " + PROPERTIES_FILE + " not found." );
//        }
//        try {
//            properties.load( fichierProperties );
//            url = properties.getProperty( PROPERTY_URL );
//            userName = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
//            password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
//            cachePrepStmts = properties.getProperty( PROPERTY_CACHE_PREP_STMTS );
//            prepStmtCacheSize = properties.getProperty( PROPERTY_PREP_STMT_CACHE_SIZE );
//            prepStmtCacheSqlLimit = properties.getProperty( PROPERTIY_PREP_STMT_CACHE_SQL_LIMITE );
//            hikariConfig.setJdbcUrl( url );
//            hikariConfig.setUsername( userName );
//            hikariConfig.setPassword( password );
//            hikariConfig.addDataSourceProperty( "cachePrepStmts" , cachePrepStmts );
//            hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , prepStmtCacheSize );
//            hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , prepStmtCacheSqlLimit );
//            hikariConfig.setAutoCommit(false);
//            hikariConfig.setTransactionIsolation("TRANSACTION_SERIALIZABLE");
//        } catch ( IOException e  ) {
//            throw new DAOConfigurationException( "Can't load properties file " + PROPERTIES_FILE, e );
//        }
//		
//		dataSource = (DataSource) new HikariDataSource(hikariConfig); 
//		
//		return dataSource;
//	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}
}
