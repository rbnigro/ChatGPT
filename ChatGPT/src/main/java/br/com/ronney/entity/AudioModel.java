package br.com.ronney.entity;

import lombok.Getter;

@Getter
public enum AudioModel {
	WHISPER_1("whisper-1");
	
	private final String name;
	
	AudioModel(String name) {
        this.name = name;
    }
}
