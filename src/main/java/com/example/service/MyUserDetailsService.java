package com.example.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			//user is also  a class here
			return new User("roshan","roshan",new ArrayList<>());
		}

}
