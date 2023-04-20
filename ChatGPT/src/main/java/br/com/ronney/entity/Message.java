package br.com.ronney.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @JsonProperty(value = "role")
    public String role;
    
    @JsonProperty(value = "content")
    public String content;    
}
