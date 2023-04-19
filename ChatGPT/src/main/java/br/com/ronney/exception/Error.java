package br.com.ronney.exception;

import lombok.Getter;

@Getter
public enum Error {

    INVALID_AUTHENTICATION(401, "Erro de Autenticação"),
    INCORRECT_API_KEY_PROVIDED(401, "Chave API Inválida"),
    MUST_BE_A_MEMBER(401, "Cont não localizada API"),
    RATE_LIMIT_REACHED(429, "Limite de requisições"),
    EXCEEDED_YOUR_CURRENT_QUOTA(429, "Quota atingida, reveja seu plano"),
    ENGINE_IS_CURRENTLY_OVERLOADED(429, "Estamos sobrecarregados, tente mais tarde"),
    SERVER_HAD_AN_ERROR(500, "Erro genérico"),
    ;

    private final Integer code;
    private final String msg;

    Error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}