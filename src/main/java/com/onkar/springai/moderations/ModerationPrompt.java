package com.onkar.springai.moderations;

public class ModerationPrompt {

	public static String build(String input) {
		return """
				You are a content moderation system.

				Analyze the following text and classify it.

				Categories:
				- SAFE
				- HATE
				- SEXUAL
				- VIOLENCE
				- SELF_HARM
				- ILLEGAL
				- HARASSMENT

				Rules:
				- Respond with ONLY valid JSON
				- Do not explain
				- Do not add extra text

				Format:
				{
				  "safe": true|false,
				  "category": "CATEGORY",
				  "reason": "short reason"
				}

				Text:
				""" + input;
	}
}
