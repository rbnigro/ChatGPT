package br.com.ronney.request;

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
public class ChatCompletionRequestBodyGPT {

    @JsonProperty(value = "model")
    private String model;
    
    @JsonProperty(value = "max_tokens")
    private Integer max_tokens;
    
    @JsonProperty(value = "n")
    private Integer n;
    
    @JsonProperty(value = "stop")
    private String stop;
    
    @JsonProperty(value = "messages")
    private List<Message> messages;
    
    @JsonProperty(value = "temperature")
    private Float temperature;
}
