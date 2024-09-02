package com.muralis.minhasfinancas.model.repository;

import com.muralis.minhasfinancas.model.entity.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamentos, Long> {

}
