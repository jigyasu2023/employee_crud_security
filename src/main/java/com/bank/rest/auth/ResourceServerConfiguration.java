package com.bank.rest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 *
 * @author Jigyasu Garg
 * @since 20 02 23
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "microservice";
	private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('READ')";
	private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('WRITE')";
	private static final String SECURED_PATTERN = "/**";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.sessionManagement().disable()
				.authorizeRequests()
				.antMatchers("v1/employee/list").permitAll()
				.and()
				.requestMatchers().antMatchers(SECURED_PATTERN)
				.and()
				.authorizeRequests().antMatchers(HttpMethod.POST, SECURED_PATTERN)
				.access(SECURED_WRITE_SCOPE).anyRequest()
				.access(SECURED_READ_SCOPE);
	}
}
