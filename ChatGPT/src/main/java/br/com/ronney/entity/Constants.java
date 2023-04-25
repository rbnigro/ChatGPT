package br.com.ronney.entity;

public class Constants {

	private Constants() {

	}

	// buga todo o rolê
	// "https://api.openai.com/v1/chat/completions";
	// Model.GPT_3_5_TURBO_0301.getName();
	
	public static final String DEFAULT_CHAT_COMPLETION_API_URL = "https://api.openai.com/v1/completions";
	public static final String DEFAULT_MODEL = Model.TEXT_DAVINCI_003.getName();
}
