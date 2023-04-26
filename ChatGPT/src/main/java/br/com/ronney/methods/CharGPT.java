package br.com.ronney.methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.ronney.entity.Messages;
import br.com.ronney.entity.Model;
import br.com.ronney.entity.request.ChatCompletionRequestBodyText;
import br.com.ronney.entity.response.ChatCompletionResponseBodyText;
import br.com.ronney.erros.Erros;
import br.com.ronney.erros.Excecoes;
import lombok.extern.slf4j.Slf4j;
import br.com.ronney.entity.Constants;

@Slf4j
public class CharGPT {

	private final String apiKey;
	private String apiHost = Constants.DEFAULT_CHAT_COMPLETION_API_URL;
	protected OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public CharGPT(String apiKey) {
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient();
    }
			
    public String ask(String model, List<Messages> messages) {
        ChatCompletionResponseBodyText chatCompletionResponseBody = askModelMessages(model, messages);
        List<ChatCompletionResponseBodyText.Choices> choices = chatCompletionResponseBody.getChoices();
        StringBuilder result = new StringBuilder();
        for (ChatCompletionResponseBodyText.Choices choice : choices) {
            result.append(choice.getText());
        }
        return result.toString();
    }

    public String ask(Model model, String user, String input) {
        return ask(model.getName(), user, input);
    }
    
    private String buildRequestBody(String model, List<Messages> messages) {
        try {
            ChatCompletionRequestBodyText requestBody = ChatCompletionRequestBodyText.builder()
                    .model(model)
                    .prompt(messages.get(0).getContent())
                    .temperature((float) 1.2)
                    .build();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ChatCompletionResponseBodyText askOriginal(String model, String role, String input) {
    	ChatCompletionResponseBodyText chatCompletionResponseBody = askModelMessages(model, Collections.singletonList(Messages.builder() 
                .role(role)
                .content(input)
                .build())); 
    	return chatCompletionResponseBody;
    }

    public ChatCompletionResponseBodyText askModelMessages(String model, List<Messages> messages) {
        RequestBody body = RequestBody.create(buildRequestBody(model, messages), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder() 
                .url(apiHost)
                .header("Authorization", "Bearer " + apiKey)
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
    
	public String ask(String model, String role, String content) {
		ChatCompletionResponseBodyText chatCompletionResponseBody = askOriginal(model, role, content);
		List<ChatCompletionResponseBodyText.Choices> choices = chatCompletionResponseBody.getChoices();
		StringBuilder result = new StringBuilder();
	
		for (ChatCompletionResponseBodyText.Choices choice : choices) {
			result.append(choice.getText());
		}
		return result.toString();
	}

}
