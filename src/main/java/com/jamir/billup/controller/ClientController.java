package com.jamir.billup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jamir.billup.config.UserPrincipal;
import com.jamir.billup.model.Client;
import com.jamir.billup.repository.ClientRepository;

@Controller
public class ClientController {
	
	@Autowired
	private ClientRepository cr;
	
	@GetMapping("/client/create")
	public String create() {
		return "client/create";
	}
	@PostMapping("/client/create")
	public ResponseEntity<Map<String, String>> create(Client client, @AuthenticationPrincipal UserPrincipal user) {
		client.setUser(user.getUser());
		cr.save(client);
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "Cliente cadastrado com sucesso");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/client/list")
	public ModelAndView list(@AuthenticationPrincipal UserPrincipal user) {
		Long id = user.getId();
		List<Client> list = cr.findAllByUserId(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("client/list");
		mv.addObject("clients", list);
		return mv;
	}
	@GetMapping("/client/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		Optional<Client> client = cr.findById(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("client/edit");
		mv.addObject("client", client.get());
		return mv;
	}
	@PostMapping("/client/update")
	public String update(Client client, @AuthenticationPrincipal UserPrincipal user) {
		Optional<Client> dbClient = cr.findById(client.getId());
		//check para ver se o cliente e do logado
		if(user.getId() == dbClient.get().getUser().getId()) {
			client.setUser(user.getUser());
			cr.save(client);			
		}
		return "redirect:/client/list";
	}
}
