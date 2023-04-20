package br.com.ronney.erros;

import lombok.Builder;

@Builder
public class Excecoes extends RuntimeException {

	private static final long serialVersionUID = -1293237038165033504L;

	private final Integer code;
	private final String msg;

    public Excecoes(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Excecoes(Erros erros) {
        super(erros.getMsg());
        this.code = erros.getCode();
        this.msg = erros.getMsg();
    }

}
