package com.onkar.springai.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

	private ChatClient chatClient;

	public OllamaService(ChatClient.Builder builder, ChatMemory chatMemory) {
//		System.err.println("constructor being called!!!!");
		chatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}

	public ChatResponse generateAnswer(String question) {
//		OllamaChatOptions options = new OllamaChatOptions();
//		options.setModel("gemma:2b");
//		options.setTemperature(0.7);
//		options.setMaxTokens(30);
		return chatClient.prompt(question).call().chatResponse();
	}

}
