package br.com.tqi.analisecredito.service;

import br.com.tqi.analisecredito.dto.EmprestimoDTO;
import br.com.tqi.analisecredito.entity.Emprestimo;
import br.com.tqi.analisecredito.exception.BusinessException;

import java.util.List;

public interface EmprestimoService {

    void solicitar(EmprestimoDTO emprestimoDTO) throws BusinessException;

    List<Emprestimo> listar(Long clienteId);

    Emprestimo buscaPorId(Long id);
}
