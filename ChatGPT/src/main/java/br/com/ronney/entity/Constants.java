package br.com.ronney.entity;

public class Constants {

	private Constants() {

	}

	public static final String BASE_URL = "https://api.openai.com/v1/";
		
	public static final String DEFAULT_COMPLETION_API_URL = BASE_URL + "completions";
	public static final String DEFAULT_CHAT_COMPLETION_API_URL = BASE_URL + "chat/completions";
	
	public static final String DEFAULT_TRANSCRIPTION_URL =  BASE_URL + "/audio/transcriptions";
}
