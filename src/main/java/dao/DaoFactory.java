package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import exception.DAOConfigurationException;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import dao.daoImplementation.CompanyDaoImplementation;
import dao.daoImplementation.ComputerDaoImplementation;
import dao.daoInterface.CompanyDao;
import dao.daoInterface.ComputerDao;

public class DaoFactory {

    private static final String PROPERTIES_FILE          = "dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "username";
    private static final String PROPERTY_MOT_DE_PASSE    = "password";
    private static String url;
    private static String username;
    private static String password;
    private static Logger logger = (Logger) LoggerFactory.getLogger("DaoFactory");
    private static DaoFactory daoFactorySingleton = new DaoFactory();
    
    DaoFactory(){   	
    }

    DaoFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Load the configuration of the data base
     * 
     * @return instance
     * @throws DAOConfigurationException
     */
    public static DaoFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String userName;
        String password;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTIES_FILE );
        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Properties file : " + PROPERTIES_FILE + " not found." );
        }
        try {
            properties.load( fichierProperties );
            //Get the connection information
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            userName = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Can't load properties file " + PROPERTIES_FILE, e );
        }
        try {
        	//Load the jcbd driver
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Driver not found.", e );
        }
        //DaoFactory instance = new DaoFactory( url, userName, password );
        setDaoFactoryParameter( url, userName, password );
        return daoFactorySingleton;
    }
    
    public static void setDaoFactoryParameter(String urlSet, String userNameSet, String passwordSet) {
    	url = urlSet;
    	username = userNameSet;
    	password = passwordSet;
    }

	public Connection getConnection() throws SQLException {
    	logger.info("Connection with the database");
        return DriverManager.getConnection( url, username, password );
    }

    public CompanyDao getCompanyDao() {
        return new CompanyDaoImplementation(this);
    }
    
    public ComputerDao getComputerDao() {
    	return new ComputerDaoImplementation(this);
    }
}