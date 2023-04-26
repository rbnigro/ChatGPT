package br.com.ronney.entity.request;

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
public class ChatCompletionRequestBodyGPT {

    @JsonProperty(value = "model")
    private String model;
    
    @JsonProperty(value = "messages")
    private List<Messages> messages;
    
    @JsonProperty(value = "temperature")
    private Float temperature;
}
