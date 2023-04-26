package br.com.ronney.entity.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ronney.entity.Messages;
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
	public List<Messages> choices;

    @JsonProperty(value = "finish_reason")
    public String finishReason;
    
    @JsonProperty(value = "index")
    public Integer index;
    
    @Data
    public static class Choices {
    	@JsonProperty(value = "message")
    	public Messages message;
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

	public Object getChoices() {
		// TODO Auto-generated method stub
		return null;
	}
}
