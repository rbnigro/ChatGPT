package br.com.ronney.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionResponseBody {

	@JsonProperty(value = "id")
	public String id;
	
	@JsonProperty(value = "object")
	public String object;
	
	@JsonProperty(value = "created")
	public Long created;
	
	@JsonProperty(value = "model")
	public String model;
	
	@JsonProperty(value = "choices")
	public List<Choice> choices;
	
	@JsonProperty(value = "usage")
	public Usage usage;
	
	@Data
    public static class Choice {
        @JsonProperty(value = "index")
        public Integer index;
        
        @JsonProperty(value = "messages")
        public Messages messages;
        
        @JsonProperty(value = "finish_reason")
        public String finishReason;
    }
 
    @Data
    public static class Usage {
        @JsonProperty(value = "prompt_tokens")
        public Integer promptTokens;
        
        @JsonProperty(value = "completion_tokens")
        public Integer completionTokens;
        
        @JsonProperty(value = "total_tokens")
        public Integer totalTokens;
    }
}
