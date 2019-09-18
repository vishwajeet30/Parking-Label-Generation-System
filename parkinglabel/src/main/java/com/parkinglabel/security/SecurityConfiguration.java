package com.parkinglabel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
 
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	
	@Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
        	.antMatchers("/signup","/forgot","/forgotsteptwo").permitAll()
        	.antMatchers("/static/**","/captcha").permitAll()
        	.antMatchers("/", "/welcome", "/renew","/renewlabel", "/form","/formsteptwo","/pdfview","/uploadsignedform","/editlabel","/editlabeldocuments","/account")
            .access("hasRole('USER') or hasRole('ADMIN')")
            .antMatchers("/requests", "/list","/delete","/deletelabel","/deletelabelwelcome","/issue","/saveform","/deletelabel","/search","/makeadmin","/removeadmin","/postremarks","/admineditlabel")
            .access("hasRole('ADMIN')")
            .and().formLogin().loginPage("/login")
            .loginProcessingUrl("/performlogin")
            .defaultSuccessUrl("/welcome")
            .failureUrl("/login?error")
        	.usernameParameter("username")
        	.passwordParameter("password")
        	.permitAll()
        	.and().logout().logoutSuccessUrl("/login?logout")
        	.and().exceptionHandling().accessDeniedPage("/accessDenied");
        
        
	}
 
 
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20971520);	// 20MB
		multipartResolver.setMaxInMemorySize(20971520);	// 20MB
		return multipartResolver;
	}
	
	
	 @Bean
	 public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService);
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	 }
	 
	 @Bean
	    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
	        return new AuthenticationTrustResolverImpl();
	    }

	
	
	
}
