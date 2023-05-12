package br.com.ronney.chatGPT;

import org.apache.commons.lang3.StringUtils;

public class MeusTestes {

	public static void main(String[] args) {
		final String sChassi = "8779879*VNEOVEOI";
		
		String sPrefixo = StringUtils.substringBefore(sChassi, "*");
		String sSufixo = StringUtils.substringAfter(sChassi, "*");;
		
		
		System.out.println("sPrefixo: " + sPrefixo);
		System.out.println("sSufixo: " + sSufixo);	
	}

}
