package com.jamir.billup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jamir.billup.config.UserPrincipal;
import com.jamir.billup.model.User;
import com.jamir.billup.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository ur;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = ur.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
		}

		return new UserPrincipal(user);
	}

}
