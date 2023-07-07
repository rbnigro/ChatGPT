package br.com.ronney.chatGPT;

import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import br.com.ronney.others.PlayWAV;
import br.com.ronney.tipoChat.ChatPorTexto;

public class Principal {

	private static String sTipoChat = "";
	
	public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
		
	//	System.out.print("Perguntar através de: <T>exto ou <V>oz:");
		
	//	Scanner scannerPrompt = new Scanner(System.in);
	//	sTipoChat = scannerPrompt.nextLine();
		
		
		//if (sTipoChat.toUpperCase().equals("T")) 
			ChatPorTexto.chatPorTexto();
		//else if (sTipoChat.toUpperCase().equals("V")) 
	//		PlayWAV.executarWAV("C:/Users/ronne/OneDrive/Documentos/ChatGPT/chat01.wav");
//		else 
	//		System.out.println("Opção não reconhecida!");
		
		//scannerPrompt.close();
	}
}