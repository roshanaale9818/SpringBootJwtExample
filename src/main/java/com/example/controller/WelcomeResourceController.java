package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configs.JwtUtil;
import com.example.models.AuthenticationRequest;
import com.example.models.AuthenticationResponse;
import com.example.service.MyUserDetailsService;

@RestController
public class WelcomeResourceController {
	//this authentication manager is provided by spring secuity for auth managing
		@Autowired
		AuthenticationManager authenticationManager;
		//we load from myUserDetailsService;
		@Autowired
		MyUserDetailsService myUserDetailsService;
		@Autowired
		JwtUtil jwtUtil;
		
		
		@RequestMapping("/hello")
		public String hello() {
			return "hello world";
		}
		
		@PostMapping(value = "/authenticate")
		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()
						));
		}
		catch(BadCredentialsException e) {
			throw new Exception("Incorrect id or password",e);
			
		}
			final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String jwt = jwtUtil.generateToken(userDetails);
			return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
		}

}
