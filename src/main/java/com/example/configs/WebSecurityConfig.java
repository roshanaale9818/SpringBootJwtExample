package com.example.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.filters.JwtRequestFilter;
import com.example.service.MyUserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//overide the default behaviour of spring security
//we are using myuserdetailsservice for auth here by overriding
//	we overide our own userDetailsService here
		auth.userDetailsService(myUserDetailsService);
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(http);
		http.csrf().disable()
		.authorizeRequests().antMatchers("/authenticate").permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		//for no any hashing purpose
		return NoOpPasswordEncoder.getInstance();
	}

}
