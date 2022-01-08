package br.com.tqi.analisecredito.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login() {
        log.info("Cliente logado com sucesso!");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/custom-logout")
    public ResponseEntity<Object> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        log.info("Cliente deslogado com sucesso!");
        return ResponseEntity.noContent().build();
    }
}
