package com.onkar.springai.services;

import org.springframework.ai.tool.annotation.Tool;

public class WeatherTools {

	@Tool(description = "Fetches the current weather condition for a given city")
	public String getWeather(String city) {
		System.out.println("Seeks weather info only");
		return "The current weather in the "+city+" is 40 degree celcius with light winds.";
	}
	
	@Tool(description = "Provides weather related advice based on given weather")
	public String getWeatherAdvice(String weather) {
		System.out.println("Seeks weather advice only");
		if(weather.equalsIgnoreCase("rain")) {
			return "Carry an Umbrella! It is expected rain.";
		}else if(weather.equalsIgnoreCase("cold")) {
			return "Wear warm clothes!";
		}else {
			return "Weather looks fine!";
		}
	}
}
