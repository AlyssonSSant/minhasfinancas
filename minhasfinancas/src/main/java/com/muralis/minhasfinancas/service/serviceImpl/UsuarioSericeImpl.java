package com.muralis.minhasfinancas.service.serviceImpl;

import com.muralis.minhasfinancas.model.entity.Usuario;
import com.muralis.minhasfinancas.model.repository.UsuarioRepository;
import com.muralis.minhasfinancas.service.UsuarioService;
import com.muralis.minhasfinancas.service.exception.ErroAutenticacao;
import com.muralis.minhasfinancas.service.exception.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioSericeImpl implements UsuarioService {

    private  UsuarioRepository repository;

    @Autowired
    public UsuarioSericeImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario =repository.findByEmail(email);

        if (!usuario.isPresent()){
            throw new ErroAutenticacao("Usuario não encontrado para o email informado!");

        }
        if (!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("senha invalida!");
        }
        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {

        boolean existe =repository.existsByEmail(email);
        if (existe){
            throw  new RegraNegocioException("ja existe um usuario com esse email");
        }

    }
}
