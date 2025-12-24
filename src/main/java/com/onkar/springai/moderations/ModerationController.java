package com.onkar.springai.moderations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onkar.springai.services.OllamaService;

@Controller
public class ModerationController {

	@Autowired
    private OllamaService chatService;

    @GetMapping("/showModeration")
    public String showChatPage() {
         return "moderation";
    }

    @PostMapping("/moderation")
    public String getChatResponse(@RequestParam("text") String text, Model model) {
    	model.addAttribute("response", chatService.getModerationStatus(text));
        return "moderation";
    }
}