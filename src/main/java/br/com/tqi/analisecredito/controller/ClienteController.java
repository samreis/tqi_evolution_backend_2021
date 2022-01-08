package br.com.tqi.analisecredito.controller;

import br.com.tqi.analisecredito.dto.ClienteDTO;
import br.com.tqi.analisecredito.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = "cliente")
public class ClienteController {

    private static final Map<String, Object> response = new HashMap<>();

    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> cadastrar(@Valid @RequestBody final ClienteDTO clienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            var errors = result.getFieldErrors().stream()
                    .map(err -> "O campo '".concat(err.getField()).concat("' ").concat(Objects.requireNonNull(err.getDefaultMessage())))
                    .collect(Collectors.toList());
            response.put("Erros", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        clienteService.cadastrar(clienteDTO, null);
        log.info("Cliente cadastro com sucesso!");
        clienteDTO.setSenha(null);
        return ResponseEntity.created(URI.create("cliente")).body(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<Object> listar(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok().body((nome == null || nome.isBlank()) ? clienteService.buscarTodosClientes() : clienteService.buscarPorNome(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Long id, @Valid @RequestBody ClienteDTO clienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> "O campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());
            response.put("Errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        clienteService.cadastrar(clienteDTO, id);
        log.info("Cliente atualizado com sucesso!");
        clienteDTO.setSenha(null);
        return ResponseEntity.created(URI.create("cliente")).body(clienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> remover(@PathVariable("id") Long id) {
        clienteService.remover(id);
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        log.info("Cliente deletado e deslogado com sucesso!");
        return ResponseEntity.ok().build();
    }
}
