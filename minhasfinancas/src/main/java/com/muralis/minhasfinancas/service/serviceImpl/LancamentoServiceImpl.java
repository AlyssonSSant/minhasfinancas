package com.muralis.minhasfinancas.service.serviceImpl;

import com.muralis.minhasfinancas.enumerator.StatusLancamento;
import com.muralis.minhasfinancas.model.entity.Lancamentos;
import com.muralis.minhasfinancas.model.repository.LancamentoRepository;
import com.muralis.minhasfinancas.service.LancamentoService;
import com.muralis.minhasfinancas.service.exception.RegraNegocioException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {


    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public Lancamentos salvar(Lancamentos lancamentos) {
        validar(lancamentos);
        lancamentos.setStatusLancamento(StatusLancamento.PENDENTE);
        return repository.save(lancamentos);
    }

    @Override
    @Transactional
    public Lancamentos atualizar(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        validar(lancamentos);
        return repository.save(lancamentos);
    }

    @Override
    @Transactional
    public void deletar(Lancamentos lancamentos) {
        Objects.requireNonNull(lancamentos.getId());
        repository.delete(lancamentos);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamentos> buscar(Lancamentos lancamentosFiltro) {
        Example example = Example.of( lancamentosFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example);
    }

    @Override
    public void atualizarLancamentos(Lancamentos lancamentos, StatusLancamento status) {
        lancamentos.setStatusLancamento(status);
        atualizar(lancamentos);

    }

    @Override
    public void validar(Lancamentos lancamentos) {
        if (lancamentos.getDescricao()== null || lancamentos.getDescricao()
                .trim().equals("")){
            throw  new RegraNegocioException("informe uma Descrição válida");
        }
        if (lancamentos.getMes()== null || lancamentos.getMes() <1 ||
                lancamentos.getMes()>12 ){
            throw  new RegraNegocioException("informe uma Mês válido");
        }

        if (lancamentos.getAno() == null || lancamentos.getAno().toString().
                length() !=4){
            throw new RegraNegocioException("Informe um Ano Válido");
        }

        if (lancamentos.getUsuario() == null || lancamentos.getUsuario()
                .getId() == null){
            throw new RegraNegocioException("Informe um Usuario.");
        }

        if (lancamentos.getValor() == null || lancamentos.getValor()
                .compareTo(BigDecimal.ZERO)<1){
            throw new RegraNegocioException("infrome um Valor válido");
        }

        if (lancamentos.getTipoLancamento() == null){
            throw new RegraNegocioException("informe um Tipo de Lançamento");
        }
    }
}
