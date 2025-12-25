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
import com.jamir.billup.model.Bill;
import com.jamir.billup.model.Client;
import com.jamir.billup.repository.BillRepository;
import com.jamir.billup.repository.ClientRepository;

@Controller
public class BillController {
	
	@Autowired
	private ClientRepository cr;
	@Autowired
	private BillRepository br;

	@GetMapping("/bill/create")
	public ModelAndView create() {
		ModelAndView mv = new ModelAndView();
		List<Client> clients = cr.findAll();
		mv.setViewName("bill/create");
		
		mv.addObject("clients", clients);
		return mv;
	}
	@PostMapping("/bill/create")
	public ResponseEntity<Map<String, String>> create(Bill bill, @AuthenticationPrincipal UserPrincipal user) {
		bill.setUser(user.getUser());
		br.save(bill);
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "Conta cadastrada com sucesso");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/bill/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		List<Bill> bills = br.findAll();
		mv.setViewName("bill/list");
		mv.addObject("bills", bills);
		return mv;
	}
	@GetMapping("/bill/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();		
		Optional<Bill> bill = br.findById(id);
		mv.setViewName("bill/edit");
		mv.addObject("bill", bill.get());
		return mv;
	}
	@PostMapping("/bill/update")
	public String update(Bill bill) {
		br.save(bill);
		return "redirect:/bill/list";
	}
}
