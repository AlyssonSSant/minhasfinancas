package com.muralis.minhasfinancas.model.repository;

import com.muralis.minhasfinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    boolean existsByEmail(String email);

    Optional <Usuario> findByEmail(String email);


}
