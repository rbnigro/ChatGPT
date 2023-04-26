package br.com.ronney.entity;

import lombok.Getter;

@Getter
public enum Model {
    GPT_3_5_TURBO("gpt-3.5-turbo"),				// novo endpoit
    GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),	// novo endpoit
    TEXT_DAVINCI_003("text-davinci-003"), 
    TEXT_DAVINCI_002("text-davinci-002"),
    TEXT_DAVINCI_001("text-davinci-001"),
    ;
	
    private final String name;

    Model(String name) {
        this.name = name;
    }
}
