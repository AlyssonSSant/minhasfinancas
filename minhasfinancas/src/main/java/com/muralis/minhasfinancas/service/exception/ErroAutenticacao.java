package com.muralis.minhasfinancas.service.exception;

public class ErroAutenticacao extends RuntimeException {
    public ErroAutenticacao(String message)
    {
       super(message);
    }
}
