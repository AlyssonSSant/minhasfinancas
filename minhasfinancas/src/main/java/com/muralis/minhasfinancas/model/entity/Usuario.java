package com.muralis.minhasfinancas.model.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="usuario",schema = "financas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name ="nome")
    private String nome;

    @Column(name ="email")
    private String email;

    @Column(name ="senha")
    private String senha;

//    @Column(name ="data_cadastro")
//    private LocalDate dataCadastro;


}
