package br.com.usuarios.rest;

import br.com.usuarios.model.entity.Usuario;
import br.com.usuarios.model.repository.UsuarioRepository;
import br.com.usuarios.rest.exception.UsuarioCadastradoException;
import br.com.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("http://localhost:4200")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioRepository repository, UsuarioService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<Usuario> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar (@RequestBody @Valid Usuario usuario) {
        try{
           return service.salvar(usuario);
        } catch (UsuarioCadastradoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{id}")
    public Usuario acharPorId(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND , "Usuario não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Integer id) {
         repository.findById(id).
                map(usuario -> {
                    repository.delete(usuario);
                    return Void.TYPE;
                }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado, UsuarioService usuarioService) {

        repository.findById(id).
                map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setSenha(usuarioAtualizado.getSenha());
                    usuario.setConfirmacaoSenha(usuarioAtualizado.getConfirmacaoSenha());
                    usuario.setForcaSenha(usuarioAtualizado.getForcaSenha());
                    return service.salvar(usuario);
                }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        }
}
