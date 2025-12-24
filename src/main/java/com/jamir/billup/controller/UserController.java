package com.jamir.billup.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jamir.billup.model.User;
import com.jamir.billup.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/user/create")
	public String create() {
		return "user/create";
	}
	@PostMapping("/user/create")
	public ResponseEntity<Map<String, String>> create(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		ur.save(user);
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "Usuário cadastrado com sucesso");
		return ResponseEntity.ok(res);
	}
}
