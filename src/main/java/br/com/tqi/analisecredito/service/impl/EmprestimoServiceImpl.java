package br.com.tqi.analisecredito.service.impl;

import br.com.tqi.analisecredito.dto.EmprestimoDTO;
import br.com.tqi.analisecredito.entity.Emprestimo;
import br.com.tqi.analisecredito.exception.BusinessException;
import br.com.tqi.analisecredito.repository.EmprestimoRepository;
import br.com.tqi.analisecredito.service.EmprestimoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmprestimoServiceImpl implements EmprestimoService {

    private static final String REGRA_DATA_MAXIMA_3_MESES = "A data da primeira parcela nao pode ser maior que 3 meses apos o dia atual";

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Override
    public void solicitar(EmprestimoDTO emprestimoDTO) throws BusinessException {
        if (!dataMaximaPrimeiraParcela(emprestimoDTO)) {
            log.info(REGRA_DATA_MAXIMA_3_MESES);
            throw new BusinessException(REGRA_DATA_MAXIMA_3_MESES);
        }
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setValor(emprestimoDTO.getValor());
        emprestimo.setDataPrimeiraParcela(emprestimoDTO.getDataPrimeiraParcela());
        emprestimo.setQuantidadeParcelas(emprestimoDTO.getQuantidadeParcelas());
        emprestimo.setCliente(emprestimoDTO.getCliente());
        emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> listar(Long clienteId) {
        return emprestimoRepository.findByClienteId(clienteId);
    }

    public Emprestimo buscaPorId(Long id) {
        Optional<Emprestimo> optionalEmprestimo = emprestimoRepository.findById(id);
        return optionalEmprestimo.orElse(null);
    }

    private boolean dataMaximaPrimeiraParcela(EmprestimoDTO emprestimoDTO) {
        Date dataAtual = Date.from(Instant.now());
        long diasEntreDatas = Duration.between(dataAtual.toInstant(), emprestimoDTO.getDataPrimeiraParcela().toInstant()).toDays();
        return diasEntreDatas <= 90;
    }
}
