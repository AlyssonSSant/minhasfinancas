package com.muralis.minhasfinancas.model.repository;

import java.util.AbstractSet;
import java.util.Optional;
import com.muralis.minhasfinancas.model.entity.Usuario;
import com.muralis.minhasfinancas.model.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest //cria uma instancia no BD em memoria e finalizando deleta a BD....da rollback(desfaz o que foi feito na transação)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //subescreve configurações feitas no ambiente de teste

public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void  deveVerificarAExistenciaDeUmEmail(){
        //cenário
        Usuario usuario = criarUsuario();
        testEntityManager.persist(usuario);

       // usuarioRepository.save(usuario);

        //ação/ execução
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();

    }
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){
        //cenario
        //usuarioRepository.deleteAll();

        //acao
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");

        //verficação
        Assertions.assertThat(result).isFalse();

    }


    public void devePersistirUmUsuarioNaBaseDeDados(){
        //cenario
        Usuario usuario = criarUsuario();
        //acao
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        //verificaçao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull(); //verifica se o usuario tem um id agora

    }
    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        //cenario
            Usuario usuario= criarUsuario();
            testEntityManager.persist(usuario);
         //acao
        Optional <Usuario> result =  usuarioRepository.findByEmail("usuario@email.com");

        //
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetonerVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBase(){
        //cenario

        //acao
        Optional <Usuario> result =  usuarioRepository.findByEmail("usuario@email.com");

        //
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    public static Usuario criarUsuario(){
        return   Usuario.builder()
                .nome("usuario").email("usuario@email.com")
                .senha("senha").build();
    }


}
