package com.onkar.springai.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.onkar.springai.services.OllamaService;

@RestController
public class AnswerAnyThingStreamingController {

	@Autowired
	OllamaService service;

}