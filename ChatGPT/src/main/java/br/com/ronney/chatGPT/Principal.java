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
	
	private static String myPrompt = "Peça o que quiser: ";
	private static String promptScanner = "Crie um texto para me ajduar a vender o meu iPhone 8";
	
	public static void main(String[] args) throws IOException {
		String tipoChamada = "Chamada <B>asica ou <S>ecundaria: ";
		
		Scanner scannerChamada = new Scanner(System.in);
		System.out.print(tipoChamada);
		String promptChamada = scannerChamada.nextLine();
		
		Scanner scannerPrompt = new Scanner(System.in);
		System.out.print(myPrompt);
		promptScanner = scannerPrompt.nextLine();

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream("./properties/conf.properties");
		properties.load(fileInputStream);
		API_KEY = properties.getProperty("openai.apikey");

		if (promptChamada.equals("B")) 
			chamadaBasica(promptScanner);
		else if (promptChamada.equals("S")) 
			chamadaSecundaria(promptScanner);
		else
			System.out.println("Tipo de Chamada não tratada!");
		
		scannerChamada.close();
		scannerPrompt.close();
	}

	private static void chamadaSecundaria(String promptScanner) {
		Official methodsChatGPT = new Official(API_KEY);
		methodsChatGPT.ask(myModel, "", promptScanner);
	}
	
	private static void chamadaBasica(String prompt) {
	
		OpenAiService openAiService = new OpenAiService(API_KEY);	
		CompletionRequest completionRequest = CompletionRequest
				.builder()
				.model(myModel)
				.prompt(prompt)
				.maxTokens(100)
			//	.temperature(0.0) // variação de respostas
				.build();
		
		System.out.println(openAiService.createCompletion(completionRequest).getChoices());
	}
}
