package br.com.ronney.tipoChat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import br.com.ronney.voices.Voices;
import br.com.ronney.entity.TextModel;
import br.com.ronney.methods.ChatGPT;
import br.com.ronney.methods.ChatText;

public class ChatPorTexto {

	private static String API_KEY; 
	private static String sUserInput = "";
	private static String sInitPrompt = "";
	private static String sQuestionDialog = "";
	private static String response = "";
	
	public static void chatPorTexto()  {
		String sTipo = "l";
		
		int iPassagens = 1;
		
		Properties properties = new Properties();
		FileInputStream fileInputStream;
		
		try {
			fileInputStream = new FileInputStream("./properties/conf.properties");
			properties.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		API_KEY = properties.getProperty("openai.apikey");
		
		Scanner scannerPrompt = new Scanner(System.in);
		
		while (iPassagens <= 3) {
			System.out.print("Você:");
			sUserInput = scannerPrompt.nextLine();
		
			sInitPrompt += sUserInput + "\nChatbot:";
		
			System.out.println("Estou pensando, por favor espere...");
		
			if (!sUserInput.isEmpty())
			{
				sQuestionDialog = sUserInput + sInitPrompt;
				response = chamadaChatGPT(sQuestionDialog, sTipo);
				
				System.out.println(response);
				
				try (Scanner scannerTypeAnswer = new Scanner(System.in)) {
					String sTypeAnswer = scannerTypeAnswer.nextLine();
					
				//	System.out.println("Resposta por <G>oogle (PT-BR) ou <F>reeTTS (EN-US): ");
					Voices voice = new Voices();
					
				//	if (sTypeAnswer.toUpperCase().equals("F")) {
				//		voice.AnswerFreeTTS(response);
				//	} else {
						voice.AnswerGoogle(response);
				//	}
				}
					
				
			}
			
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
			retorno = methodsChatText.askText(TextModel.TEXT_DAVINCI_003.getName(), promptScanner);
		}
		
		if (pTipo.toUpperCase().equals("L")) {
			methodsChatGPT = new ChatGPT(API_KEY);
			retorno = methodsChatGPT.askGPT(TextModel.GPT_3_5_TURBO_0301.getName(), promptScanner);
		}
		
		return retorno;
	}
}
