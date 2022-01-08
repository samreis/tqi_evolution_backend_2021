package br.com.tqi.analisecredito.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClienteDTO {

    @NotBlank
    private String nome;
    @Email
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    private String rg;
    @NotBlank
    private String enderecoCompleto;
    @NotNull
    private Float renda;
    @NotBlank
    private String senha;
}
