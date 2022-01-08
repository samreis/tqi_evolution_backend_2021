package br.com.tqi.analisecredito.controller;

import br.com.tqi.analisecredito.dto.DetalheEmprestimoClienteDTO;
import br.com.tqi.analisecredito.dto.EmprestimoClienteDTO;
import br.com.tqi.analisecredito.dto.EmprestimoDTO;
import br.com.tqi.analisecredito.entity.Cliente;
import br.com.tqi.analisecredito.entity.Emprestimo;
import br.com.tqi.analisecredito.service.ClienteService;
import br.com.tqi.analisecredito.service.EmprestimoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("emprestimo")
public class EmprestimoController {

    private static final Map<String, Object> retorno = new HashMap<>();

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> solicitar(@Valid @RequestBody final EmprestimoDTO emprestimoDTO, BindingResult result) throws Exception {
        var response = new HashMap<>();

        if (result.hasErrors()) {
            var errors = result.getFieldErrors().stream()
                    .map(err -> "O campo '".concat(err.getField()).concat("' ").concat(Objects.requireNonNull(err.getDefaultMessage())))
                    .collect(Collectors.toList());
            response.put("Erros", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Cliente cliente = clienteService.buscarPorId(emprestimoDTO.getCliente().getId());
        emprestimoDTO.setCliente(cliente);

        emprestimoService.solicitar(emprestimoDTO);
        log.info("Emprestimo solicitado com sucesso!");
        return ResponseEntity.created(URI.create("emprestimo")).body(emprestimoDTO);
    }


    @GetMapping(value = "/auth/listagem-emprestimos")
    public ResponseEntity<Object> listarEmprestimosClienteAutenticado(@AuthenticationPrincipal UserDetails usuarioAtualLogado) {
        try {
            Cliente cliente = clienteService.buscarPorEmail(usuarioAtualLogado.getUsername());
            List<Emprestimo> emprestimos = emprestimoService.listar(cliente.getId());
            List<EmprestimoClienteDTO> emprestimosClienteDTO = new ArrayList<>();

            emprestimos.forEach(emprestimo -> {
                EmprestimoClienteDTO emprestimoClienteDTO = new EmprestimoClienteDTO();
                emprestimoClienteDTO.setId(emprestimo.getId());
                emprestimoClienteDTO.setValor(emprestimo.getValor());
                emprestimoClienteDTO.setQuantidadeParcelas(emprestimo.getQuantidadeParcelas());
                emprestimosClienteDTO.add(emprestimoClienteDTO);
            });

            return ResponseEntity.ok().body(emprestimosClienteDTO);
        } catch (NullPointerException npe) {
            log.error(npe.getMessage());
            String erro = "Nao existe o cliente cadastrado!";
            retorno.put("Erro", erro);
            return ResponseEntity.internalServerError().body(retorno);
        }
    }

    @GetMapping(value = "/auth/detalhe-emprestimo/{id}")
    public ResponseEntity<Object> detalharEmprestimoClienteAutenticado(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails usuarioAtualLogado) {
        try {
            Cliente cliente = clienteService.buscarPorEmail(usuarioAtualLogado.getUsername());
            Emprestimo emprestimo = emprestimoService.buscaPorId(id);

            DetalheEmprestimoClienteDTO detalheEmprestimoClienteDTO = new DetalheEmprestimoClienteDTO();
            detalheEmprestimoClienteDTO.setId(emprestimo.getId());
            detalheEmprestimoClienteDTO.setValor(emprestimo.getValor());
            detalheEmprestimoClienteDTO.setQuantidadeParcelas(emprestimo.getQuantidadeParcelas());
            detalheEmprestimoClienteDTO.setDataPrimeiraParcela(emprestimo.getDataPrimeiraParcela());
            detalheEmprestimoClienteDTO.setEmailCliente(cliente.getEmail());
            detalheEmprestimoClienteDTO.setRendaCliente(cliente.getRenda());

            return ResponseEntity.ok().body(detalheEmprestimoClienteDTO);
        } catch (NullPointerException npe) {
            log.error(npe.getMessage());
            String erro = "Nao existe o emprestimo solicitado!";
            retorno.put("Erro", erro);
            return ResponseEntity.internalServerError().body(retorno);
        }
    }
}
