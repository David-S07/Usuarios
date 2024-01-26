package br.com.usuarios.controller;

import br.com.usuarios.model.entity.Usuario;
import br.com.usuarios.model.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUsuarioTest() {

        Usuario usuario = Usuario.builder()
                .nome("Maria")
                .senha("Teste@123")
                .confirmacaoSenha("Teste@123")
                .forcaSenha(4)
                .build();

        usuarioRepository.save(usuario);
        Assertions.assertThat(usuario.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUsuarioTest() {

        Usuario usuario = usuarioRepository.findById(1).get();
        Assertions.assertThat(usuario.getId()).isEqualTo(1L);

    }

    @Test
    @Order(3)
    public void getListOfUsuarioTest() {

        List<Usuario> usuarioList = usuarioRepository.findAll();
        Assertions.assertThat(usuarioList.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUsuarioTest() {

        Usuario usuario = usuarioRepository.findById(1).get();
        usuario.setNome("Manoela");
        Usuario usuarioUpdated = usuarioRepository.save(usuario);
        Assertions.assertThat(usuarioUpdated.getNome()).isEqualTo("Manoela");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUsuarioTest() {

        Usuario usuario = usuarioRepository.findById(1).get();
        usuarioRepository.delete(usuario);
        Usuario usuario1 = null;
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(1);

        if (optionalUsuario.isPresent()) {
            usuario1 = optionalUsuario.get();
        }
        Assertions.assertThat(usuario1).isNull();
    }
}
