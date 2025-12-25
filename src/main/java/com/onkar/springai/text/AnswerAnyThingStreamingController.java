package com.onkar.springai.text;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onkar.springai.services.OllamaService;

import reactor.core.publisher.Flux;

@RestController
public class AnswerAnyThingStreamingController {

	@Autowired
	OllamaService service;

	// Stream the response
//	@GetMapping("/stream")
//	public Flux<String> askAnything(@RequestParam("message") String message) {
//		return service.generateStreamAnswer(message);
//	}

	// Stream the response With Meta Data
	@GetMapping("/stream")
	public Flux<ChatResponse> streamWithMetaData(@RequestParam("message") String message) {
		return service.generateStreamAnsWithMetaData(message);
	}

}