package br.com.ronney.chatGPT;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

import br.com.ronney.methods.Official;

public class Principal {

	// https://platform.openai.com/account/api-keys
	private static String API_KEY;
	
	// https://platform.openai.com/docs/models/gpt-3-5
	private static final String myModel = "text-davinci-003";
	
	private static final String myPrompt = "Peça o que quiser: ";
	
	public static void main(String[] args) throws IOException {
		// chamadaBasica();
		
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream("./properties/conf.properties");
		properties.load(fileInputStream);
		API_KEY = properties.getProperty("openai.apikey");
		chamadaSecundaria(API_KEY);
	}

	private static void chamadaSecundaria(String apiKey) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(myPrompt);
		String prompt = scanner.nextLine();
		scanner.close();
		Official methodsChatGPT = new Official(apiKey);
		methodsChatGPT.ask(myModel, "", prompt);
	}
	
	private static void chamadaBasica() {
		Scanner scanner = new Scanner(System.in);
		System.out.print(myPrompt);
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
		// Crie um texto para me ajduar a vender o meu iPhone 8
		System.out.println(openAiService.createCompletion(completionRequest).getChoices());
	}
}
