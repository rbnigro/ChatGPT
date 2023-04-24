package br.com.ronney.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatCompletionRequestBody {
	
    @JsonProperty(value = "model")
    private String model;
    
    @JsonProperty(value = "prompt")
    private String prompt;

    @JsonProperty(value = "max_tokens")
    private Integer maxTokens;

    @JsonProperty(value = "temperature")
    private Float temperature;

    @JsonProperty(value = "top_p")
    private Float topP;
    
    @JsonProperty(value = "n")
    private Integer n;
    
    @JsonProperty(value = "stream")
    private Boolean stream;
    
    @JsonProperty(value = "stop")
    private List<String> stop;
    
    @JsonProperty(value = "logprobs")
    private List<String> logprobs;
    
    //@JsonProperty(value = "presence_penalty")
    //private Float presencePenalty;
    
    //@JsonProperty(value = "frequency_penalty")
    //private Float frequencyPenalty;
    
    //@JsonProperty(value = "logit_bias")
    //private Map<Object, Object> logitBias;
    
   // @JsonProperty(value = "messages")
   // private List<Messages> messages;

   // private String user = "";
}
