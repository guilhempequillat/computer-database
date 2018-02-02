package org.cdb.springConfiguration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.cdb.connectionPool.DataSourceConfigHikari;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "/hibernate.properties" })
public class PersistanceConfig {
	
 		@Autowired
	   private Environment env;
 		
 		@Autowired
 		private DataSource dataSource;
	   
	   @Bean
	   public LocalSessionFactoryBean sessionFactory() {
	      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	      sessionFactory.setDataSource(dataSource);
	      sessionFactory.setPackagesToScan(
	        new String[] { "org.cdb.model" });
	      sessionFactory.setHibernateProperties(hibernateProperties());
	 
	      return sessionFactory;
	   }
	 
	   @Bean
	   public org.apache.tomcat.jdbc.pool.DataSource restDataSource() {
	      org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
	      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	      dataSource.setUrl("jdbc:mysql://localhost:3306/computer-database-db?useSSL=false");
	      dataSource.setUsername("admincdb");
	      dataSource.setPassword("test123");
	      return dataSource;
	   }
	 
	   @Bean
	   @Autowired
	   public HibernateTransactionManager transactionManager(
        SessionFactory sessionFactory) {
	  
	      HibernateTransactionManager txManager = new HibernateTransactionManager();
	      txManager.setSessionFactory(sessionFactory);
	 
	      return txManager;
	   }
	 
	   @Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	      return new PersistenceExceptionTranslationPostProcessor();
	   }
	 
	   Properties hibernateProperties() {
	      return new Properties() {
	         {
	            setProperty("hibernate.hbm2ddl.auto",
	              env.getProperty("hibernate.hbm2ddl.auto"));
	            setProperty("hibernate.dialect",
	              env.getProperty("hibernate.dialect"));
	            setProperty("hibernate.globally_quoted_identifiers",
	             "true");
	         }
	      };
	   }

}
