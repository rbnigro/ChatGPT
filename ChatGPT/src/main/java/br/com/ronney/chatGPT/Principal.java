package br.com.ronney.chatGPT;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.ronney.entity.Constants;
import br.com.ronney.methods.Official;
import okhttp3.OkHttpClient;

public class Principal {

	// https://platform.openai.com/account/api-keys
	private static String API_KEY;
	
	private static String API_USER;
	
	// https://platform.openai.com/docs/models/gpt-3-5
	private static String myModel = Constants.DEFAULT_MODEL;
	
	private static String myPrompt = "Peça o que quiser: ";
	private static String promptScanner = "Crie um texto para me ajduar a vender o meu iPhone 8";
	
	public static void main(String[] args) throws IOException {
	//	String tipoChamada = "Chamada <B>asica ou <S>ecundaria: ";
		
	//	Scanner scannerChamada = new Scanner(System.in);
	//	System.out.print(tipoChamada);
	//	String promptChamada = scannerChamada.nextLine();
		
	//	Scanner scannerPrompt = new Scanner(System.in);
	//	System.out.print(myPrompt);
	//	promptScanner = scannerPrompt.nextLine();

		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream("./properties/conf.properties");
		properties.load(fileInputStream);
		API_KEY = properties.getProperty("openai.apikey");
		API_USER = properties.getProperty("openai.apiuser");
		
		System.out.println("Aguarde...");
		
	//	if (promptChamada.toUpperCase().equals("B")) 
	//		chamadaBasica(promptScanner);
	//	else if (promppromptChamada.toUpperCase().equals("S"))
	//		novaChamadaBasica();
	//	else if (promptChamada.toUpperCase().equals("S")) 
			chamadaSecundaria(promptScanner);
	//  else if (promptChamada.toUpperCase().equals("P"))
	//	    chamadaPronta();
	//	else
	//		System.out.println("Tipo de Chamada não tratada!");
		
	//	scannerChamada.close();
	//	scannerPrompt.close();
	}

	private static void chamadaSecundaria(String promptScanner) throws IOException {
		Official methodsChatGPT = new Official(API_KEY);
		// String retorno = methodsChatGPT.ask(myModel, API_USER, promptScanner);
		String retorno = methodsChatGPT.ask(myModel, "user", promptScanner);
		System.out.println(retorno);
	}
	
	private static void chamadaBasica(String prompt) {
	
	//	OpenAiService openAiService = new OpenAiService(API_KEY);	
	//	CompletionRequest completionRequest = CompletionRequest
	//			.builder()
	//			.prompt(prompt)
	//			.model(myModel)
	//			.echo(true)
	//			.maxTokens(100)
	//			.temperature(1.1) // variação de respostas
	//			.build();
		
        // openAiService.createCompletion(completionRequest).getChoices().forEach(System.out::println);
	//	System.out.println(openAiService.createCompletion(completionRequest).getChoices());
	}
	
	private static void chamadaPronta() throws IOException {
		Official chatGPT = new Official(API_KEY);
	    String hello = chatGPT.ask(myModel, API_USER, promptScanner);
	    System.out.println(hello); 
	}
}
