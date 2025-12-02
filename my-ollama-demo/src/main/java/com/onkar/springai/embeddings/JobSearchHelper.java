package com.onkar.springai.embeddings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onkar.springai.services.OllamaService;

@Controller
public class JobSearchHelper {

	@Autowired
	private OllamaService service;

	@GetMapping("/showJobSearchHelper")
	public String showJobSearchHelper() {
		return "jobSearchHelper";

	}

	@PostMapping("/jobSearchHelper")
	public String jobSearchHelper(@RequestParam String query, Model model) {

		return "jobSearchHelper";

	}

}