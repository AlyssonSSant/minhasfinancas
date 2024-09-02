package com.muralis.minhasfinancas.service;

import com.muralis.minhasfinancas.model.entity.Usuario;
import com.muralis.minhasfinancas.model.repository.UsuarioRepository;
import com.muralis.minhasfinancas.model.repository.UsuarioRepositoryTest;
import com.muralis.minhasfinancas.service.exception.ErroAutenticacao;
import com.muralis.minhasfinancas.service.exception.RegraNegocioException;
import com.muralis.minhasfinancas.service.serviceImpl.UsuarioSericeImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")

public class UsuarioServiceTest {

    @SpyBean//spy sempre vai chamar o metodo original
    UsuarioSericeImpl service;

    @MockBean
    UsuarioRepository repository;


    @Test(expected = Test.None.class)
    public void deveSalvarUsuario() {
        //cenario
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder()
                .nome("usuario").email("usuario@email.com")
                .senha("senha").build();

        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
        //acao
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
        //verificação
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("usuario");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("usuario@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveSalvarUsuarioComEmailJaCadastraco() {
        //cenario
        String email = "usuario@email.com";
        Usuario usuario = Usuario.builder().email(email).build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
        //acao
        service.salvarUsuario(usuario);
        //verificação
        Mockito.verify(repository, Mockito.never()).save(usuario);
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso() {
        // cenario
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //acao
        Usuario resultado = service.autenticar(email, senha);

        //veerificacao
        Assertions.assertThat(resultado).isNotNull();
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
        //cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        //acao
        Throwable excption = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));

        //Verificacao
        Assertions.assertThat(excption).
                isInstanceOf(ErroAutenticacao.class).
                hasMessage("Usuario não encontrado para o email informado!");
    }


    @Test
    public void DeveLancarErroQuandoSenhaNaoBater() {
        //cenario
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //acao
        Throwable exception = Assertions.catchThrowable(() -> service
                .autenticar("email@email.com", "123"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("senha invalida!");
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {

        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);


        repository.deleteAll();
        //acao
        service.validarEmail("email@email.com");

    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {

        //cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
        //acao
        service.validarEmail("email@email.com");
        //

    }


}
