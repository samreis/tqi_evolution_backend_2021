package br.com.tqi.analisecredito.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String, Object> retorno = new HashMap<>();

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> erroSQL() {
        String erro = "Erro ao cadastrar cliente: e-mail, CPF ou RG informados existem no banco de dados, por gentileza informar outros dados!";
        retorno.put("Erro", erro);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retorno);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> erroRegrasDeNegocio() {
        String erro = "A data da primeira parcela nao pode ser maior que 3 meses apos o dia atual";
        retorno.put("Erro", erro);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(retorno);
    }
}