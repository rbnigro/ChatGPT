package br.com.ronney.chatGPT;

import java.io.IOException;
import java.util.Scanner;

import br.com.ronney.tipoChat.ChatPorTexto;

public class Principal {

	private static String sUserInput = "";
	
	public static void main(String[] args) throws IOException {
		Scanner scannerPrompt = new Scanner(System.in);
		
		System.out.print("Tipo de Chat <T>exto ou <V>oz:");
		sUserInput = scannerPrompt.nextLine();
		
		if (sUserInput.toUpperCase().equals("T")) {
			ChatPorTexto.chatPorTexto();
		}
		else if (sUserInput.toUpperCase().equals("V")) {
			System.out.println("Chat em Voz");
		}
		else {
			System.out.println("Opção não reconhecida!");
		}
	}
}