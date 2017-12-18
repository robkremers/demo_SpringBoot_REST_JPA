package com.rkremers.rest;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rkremers.rest.security.SecurityUtils;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Pre-load the system with employees and items.
	 */
	public @PostConstruct void init() {

		/**
		 * Due to method-level protections on {@link example.company.ItemRepository},
		 * the security context must be loaded with an authentication token containing
		 * the necessary privileges.
		 */
		SecurityUtils.runAs("system", "system", "ROLE_ADMIN");

		SecurityContextHolder.clearContext();
	}

	/**
	 * This application is secured at both the URL level for some parts, and the method level for other parts. The URL
	 * security is shown inside this code, while method-level annotations are enabled at by
	 * {@link EnableGlobalMethodSecurity}.
	 *
	 * @author Greg Turnquist
	 * @author Oliver Gierke
	 */
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		/**
		 * This section defines the user accounts which can be used for authentication as well as the roles each user has.
		 * 
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
		 */
		@Override
		protected  void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.inMemoryAuthentication().//
					withUser("test").password("password").roles("USER").and().//
					withUser("rob").password("kremers").roles("USER", "ADMIN", "ACTUATOR");
					
		}

		/**
		 * This section defines the security policy for the app.
		 * <p>
		 * <ul>
		 * <li>BASIC authentication is supported (enough for this REST-based demo).</li>
		 * <li>/employees is secured using URL security shown below.</li>
		 * <li>CSRF headers are disabled since we are only testing the REST interface, not a web one.</li>
		 * </ul>
		 * NOTE: GET is not shown which defaults to permitted.
		 *
		 * @param http
		 * @throws Exception
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		protected  void configure(HttpSecurity http) throws Exception {

			http.httpBasic().and().authorizeRequests().//
					antMatchers(HttpMethod.POST, "/topics/**").hasRole("USER").//
					antMatchers(HttpMethod.PUT, "/topics/**").hasRole("USER").//
					antMatchers(HttpMethod.DELETE, "/topics/**").hasRole("ADMIN").//
					antMatchers(HttpMethod.POST, "/health/**").hasRole("ADMIN").and().//
					csrf().disable();
		}
	}

}
