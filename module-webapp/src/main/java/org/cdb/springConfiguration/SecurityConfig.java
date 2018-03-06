package org.cdb.springConfiguration;

import java.util.Arrays;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.cdb.service.serviceImplementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages = { "org.cdb" })
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserServiceImplementation userService;
	
	@Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
 
    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests().antMatchers("/dashboard").hasAnyRole("USER", "ADMIN");
//		http.authorizeRequests().antMatchers("/find-a-computer/**").hasAnyRole("USER", "ADMIN");
//		http.authorizeRequests().antMatchers("/add-computer").hasAnyRole("ADMIN");
//		http.authorizeRequests().antMatchers("/edit-computer").hasAnyRole("ADMIN")
//				.and().cors().and()
//			.formLogin()
//				.loginProcessingUrl("/perform-login")
//				.loginPage("/login") 
//				.permitAll()
//				.defaultSuccessUrl("/dashboard")
//				.failureUrl("/login?error=true")
//				.usernameParameter("username")
//				.passwordParameter("password")
//			.and().logout()
//				.logoutUrl("/spring_logout")
//				.logoutSuccessUrl("/login");
		http
		.cors().and()
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and().authorizeRequests().antMatchers("/find-a-computer/**").authenticated()
        .and().authorizeRequests().antMatchers("/find-computer-pagination").authenticated()
        .and().authorizeRequests().antMatchers("/count-computer").authenticated()
        .and().authorizeRequests().antMatchers("/count-computer-filter").authenticated()
        .and().authorizeRequests().antMatchers("/update-name").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/update-introduced").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/update-discontinued").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/update-company").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/create-computer").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/create-company").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/delete-computer").hasAnyRole("ADMIN")
        .and().authorizeRequests().antMatchers("/delete-company").hasAnyRole("ADMIN")
        .and()
        .authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        .and()
        	.formLogin().loginProcessingUrl("/perform-login")
        	.successHandler(authenticationSuccessHandler)
        	.failureHandler(new SimpleUrlAuthenticationFailureHandler())
        .and().logout();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() {
		return (UserDetailsService) userService;
}
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceBean());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://10.0.1.34:4200"));
		configuration.setAllowedMethods(Arrays.asList("PUT","GET","POST","OPTIONS","DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		configuration.addAllowedHeader("content-type");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
