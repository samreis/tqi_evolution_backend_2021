package br.com.tqi.analisecredito.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoClienteDTO {

    private Long id;
    private Float valor;
    private Integer quantidadeParcelas;
}
