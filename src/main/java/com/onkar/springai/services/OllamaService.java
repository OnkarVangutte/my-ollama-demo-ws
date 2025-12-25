package com.onkar.springai.services;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.moderation.ModerationResult;
//import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.onkar.springai.text.prompttemplate.dtos.CountryCuisines;

import reactor.core.publisher.Flux;

@Service
public class OllamaService {

	private ChatClient chatClient;
	
	@Autowired
	private EmbeddingModel embeddingModel;
	
//	@Autowired
//	private ModerationModel moderationModel;
	
//	@Autowired
//	private VectorStore vectorStore;

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
	
	public ChatResponse generateAnswerWithRoles(String question) {
//		OllamaChatOptions options = new OllamaChatOptions();
//		options.setModel("gemma:2b");
//		options.setTemperature(0.7);
//		options.setMaxTokens(30);
		return chatClient.prompt().system("You are a professional assistant who gives answers on any question.")
				.user(question).call().chatResponse();
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
	
	public float[] getEmbed(String text) {
		return embeddingModel.embed(text);
	}
	
	public double findSimilarity(String text1, String text2) throws Exception {
		List<float[]> floatVectors = embeddingModel.embed(List.of(text1, text2));
		return findCosineSimilarity(floatVectors.get(0), floatVectors.get(1));
		
	}
	
	public double findCosineSimilarity(float[] vector1, float[] vector2) throws Exception {
		if(vector1.length != vector2.length) {
			throw new Exception("Vectors must be of same length!");
		}else {
			//Initialize variables for dot product and magnitudes
			double dotProduct = 0.0;
			double magnitudeA = 0.0;
			double magnitudeB = 0.0;
			
			//Calculateb dot product and magnitude
			for(int i = 0; i < vector1.length; i++) {
				dotProduct += vector1[i] * vector2[i];
				magnitudeA += vector1[i] * vector1[i];
				magnitudeB += vector2[i] * vector2[i];
			}
			
			//Calculate and return cosine similarity
			
			return (dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB)) * 100);
		}
	}
	
	public List<Document> searchJobs(String query){
		return null;
//		return vectorStore.similaritySearch(query);
	}

	public String explainImage(String prompt, String path) {
		String response = chatClient.prompt()
				.user(u -> u.text(prompt).media(MimeTypeUtils.IMAGE_JPEG, new FileSystemResource(path))).call()
				.content();
		return response;
	}
	
	public String getDietAdvice(String prompt, String path1, String path2) {
		String response = chatClient.prompt()
				.user(u -> u.text(prompt).media(MimeTypeUtils.IMAGE_JPEG, new FileSystemResource(path1))
						.media(MimeTypeUtils.IMAGE_JPEG, new FileSystemResource(path2))).call()
				.content();
		return response;
	}
	
	public String callAgent(String query) {
		String response = chatClient.prompt(query).tools(new WeatherTools()).call().content();
		System.out.println("weather response : "+response);
		return response;
	}
	
//	public ModerationResult getModerationStatus(String prompt) {
//		return moderationModel.call(new ModerationPrompt(prompt)).getResult().getOutput().getResults().get(0);
//	}
	
	public Flux<String> generateStreamAnswer(String message) {
		return chatClient.prompt(message).stream().content();
	}
	
	public Flux<ChatResponse> generateStreamAnsWithMetaData(String message) {
		return chatClient.prompt(message).stream().chatResponse();
	}
}
