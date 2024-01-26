package br.com.usuarios;

import br.com.usuarios.model.entity.Usuario;
import br.com.usuarios.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsuariosAplication {

    @Bean
    public CommandLineRunner run(@Autowired UsuarioRepository repository){
        return args -> {
            Usuario usuario1 = Usuario.builder().nome("Fulano").senha("testeteste")
                    .confirmacaoSenha("testeteste").forcaSenha(1).build();
            Usuario usuario2 = Usuario.builder().nome("Ciclano Freitas").senha("teste123")
                    .confirmacaoSenha("teste123").forcaSenha(2).build();
            Usuario usuario3 = Usuario.builder().nome("David Santana").senha("Teste123")
                    .confirmacaoSenha("954773858").forcaSenha(3).build();
            Usuario usuario4 = Usuario.builder().nome("Joao Augusto").senha("Teste@123")
                    .confirmacaoSenha("Teste@123").forcaSenha(4).build();
            repository.save(usuario1);
            repository.save(usuario2);
            repository.save(usuario3);
            repository.save(usuario4);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(UsuariosAplication.class, args);
    }
}
