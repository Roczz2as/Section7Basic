package com.example.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.example.exceptionhandling.CustomAccessDeniedHandler;
import com.example.exceptionhandling.CustomBasicAuthenticationEntryPoint;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {
          
	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(smc->smc.invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true))
 		.requiresChannel(rcc->rcc.anyRequest().requiresSecure()) // this line  accept only HTTPS Request
		.csrf(csc->csc.disable())
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccounts","/myBalance","/myLoans","/myCards").authenticated()
				.requestMatchers("/notices","/contact","/register","/invalidSession").permitAll());
		// whenever we enable formLogin in our webApplication , the extract credential from
		// request is going to taken care by UsernamePasswordAuthenticationFilter()
		// httpFormLogin () uses Login form as an starting 
		http.formLogin(Customizer.withDefaults());
		// if i only use http.httpBasic instead of http.formLogin,  it will show Alert Box instead of formLogin page
		//http.httpBasic(Customizer.withDefaults());
		
		// this is line is used to communicate this entrypoint(CustomBasicAuthenticationEntryPoint) with spring security
		http.httpBasic(hbc->hbc.authenticationEntryPoint(new  CustomBasicAuthenticationEntryPoint())); 
		
		// this is line is used to communicate this entrypoint(CustomAccessDeniedHandler) with spring security

	      http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));		 
		return http.build();
	 }

	
	// to create user manually we use UserDetailsService
//	@Bean
//	public UserDetailsService userDetailsService() {
//		// build () method is used to make sure that this line of code return as an  UserDetails() object
//	  UserDetails user =User.withUsername("user").password("{noop}12345").authorities("read").build();
//	  UserDetails admin=User.withUsername("admin").password("{bcrypt}$2a$12$1IKu9vqU/ev4lRfNO1PTEeqTVd1ywhYmbh2lpJzCjSMb0DElZWtBa")
//			  .authorities("admin").build();
//	    
//	  // InMemoryUserDetailsManager accept UserDetails as an argument 
//	  // we have to return one of the UserDetailsService method (which is InMemoryUserDetailsManager)  
//	  return new  InMemoryUserDetailsManager(user,admin);
// 
//	}
	 
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// CompromisedPasswordChecker always take strong password
	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
