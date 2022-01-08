package br.com.tqi.analisecredito.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DetalheEmprestimoClienteDTO {

    private Long id;
    private Float valor;
    private Integer quantidadeParcelas;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Brazil/East")
    private Date dataPrimeiraParcela;
    private String emailCliente;
    private Float rendaCliente;
}
