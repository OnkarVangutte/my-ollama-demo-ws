package com.onkar.springai.imageprocessing;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.onkar.springai.services.OllamaService;

@Controller
public class DietHelperController {

    // Define the folder where images will be saved
	private static final String UPLOAD_DIR = "/Users/vangu/Documents/springai/images/uploads/";
    
    @Autowired
    private OllamaService service;

    // Display the image upload form
    @GetMapping("/showDietHelper")
    public String showUploadForm() {
        return "dietHelper";
    }

    @PostMapping("/dietHelper")
    public String dietHelper(String prompt, @RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2, Model model) {
    	if (file1.isEmpty() && file2.isEmpty()) {
			model.addAttribute("message", "Please select an appropriate file");
			return "dietHelper";
		}

		try {
			// Ensure the directory exists
			Path uploadDir = Paths.get(UPLOAD_DIR);
			if (Files.notExists(uploadDir)) {
				Files.createDirectories(uploadDir); // Create directory is not exists
			}

			// Save the uploaded file into specific directory
			Path path1 = uploadDir.resolve(file1.getOriginalFilename());
			Files.write(path1, file1.getBytes(), StandardOpenOption.CREATE);
			
			Path path2 = uploadDir.resolve(file2.getOriginalFilename());
			Files.write(path2, file2.getBytes(), StandardOpenOption.CREATE);
			// Generate Explanation and add the model

			String response = service.getDietAdvice(prompt, path1.toString(), path2.toString());
			model.addAttribute("suggestion", response);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}
    	return "dietHelper";
    }
}