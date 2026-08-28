package com.picpay.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String dashboard(Model model) {
		model.addAttribute("funcionarios", List.of());
		model.addAttribute("totalCandidatos", 0);
		model.addAttribute("emAnalise", 0);
		model.addAttribute("aprovados", 0);
		model.addAttribute("contratados", 0);

		return "index";
	}
}
