package br.com.tqi.analisecredito.repository;

import br.com.tqi.analisecredito.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByEmail(String email);

    List<Cliente> findByNomeIgnoreCaseContaining(String nome);
}
