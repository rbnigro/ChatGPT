package br.com.ronney.methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.ronney.entity.Message;
import br.com.ronney.request.ChatCompletionRequestBodyText;
import br.com.ronney.response.ChatCompletionResponseBodyText;
import br.com.ronney.erros.Erros;
import br.com.ronney.erros.Excecoes;
import lombok.extern.slf4j.Slf4j;
import br.com.ronney.entity.Constants;

@Slf4j
public class ChatText {

	private final String apiKey;
	private String apiHost = Constants.DEFAULT_COMPLETION_API_URL;
	protected OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public ChatText(String apiKey) { // 1
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient();
    }
			
    private String buildRequestBody(String model, List<Message> messages) { // 5
        try {
            ChatCompletionRequestBodyText requestBody = ChatCompletionRequestBodyText.builder()
                    .model(model)
                    .prompt(messages.get(0).getContent())
                    .maxTokens(1024)
                    .temperature((float) 1.2)
                    .build();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ChatCompletionResponseBodyText askOriginalText(String model, String role, String input) { // 3
    	ChatCompletionResponseBodyText chatCompletionResponseBody = askModelMessages(model, Collections.singletonList(Message.builder() 
                .role(role)
                .content(input)
                .build())); 
    	return chatCompletionResponseBody;
    }

    public ChatCompletionResponseBodyText askModelMessages(String model, List<Message> messages) { // 4
        RequestBody body = RequestBody.create(buildRequestBody(model, messages), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder() 
                .url(apiHost)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = okHttpClient. newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.body() == null) {
                    log.error("Request failed: {}, please try again", response.message());
                    throw new Excecoes(response.code(), "Request failed");
                } else {
                    log.error("Request failed: {}, please try again", response.body().string());
                    throw new Excecoes(response.code(), response.body().string());
                }
            } else {
                assert response.body() != null;
                String bodyString = response.body().string();
                return objectMapper.readValue(bodyString, ChatCompletionResponseBodyText.class);
            }
        } catch (IOException e) {
            log.error("Request failed: {}", e.getMessage());
            throw new Excecoes(Erros.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
        }
    }   
    
	public String askText(String model, String content) { // 2
		ChatCompletionResponseBodyText chatCompletionResponseBodyText = askOriginalText(model, "user", content);
		List<ChatCompletionResponseBodyText.Choices> choices = chatCompletionResponseBodyText.getChoices();
		StringBuilder result = new StringBuilder();
	
		for (ChatCompletionResponseBodyText.Choices choice : choices) {
			result.append(choice.getText());
		}
		return result.toString();
	}	
}
