package br.com.ronney.methods;

import cn.hutool.core.text.CharSequenceUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.ronney.entity.ChatCompletionRequestBody;
import br.com.ronney.entity.ChatCompletionResponseBody;
import br.com.ronney.erros.Erros;
import br.com.ronney.erros.Excecoes;

//@Slf4j
public class Official {

	private String parentMessageId;
	private String apiHost = "DEFAULT_CHAT_COMPLETION_API_URL";
	private final String apiKey;
	private final Gson gson = new Gson();
    private final Map<String, String> accessTokenCache = ExpiringMap.builder().expiration(60, TimeUnit.SECONDS).build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    protected OkHttpClient client;
    
    public Official(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }
			//TODO transformar em Classe
			private String sessionToken;
	
			public String getSessionToken() {
				return sessionToken;
			}

			public void setSessionToken(String sessionToken) {
				this.sessionToken = sessionToken;
			}
			
	public String ask(String model, String role, String content) {
		ChatCompletionResponseBody chatCompletionResponseBody = askOriginal(model, role, content);
		List<ChatCompletionResponseBody.Choice> choices = chatCompletionResponseBody.getChoices();
		StringBuilder result = new StringBuilder();
	
		for (ChatCompletionResponseBody.Choice choice : choices) {
			result.append(choice.getMessage().getContent());
		}
		return result.toString();
	}
	
	public ChatCompletionResponseBody askOriginal(String model, String role, String content) {
		RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), buildRequestBody(model, role, content));
		Request request = new Request.Builder()
				.url(apiHost)
				.header("Authorization", "Bearer " + apiKey)
				.post(body)
				.build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				if (response.body() == null) {
					//log.error("Request failed: {}, please try again", response.message());
					throw new Excecoes(response.code(), "Request failed");
				} else {
					//log.error("Request failed: {}, please try again", response.body().string());
					throw new Excecoes(response.code(), response.body().string());
				}
			} else {
				assert response.body() != null;
				String bodyString = response.body().string();
				return objectMapper.readValue(bodyString, ChatCompletionResponseBody.class);
			}
		} catch (IOException e) {
			//log.error("Request failed: {}", e.getMessage());
			throw new Excecoes(Erros.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
		}
	}
	   
    private String buildRequestBody(String parentMessageId, String conversationId, String input) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("action", "variant");
        requestBody.addProperty("parent_message_id", parentMessageId);
        requestBody.addProperty("model", "text-davinci-002-render");
        JsonArray jsonArray = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("id", conversationId);
        message.addProperty("role", "user");
        JsonObject content = new JsonObject();
        content.addProperty("content_type", "text");
        JsonArray parts = new JsonArray();
        parts.add(input);
        content.add("parts", parts);
        message.add("content", content);
        requestBody.add("messages", jsonArray);
        return requestBody.toString();
    }
}
