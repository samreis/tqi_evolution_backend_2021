package br.com.tqi.analisecredito.service.impl;

import br.com.tqi.analisecredito.entity.Cliente;
import br.com.tqi.analisecredito.repository.ClienteRepository;
import br.com.tqi.analisecredito.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new UsernameNotFoundException("E-mail ou senha inválidos.");
        }
        return new org.springframework.security.core.userdetails.User(
                cliente.getEmail(), cliente.getSenha(), mapRolesToAuthorities(funcoes()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Funcao> funcoes) {
        return funcoes.stream().map(funcao -> new SimpleGrantedAuthority(funcao.getNome())).collect(Collectors.toList());
    }

    private Collection<Funcao> funcoes() {
        Funcao funcao = new Funcao(1L, "CLIENTE");
        Collection<Funcao> funcoes = new ArrayList<>();
        funcoes.add(funcao);
        return funcoes;
    }

    @Getter
    @AllArgsConstructor
    private static class Funcao {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;
    }
}
