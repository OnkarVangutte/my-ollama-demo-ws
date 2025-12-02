package com.onkar.springai.text.prompttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onkar.springai.services.OllamaService;

@Controller
public class interviewHelperController {

	@Autowired
    private OllamaService chatService;

    @GetMapping("/showInterviewHelper")
    public String showChatPage() {
         return "interviewHelper";
    }

	@PostMapping("/interviewHelper")
	public String getChatResponse(@RequestParam("company") String company, @RequestParam("jobTitle") String jobTitle,
			@RequestParam("strength") String strength, @RequestParam("weakness") String weakness, Model model) {
		String response = chatService.getInterviewGuidance(company, jobTitle, strength, weakness);
		System.out.println(response);
		model.addAttribute("response", response);
		return "interviewHelper";
	}
}
