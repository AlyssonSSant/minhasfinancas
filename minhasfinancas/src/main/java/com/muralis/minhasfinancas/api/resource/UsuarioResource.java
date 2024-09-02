package com.muralis.minhasfinancas.api.resource;

import com.muralis.minhasfinancas.api.DTO.UsuarioDTO;
import com.muralis.minhasfinancas.model.entity.Usuario;
import com.muralis.minhasfinancas.service.UsuarioService;
import com.muralis.minhasfinancas.service.exception.ErroAutenticacao;
import com.muralis.minhasfinancas.service.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    @GetMapping("/hello")
    public String helloWord(){
        return "Hello Word";
    }
    private UsuarioService service;

    public UsuarioResource (UsuarioService service){
        this.service = service;
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar (@RequestBody UsuarioDTO usuarioDTO){
        try {
         Usuario usuarioAutenticado =  service.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
        return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .build();
        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
