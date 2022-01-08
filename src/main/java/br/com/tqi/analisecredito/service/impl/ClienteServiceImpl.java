package br.com.tqi.analisecredito.service.impl;

import br.com.tqi.analisecredito.dto.ClienteDTO;
import br.com.tqi.analisecredito.entity.Cliente;
import br.com.tqi.analisecredito.repository.ClienteRepository;
import br.com.tqi.analisecredito.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void cadastrar(ClienteDTO clienteDTO, Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setRg(clienteDTO.getRg());
        cliente.setEnderecoCompleto(clienteDTO.getEnderecoCompleto());
        cliente.setRenda(clienteDTO.getRenda());
        cliente.setSenha(passwordEncoder.encode(clienteDTO.getSenha()));
        clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElse(null);
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeIgnoreCaseContaining(nome);
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }

    public void remover(Long id){
        clienteRepository.deleteById(id);
    }
}
