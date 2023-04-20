package br.com.ronney.erros;

import lombok.Getter;

@Getter
public enum Erros {

    INVALID_AUTHENTICATION(401, "Invalid Authentication"),
    INCORRECT_API_KEY_PROVIDED(401, "Incorrect API key provided"),
    MUST_BE_A_MEMBER(401, "You must be a member of an organization to use the API"),
    RATE_LIMIT_REACHED(429, "Rate limit reached for requests"),
    EXCEEDED_YOUR_CURRENT_QUOTA(429, "You exceeded your current quota, please check your plan and billing details"),
    ENGINE_IS_CURRENTLY_OVERLOADED(429, "The engine is currently overloaded, please try again later"),
    SERVER_HAD_AN_ERROR(500, "The server had an error while processing your request"),
    ;

    @SuppressWarnings("unused")
	private final Integer code;
    
    @SuppressWarnings("unused")
	private final String msg;

    Erros(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}