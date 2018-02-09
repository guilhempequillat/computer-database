package org.cdb.springConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
