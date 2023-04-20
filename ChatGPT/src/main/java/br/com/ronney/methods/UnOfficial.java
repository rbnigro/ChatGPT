package br.com.ronney.methods;

import cn.hutool.core.text.CharSequenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import net.jodah.expiringmap.ExpiringMap;
import okhttp3.OkHttpClient;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Deprecated
public class UnOfficial {

    private String cookieName = "__Secure-next-auth.session-token";
	private String conversationUrl = "https://chat.openai.com/backend-api/conversation";
	private String userAgent = "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko); compatible; ChatGPT-User/1.0; +https://openai.com/bot";
	private String accessTokenString = "accessToken";
	private String sessionUrl = "https://chat.openai.com/api/auth/session";
	
	private String conversationId;
	private String parentMessageId;
	private final Gson gson = new Gson();
    private final Map<String, String> accessTokenCache = ExpiringMap.builder().expiration(60, TimeUnit.SECONDS).build();

    protected OkHttpClient client;
    
    public UnOfficial(String apiKey) {
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
	
    public String ask(String input) {
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
