package com.jamir.billup.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jamir.billup.model.Bill;

@Controller
public class BillController {

	@GetMapping("/bill/create")
	public String create() {
		return "bill/create";
	}
	@PostMapping("/bill/create")
	public ResponseEntity<Map<String, String>> create(Bill bill) {
		Map<String, String> res = new HashMap<String, String>();
		res.put("msg", "Conta cadastrada com sucesso");
		return ResponseEntity.ok(res);
	}
	@GetMapping("/bill/list")
	public String list() {
		return "bill/list";
	}
}
