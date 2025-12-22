package com.onkar.springai.imageprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onkar.springai.services.OllamaService;

@Controller
public class ImageGenerationController {

	@Autowired
	private OllamaService service;

	@GetMapping("/showImageGenerator")
	public String showImageGenerator() {
		return "imageGenerator";

	}

	@PostMapping("/imageGenerator")
	public String imageGenerator(@RequestParam String prompt, Model model) {
		return "imageGenerator";

	}

}