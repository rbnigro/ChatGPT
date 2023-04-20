package br.com.ronney.methods;

import cn.hutool.core.text.CharSequenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
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

@Slf4j
@Builder
public class UnOfficial {

	// UnofficialChatGPT
	
    private String cookieName = "__Secure-next-auth.session-token";
	private String conversationUrl = "https://chat.openai.com/backend-api/conversation";
	private String userAgent = "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko); compatible; ChatGPT-User/1.0; +https://openai.com/bot";
	private String accessTokenString = "accessToken";
	private String sessionUrl = "https://chat.openai.com/api/auth/session";
	
	private String conversationId;
	private String parentMessageId;
	private final String apiKey;
	private final Gson gson = new Gson();
    private final Map<String, String> accessTokenCache = ExpiringMap.builder().expiration(60, TimeUnit.SECONDS).build();

    protected OkHttpClient client;
    
    public UnOfficial(String apiKey) {
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
		RequestBody body = RequestBody.create(buildRequestBody(model, role, content), MediaType.get("application/json; charset=utf-8"));
		Request request = new Request.Builder()
				.url(apiHost)
				.header("Authorization", "Bearer " + apiKey)
				.post(body)
				.build();

		try (Response response = client.newCall(request).execute()) {
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
				return objectMapper.readValue(bodyString, ChatCompletionResponseBody.class);
			}
		} catch (IOException e) {
			log.error("Request failed: {}", e.getMessage());
			throw new Excecoes(Erros.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
		}
	}
	   
    public String askNoUse(String input) {
        HttpResponse<String> response = Unirest.post(conversationUrl)
                .header("Authorization", "Bearer " + refreshAndGetAccessToken())
                .header("Accept", "text/event-stream")
                .header("Content-Type", "application/json")
               	.header("User-Agent", userAgent)
                .header("X-Openai-Assistant-App-Id", "")
                .header("Connection", "close")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Referer", "https://chat.openai.com/chat")
                .body(buildRequestBody(this.parentMessageId, this.conversationId, input))
                .asString();
        if (response.isSuccess()) {
            String[] strings = CharSequenceUtil.subBetweenAll(response.getBody(), "data: {", "}\n");
            if (strings.length == 0) {
                throw new RuntimeException("Couldn't get correct response, please try again.");
            }
            String data = "{" + strings[strings.length - 1] + "}";
//            System.out.println("Response: " + data);
            JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
            this.conversationId = jsonObject.get("conversation_id").toString();
            this.parentMessageId = jsonObject.getAsJsonObject("message").get("id").toString();
            return jsonObject.getAsJsonObject("message")
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts").get(0)
                    .toString();
        } else {
            throw new RuntimeException("Looks like the server is either overloaded or down. Try again later.");
        }
    }
	
    private String refreshAndGetAccessToken() {
        if (accessTokenCache.isEmpty() || accessTokenCache.get(accessTokenString) == null) {
            if (sessionToken == null) {
               throw new RuntimeException("Please set session token!");
            }
            HttpResponse<JsonNode> response = Unirest.get(sessionUrl)
                    .cookie(cookieName, sessionToken)
                    .header("User-Agent", userAgent)
                    .asJson();
            if (response.isSuccess()) {
                String accessToken = (String) response.getBody().getObject().get(accessTokenString);
//                System.out.println("Get accessToken: " + accessToken);
                accessTokenCache.put(accessTokenString, accessToken);
                return accessToken;
            } else {
                throw new RuntimeException("Session token may have expired");
            }
        } else {
            return accessTokenCache.get(accessTokenString);
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
