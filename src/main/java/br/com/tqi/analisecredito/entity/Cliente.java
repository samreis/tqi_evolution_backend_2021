package br.com.tqi.analisecredito.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 14, unique = true)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String rg;
    @Column(nullable = false)
    private String enderecoCompleto;
    @Column(nullable = false)
    private Float renda;
    @Column(nullable = false)
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    @ToString.Exclude
    private List<Emprestimo> emprestimos;
}
