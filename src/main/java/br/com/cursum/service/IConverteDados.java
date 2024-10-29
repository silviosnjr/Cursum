package br.com.cursum.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
