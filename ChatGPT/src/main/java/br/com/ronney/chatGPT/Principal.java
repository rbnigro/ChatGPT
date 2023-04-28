package br.com.ronney.chatGPT;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import br.com.ronney.entity.Model;
import br.com.ronney.methods.ChatGPT;
import br.com.ronney.methods.ChatText;

public class Principal {

	// https://platform.openai.com/account/api-keys
	private static String API_KEY;
	
	private static String sUserInput = "";
	private static String sInitPrompt = "";
	private static String sQuestionDialog = "";
	private static String response = "";
	
	//  quero testar minha aplicação que estou fazendo para integrar com o chatGPT
	public static void main(String[] args) throws IOException {
		String sTipo = "l";
		
		int iPassagens = 1;
		
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream("./properties/conf.properties");
		properties.load(fileInputStream);
		API_KEY = properties.getProperty("openai.apikey");
		
		Scanner scannerPrompt = new Scanner(System.in);
		
		while (iPassagens <= 3) {
			System.out.print("Você:");
			sUserInput = scannerPrompt.nextLine();
		
			sInitPrompt += sUserInput + "\nChatbot:";
		
			System.out.println("Aguarde...");
		
			if (!sUserInput.isEmpty())
			{
				sQuestionDialog = sUserInput + sInitPrompt;
				response = chamadaChatGPT(sQuestionDialog, sTipo);
			}
			
			System.out.println(response);
			
			sInitPrompt += response + "\n";
			 
			iPassagens++;
		}
		
		scannerPrompt.close();
	}

	private static String chamadaChatGPT(String promptScanner, String pTipo) { 

		ChatGPT methodsChatGPT;
		ChatText methodsChatText;
		
		String retorno = "<vazio>";
		
		if (pTipo.toUpperCase().equals("C")) {
			methodsChatText = new ChatText(API_KEY);
			retorno = methodsChatText.askText(Model.TEXT_DAVINCI_003.getName(), promptScanner);
		}
		
		if (pTipo.toUpperCase().equals("L")) {
			methodsChatGPT = new ChatGPT(API_KEY);
			retorno = methodsChatGPT.askGPT(Model.GPT_3_5_TURBO_0301.getName(), promptScanner);
		}
		
		return retorno;
	}
}
