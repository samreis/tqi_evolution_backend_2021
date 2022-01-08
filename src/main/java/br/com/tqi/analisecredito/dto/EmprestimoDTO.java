package br.com.tqi.analisecredito.dto;

import br.com.tqi.analisecredito.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class EmprestimoDTO {

    @NotNull
    private Float valor;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Brazil/East")
    private Date dataPrimeiraParcela;
    @Max(value = 60, message = "deve ter no máximo 60 parcelas")
    @Min(value = 1, message = "deve ter no mínimo 1 parcela")
    @NotNull
    private Integer quantidadeParcelas;
    @NotNull
    private Cliente cliente;
}
