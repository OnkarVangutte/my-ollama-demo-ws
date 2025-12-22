package com.onkar.springai.text;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onkar.springai.services.OllamaService;

@Controller
public class AnswerAnyThingController {

	@Autowired
    private OllamaService ollamaService;

    @GetMapping("/showAskAnything")
    public String showAskAnything() {
         return "askAnything";
    }

    @PostMapping("/askAnything")
    public String askAnything(@RequestParam("question") String question, Model model) {
//    	ChatResponse response = ollamaService.generateAnswer(question);
    	ChatResponse response = ollamaService.generateAnswerWithRoles(question);
    	System.out.println(response);
    	model.addAttribute("question", question);
    	model.addAttribute("answer", response.getResult().getOutput().getText());
        return "askAnything";
    }
}