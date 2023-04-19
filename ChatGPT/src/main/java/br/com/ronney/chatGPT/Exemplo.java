package br.com.ronney.chatGPT;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class Exemplo {

	// https://platform.openai.com/account/api-keys
	private static final String API_KEY = "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";
	
	// https://platform.openai.com/docs/models/gpt-3-5
	private static final String myModel = "text-davinci-003";
	
	private static final String myPrompt = "Slogan para uma barraca de sorvete:";
	
	public static void main(String[] args) {
		OpenAiService openAiService = new OpenAiService(API_KEY);	
		CompletionRequest completionRequest = CompletionRequest
				.builder()
				.model(myModel)
				.prompt(myPrompt)
				.maxTokens(100)
				.build();
		System.out.println(openAiService.createCompletion(completionRequest).getChoices());
	}

}
