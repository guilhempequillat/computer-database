package DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.PreparedStatement;

import exception.DAOConfigurationException;

public class DAOFactory {

    private static final String PROPERTIES_FILE          = "DAO/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "username";
    private static final String PROPERTY_MOT_DE_PASSE    = "password";

    private String              url;
    private String              username;
    private String              password;

    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DAOFactory getInstance() throws DAOConfigurationException {
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
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            userName = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            password = properties.getProperty( PROPERTY_MOT_DE_PASSE );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Can't load properties file " + PROPERTIES_FILE, e );
        }

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Driver not found.", e );
        }

        DAOFactory instance = new DAOFactory( url, userName, password );
        return instance;
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );
    }

    public CompanyDao getCompanyDao() {
        return new CompanyDaoImplementation(this);
    }
    
    public ComputerDao getComputerDao() {
    	return new ComputerDaoImplementation(this);
    }
}