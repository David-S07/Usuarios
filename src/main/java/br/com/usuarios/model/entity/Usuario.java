package br.com.usuarios.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    @Column(nullable = false, length = 150)
    private String nome;
    @Column(nullable = false, length = 50)
    private String senha;
    @Column(length = 50)
    private String confirmacaoSenha;
    @Column(length = 10)
    private Integer forcaSenha;
    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Usuario2> usuarios2 = new ArrayList<>();
}
