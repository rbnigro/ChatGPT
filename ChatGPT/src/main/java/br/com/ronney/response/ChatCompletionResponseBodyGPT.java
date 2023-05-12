package br.com.ronney.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ronney.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatCompletionResponseBodyGPT {
	@JsonProperty(value = "id")
	public String id;
	
	@JsonProperty(value = "object")
	public String object;
	
	@JsonProperty(value = "created")
	public Long created;
	
	@JsonProperty(value = "model")
	public String model;
	
	@JsonProperty(value = "usage")
	public Usage usage;
	
	@JsonProperty(value = "choices")
	public List<Choice> choices;

    @Data
    public static class Choice {
        @JsonProperty(value = "index")
        public Integer index;
        
    	@JsonProperty(value = "message")
    	public Message message;

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
