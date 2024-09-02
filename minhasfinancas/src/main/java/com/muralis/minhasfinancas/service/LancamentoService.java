package com.muralis.minhasfinancas.service;

import com.muralis.minhasfinancas.enumerator.StatusLancamento;
import com.muralis.minhasfinancas.model.entity.Lancamentos;

import java.util.List;

public interface LancamentoService {

    Lancamentos salvar (Lancamentos lancamentos);

    Lancamentos atualizar(Lancamentos lancamentos);

    void deletar(Lancamentos lancamentos);

    List<Lancamentos> buscar(Lancamentos lancamentosFiltro);

    void atualizarLancamentos (Lancamentos lancamentos, StatusLancamento status);

    void validar (Lancamentos lancamentos);

}
