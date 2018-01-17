package connectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import exception.DAOConfigurationException;

public class DataSource {
	
	private final static String PROPERTIES_FILE                      = "connectionPool/dataSource.properties";
	private static final String PROPERTY_URL                         = "url";
    private static final String PROPERTY_NOM_UTILISATEUR             = "username";
    private static final String PROPERTY_MOT_DE_PASSE                = "password";
    private static final String PROPERTY_CACHE_PREP_STMTS            = "cachePrepStmts";
    private final static String PROPERTY_PREP_STMT_CACHE_SIZE        = "prepStmtCacheSize";
    private final static String PROPERTIY_PREP_STMT_CACHE_SQL_LIMITE = "prepStmtCacheSqlLimit";
    
	private static HikariConfig hikariConfig = new HikariConfig();
	private static HikariDataSource hikariDataSource;
	
	
	public static HikariDataSource getHikariDataSource() {

		Properties properties = new Properties();
        String url;
        String userName;
        String password;
        String cachePrepStmts;
        String prepStmtCacheSize;
        String prepStmtCacheSqlLimit;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTIES_FILE );
        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Properties file : " + PROPERTIES_FILE + " not found." );
        }
        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            userName = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
            cachePrepStmts = properties.getProperty( PROPERTY_CACHE_PREP_STMTS );
            prepStmtCacheSize = properties.getProperty( PROPERTY_PREP_STMT_CACHE_SIZE );
            prepStmtCacheSqlLimit = properties.getProperty( PROPERTIY_PREP_STMT_CACHE_SQL_LIMITE );
            hikariConfig.setJdbcUrl( url );
            hikariConfig.setUsername( userName );
            hikariConfig.setPassword( password );
            hikariConfig.addDataSourceProperty( "cachePrepStmts" , cachePrepStmts );
            hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , prepStmtCacheSize );
            hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , prepStmtCacheSqlLimit );
        } catch ( IOException e  ) {
            throw new DAOConfigurationException( "Can't load properties file " + PROPERTIES_FILE, e );
        }
		
		hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource; 
	}
}
