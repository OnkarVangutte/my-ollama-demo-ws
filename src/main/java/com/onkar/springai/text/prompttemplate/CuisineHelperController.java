package com.onkar.springai.text.prompttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onkar.springai.services.OllamaService;
import com.onkar.springai.text.prompttemplate.dtos.CountryCuisines;

@Controller
public class CuisineHelperController {
	@Autowired
    private OllamaService chatService;

    @GetMapping("/showCuisineHelper")
    public String showChatPage() {
         return "cuisineHelper";
    }

    @PostMapping("/cuisineHelper")
    public String getChatResponse(@RequestParam("country") String country, @RequestParam("numCuisines") String numCuisines,@RequestParam("language") String language,Model model) {
    	CountryCuisines response = chatService.getCuisines(country, numCuisines, language);
    	System.out.println(response);
    	model.addAttribute("countryCuisines", response);
        return "cuisineHelper";
    }
}
