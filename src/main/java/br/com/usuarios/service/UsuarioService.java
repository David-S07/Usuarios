package br.com.usuarios.service;

import br.com.usuarios.model.entity.Usuario;
import br.com.usuarios.model.repository.UsuarioRepository;
import br.com.usuarios.rest.exception.UsuarioCadastradoException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    Integer forcaSenha = 0;

    public Usuario salvar(Usuario usuario) {
        if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
            throw new UsuarioCadastradoException(usuario.getSenha());
        }
        senhaForte(usuario.getSenha());
        usuario.setForcaSenha(forcaSenha);
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        usuario.setSenha(passwordEncryptor.encryptPassword(usuario.getSenha()));
        usuario.setConfirmacaoSenha(passwordEncryptor.encryptPassword(usuario.getConfirmacaoSenha()));
        return repository.save(usuario);
    }

    public Integer senhaForte(String senha) {

        boolean achouNumero = false;
        boolean achouMaiuscula = false;
        boolean achouMinuscula = false;
        boolean achouSimbolo = false;
        for (char c : senha.toCharArray()) {
            if (c >= '0' && c <= '9') {
                achouNumero = true;
            } else if (c >= 'A' && c <= 'Z') {
                achouMaiuscula = true;
            } else if (c >= 'a' && c <= 'z') {
                achouMinuscula = true;
            } else {
                achouSimbolo = true;
            }
        }

        forcaSenha = 0;
        if (achouNumero) {
            forcaSenha = ++forcaSenha;
        }
        if (achouMaiuscula) {
            forcaSenha = ++forcaSenha;
        }
        if (achouMinuscula) {
            forcaSenha = ++forcaSenha;
        }
        if (achouSimbolo) {
            forcaSenha = ++forcaSenha;
        }

        return forcaSenha;
    }
}
