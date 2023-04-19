package br.com.ronney.chatGPT;

import java.util.Scanner;

public class LerConsoleComScanner {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Qual o seu nome: ");
        String nome = scanner.nextLine();
        System.out.println("Seja bem vindo " + nome + "!");
	}

}
