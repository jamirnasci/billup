package com.jamir.billup.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jamir.billup.config.UserPrincipal;
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
	@GetMapping("/user/info")
	public ModelAndView info(@AuthenticationPrincipal UserPrincipal user) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/info");
		mv.addObject("user", user.getUser());
		return mv;
	}
	@PostMapping("/user/update")
	public String update(
			User userForm, 
			@AuthenticationPrincipal UserPrincipal user, 
			@RequestParam("newPassword") String newPassword,
			@RequestParam("currentPassword") String currentPassword
	) {
		User userDb = ur.findById(user.getUser().getId()).get();
		if(userDb == null) {
			return "redirect:/login";
		}
		if(passwordEncoder.matches(currentPassword, userDb.getPassword())) {
			userDb.setNome(userForm.getNome());
			userDb.setTelefone(userForm.getTelefone());
			
			if(newPassword != null && !newPassword.trim().isEmpty()) {
				userDb.setPassword(passwordEncoder.encode(newPassword));
			}
			ur.save(userDb);
		}else {
			return "redirect:/user/info";			
		}
		return "redirect:/user/info";
	}
}
