package br.com.ronney.chatGPT;

import java.util.Scanner;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class Exemplo {

	// https://platform.openai.com/account/api-keys
	private static final String API_KEY = "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";
	
	// https://platform.openai.com/docs/models/gpt-3-5
	private static final String myModel = "text-davinci-003";
	
	private static final String myPrompt = "Peça o que quiser: ";
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print(myPrompt);
		//String prompt = "Qual o seu nome?"; 
		String prompt = scanner.nextLine();
		
		OpenAiService openAiService = new OpenAiService(API_KEY);	
		CompletionRequest completionRequest = CompletionRequest
				.builder()
				.model(myModel)
				.prompt(prompt)
				.maxTokens(100)
			//	.temperature(0.0) // variação de respostas
				.build();
		
		scanner.close();
		
		System.out.println("R.:" + openAiService.createCompletion(completionRequest).getChoices());
	}

}
