package br.com.ronney.methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import br.com.ronney.entity.ChatCompletionRequestBody;
import br.com.ronney.entity.ChatCompletionResponseBody;
import br.com.ronney.entity.Message;
import br.com.ronney.entity.Model;
import br.com.ronney.erros.Excecoes;
import lombok.extern.slf4j.Slf4j;
import br.com.ronney.entity.Constants;

@Slf4j
public class Official {

	private final String apiKey;
	private String apiHost = Constants.DEFAULT_CHAT_COMPLETION_API_URL;
	protected OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public Official(String apiKey) {
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient();
    }
			
    public Official(String apiKey, OkHttpClient okHttpClient) {
        this.apiKey = apiKey;
        this.okHttpClient = okHttpClient;
    }


    public Official(String apiKey, Proxy proxy) {
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();
    }

    public Official(String apiKey, String proxyHost, int proxyPort) {
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().
                proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
                .build();
    }
    
    public Official(String apiHost, String apiKey) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient();
    }
    
    public Official(String apiHost, String apiKey, OkHttpClient okHttpClient) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
        this.okHttpClient = okHttpClient;
    }

    public Official(String apiHost, String apiKey, Proxy proxy) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();
    }

    public Official(String apiHost, String apiKey, String proxyHost, int proxyPort) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
        this.okHttpClient = new OkHttpClient.Builder().
                proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
                .build();
    }

    public String ask(String input) throws IOException {
        return ask(Constants.DEFAULT_MODEL, "ronneynigro@gmail.com", input);
    }

    public String ask(String user, String input) throws IOException {
        return ask(Constants.DEFAULT_MODEL, "ronneynigro@gmail.com", input);
    }

    public String ask(Model model, String input) throws IOException {
        return ask(Constants.DEFAULT_MODEL, "ronneynigro@gmail.com", input);
    }

    public String ask(List<Message> messages) throws IOException {
        return ask(Constants.DEFAULT_MODEL, messages);
    }

    public String ask(Model model, List<Message> messages) throws IOException {
        return ask(model.getName(), messages);
    }

    public String ask(String model, List<Message> message) throws IOException {
        ChatCompletionResponseBody chatCompletionResponseBody = askOriginal(model, message);
        List<ChatCompletionResponseBody.Choice> choices = chatCompletionResponseBody.getChoices();
        StringBuilder result = new StringBuilder();
        for (ChatCompletionResponseBody.Choice choice : choices) {
            result.append(choice.getText().getContent());
        }
        return result.toString();
    }

    public String ask(Model model, String user, String input) throws IOException {
        return ask(model.getName(), user, input);
    }
    
    private String buildRequestBody(String model, List<Message> messages) {
        try {
            ChatCompletionRequestBody requestBody = ChatCompletionRequestBody.builder()
                    .model(model)
                    .prompt(messages.get(0).getContent())
                    //.messages(messages)
                    .build();
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public ChatCompletionResponseBody askOriginal(String model, String role, String input) throws IOException {
        return askOriginal(model, Collections.singletonList(Message.builder()
                .role(role)
                .content(input)
                .build()));
    }

    public ChatCompletionResponseBody askOriginal(String model, List<Message> message) throws IOException {
        RequestBody body = RequestBody.create(buildRequestBody(model, message), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(apiHost)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        //try (Response response = okHttpClient. newCall(request).execute()) {
        Response response = okHttpClient.newCall(request).execute();
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
       // } catch (IOException e) {
          //  log.error("Request failed: {}", e.getMessage());
        //    throw new Excecoes(Erros.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
    //    }
    }   
    
	public String ask(String model, String role, String content) throws IOException {
		ChatCompletionResponseBody chatCompletionResponseBody = askOriginal(model, role, content);
		List<ChatCompletionResponseBody.Choice> choices = chatCompletionResponseBody.getChoices();
		StringBuilder result = new StringBuilder();
	
		for (ChatCompletionResponseBody.Choice choice : choices) {
			result.append(choice.getText().getContent());
		}
		return result.toString();
	}

}
