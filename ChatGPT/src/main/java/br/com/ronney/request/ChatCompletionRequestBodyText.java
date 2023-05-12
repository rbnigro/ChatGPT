package br.com.ronney.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatCompletionRequestBodyText {
	
    @JsonProperty(value = "model")
    private String model;
    
    @JsonProperty(value = "prompt")
    private String prompt;

    @JsonProperty(value = "max_tokens")
    private Integer maxTokens;

    @JsonProperty(value = "temperature")
    private Float temperature;
}
