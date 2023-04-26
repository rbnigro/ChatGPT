package br.com.ronney.chatGPT;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import br.com.ronney.entity.Constants;
import br.com.ronney.methods.CharGPT;

public class Principal {

	// https://platform.openai.com/account/api-keys
	private static String API_KEY;
	
	// https://platform.openai.com/docs/models/gpt-3-5
	private static String myModel = Constants.DEFAULT_MODEL;
	// private static String myPrompt = "Peça o que quiser: ";
	private static String promptScanner = "Crie um texto para me ajudar a vender o meu iPhone 8";
	
	public static void main(String[] args) throws IOException {
	//	Scanner scannerPrompt = new Scanner(System.in);
	//	System.out.print(myPrompt);
	//	promptScanner = scannerPrompt.nextLine();

		System.out.println(promptScanner);
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream("./properties/conf.properties");
		properties.load(fileInputStream);
		API_KEY = properties.getProperty("openai.apikey");
		
		System.out.println("Aguarde...");
		
		chamadaChatGPT(promptScanner);
	//	scannerPrompt.close();
	}

	private static void chamadaChatGPT(String promptScanner) throws IOException {
		CharGPT methodsChatGPT = new CharGPT(API_KEY);
		String retorno = methodsChatGPT.ask(myModel, "user", promptScanner);
		System.out.println(retorno);
	}
}
