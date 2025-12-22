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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onkar.springai.services.OllamaService;

@Controller
public class ImageAnalyzerController {

    private static final String UPLOAD_DIR = "/Users/vangu/Documents/springai/images/uploads/";
    
    @Autowired
    private OllamaService service;

    // Display the image upload form
    @GetMapping("showImageAnalyzer")
    public String showUploadForm() {
        return "imageAnalyzer";
    }

	@PostMapping("/imageAnalyzer")
	public String uploadImage(String prompt, @RequestParam("file") MultipartFile file, Model model,
			RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			model.addAttribute("message", "Please select an appropriate file");
			return "imageAnalyzer";
		}

		try {
			// Ensure the directory exists
			Path uploadDir = Paths.get(UPLOAD_DIR);
			if (Files.notExists(uploadDir)) {
				Files.createDirectories(uploadDir); // Create directory is not exists
			}

			// Save the uploaded file into specific directory
			Path path = uploadDir.resolve(file.getOriginalFilename());
			Files.write(path, file.getBytes(), StandardOpenOption.CREATE);
			// Generate Explanation and add the model

			String response = service.explainImage(prompt, path.toString());
			model.addAttribute("explanation", response);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}

		return "imageAnalyzer";
	}
}