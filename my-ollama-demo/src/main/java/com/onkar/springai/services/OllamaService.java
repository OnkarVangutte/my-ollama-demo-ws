package com.onkar.springai.services;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.onkar.springai.text.prompttemplate.dtos.CountryCuisines;

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

	public String getTravelGuidance(String city, String month, String language, String budget) {
		PromptTemplate promptTemplate = new PromptTemplate("Welcome to the {city} travel guide!\n"
				+ "If you're visiting in {month}, here's what you can do:\n" + "1. Must-visit attractions.\n"
				+ "2. Local cuisine you must try.\n" + "3. Useful phrases in {language}.\n"
				+ "4. Tips for traveling on a {budget} budget.\n" + "Enjoy your trip!");
		Prompt prompt = promptTemplate
				.create(Map.of("city", city, "month", month, "language", language, "budget", budget));

		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
	}
	
	public CountryCuisines getCuisines(String country, String numCuisines, String language) {

		PromptTemplate promptTemplate = new PromptTemplate("You are an expert in traditional cuisines.\n"
				+ "Answer the question: What is the traditional cuisine of {country}?\n"
				+ "Return a list of {numCuisines} in {language}.\n" + "You provide information about a specific dish \n"
				+ "from a specific country.\n" + "Avoid giving information about fictional places.\n"
				+ "If the country is fictional or non-existent \n" + "return the country with out any cuisines.");

		Prompt prompt = promptTemplate
				.create(Map.of("country", country, "numCuisines", numCuisines, "language", language));
		System.out.println(prompt);
		return chatClient.prompt(prompt).call().entity(CountryCuisines.class);
	}
	
	public String getInterviewGuidance(String company, String jobTitle, String strength, String weakness) {
		PromptTemplate promptTemplate = new PromptTemplate("Welcome to the {company} !\n"
				+ "If you're preparing for {jobTitle}, here's what you can do:\n" + "1. Must-focus on basics.\n"
				+ "2. Some best study resources you must try.\n" + "3. Useful phrases in {strength}.\n"
				+ "4. Tips for best justification on a {weakness}.\n" + "All the Best!");
		Prompt prompt = promptTemplate
				.create(Map.of("company", company, "jobTitle", jobTitle, "strength", strength, "weakness", weakness));
		System.out.println(prompt);

		return chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText();
	}
	
}
