package br.com.tqi.analisecredito.service;

import br.com.tqi.analisecredito.dto.ClienteDTO;
import br.com.tqi.analisecredito.entity.Cliente;

import java.util.List;

public interface ClienteService {

    void cadastrar(ClienteDTO clienteDTO, Long id);

    Cliente buscarPorId(Long id);

    Cliente buscarPorEmail(String email);

    List<Cliente> buscarPorNome(String nome);

    List<Cliente> buscarTodosClientes();

    void remover(Long id);
}
