package br.com.usuarios.rest.exception;

public class UsuarioCadastradoException extends RuntimeException {

    public UsuarioCadastradoException(String senha) {
        super("As senhas digitadas são divergentes ! ");
    }
}
