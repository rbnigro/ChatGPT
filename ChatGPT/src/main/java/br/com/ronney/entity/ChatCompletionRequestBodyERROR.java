package br.com.ronney.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatCompletionRequestBodyERROR {

    @JsonProperty(value = "model")
    private String model;
    
    @JsonProperty(value = "choice")
    private List<MessageLocal> choice;
    
    @Data
    public static class Choices {
    	@JsonProperty(value = "message")
    	public MessageLocal message;
    }
    
    @Data
    public static class MessageLocal {
    	@JsonProperty(value = "role")    	
    	public String role;
    	
    	@JsonProperty(value = "content")
    	public String content;
    }
}
