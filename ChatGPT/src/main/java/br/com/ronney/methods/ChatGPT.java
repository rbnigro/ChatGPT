package br.com.ronney.methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.ronney.entity.Message;
import br.com.ronney.request.ChatCompletionRequestBodyGPT;
import br.com.ronney.response.ChatCompletionResponseBodyGPT;
import br.com.ronney.response.ChatCompletionResponseBodyGPT.Choice;
import br.com.ronney.erros.Erros;
import br.com.ronney.erros.Excecoes;
import br.com.ronney.entity.Constants;

public class ChatGPT {

	private final String apiKey;
	private String apiHost = Constants.DEFAULT_CHAT_COMPLETION_API_URL;
	protected OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
   // private static final Logger logger = LogManager.getLogger(ChatGPT.class);  
    
    public ChatGPT(String apiKey) { // 1
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient();
    }
			
    private String buildRequestBody(String model, List<Message> messages) { // 5
        try {
            ChatCompletionRequestBodyGPT requestBody = ChatCompletionRequestBodyGPT.builder()
                    .model(model)
                    //.max_tokens(4096)
                    .messages(messages)
                   // .n(1) // Quantidade de conclusões
                   // .stop(":")
                    .temperature((float) 1.2)
                    .build();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ChatCompletionResponseBodyGPT askOriginalGPT(String model, String role, String input) { // 3
    	ChatCompletionResponseBodyGPT chatCompletionResponseBody = askModelMessages(model, Collections.singletonList(Message.builder() 
                .role(role)
                .content(input)
                .build())); 
    	return chatCompletionResponseBody;
    }

    public ChatCompletionResponseBodyGPT askModelMessages(String model, List<Message> messages) { // 4
        RequestBody body = RequestBody.create(buildRequestBody(model, messages), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder() 
                .url(apiHost)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = okHttpClient. newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.body() == null) {
               // 	logger.error("Request failed: {}, please try again" + response.message());
                    throw new Excecoes(response.code(), "Request failed");
                } else {
                //	logger.error("Request failed: {}, please try again" + response.body().string());
                    throw new Excecoes(response.code(), response.body().string());
                }
            } else {
                assert response.body() != null;
                String bodyString = response.body().string();
                return objectMapper.readValue(bodyString, ChatCompletionResponseBodyGPT.class);
            }
        } catch (IOException e) {
        //	logger.error("Request failed: " + e.getMessage());
            throw new Excecoes(Erros.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
        }
    }   

    public String askGPT(String model, String content) { // 2
        ChatCompletionResponseBodyGPT chatCompletionResponseBodyGPT = askOriginalGPT(model, "user", content);
        List<ChatCompletionResponseBodyGPT.Choice> choices = (List<Choice>) chatCompletionResponseBodyGPT.getChoices();
        StringBuilder result = new StringBuilder();
        
        for (ChatCompletionResponseBodyGPT.Choice choice : choices) {
            result.append(choice.getMessage().getContent());
        }
        return result.toString();
    }
}
