package com.muralis.minhasfinancas.model.entity;

import javax.persistence.*;

import com.muralis.minhasfinancas.enumerator.StatusLancamento;
import com.muralis.minhasfinancas.enumerator.TipoLancamento;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Lancamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name ="mes")
    private Integer mes;

    @Column(name ="ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "Id_usuario")
    private Usuario usuario;

    @Column(name ="valor")
    private BigDecimal valor;

    @Column(name= "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento statusLancamento;

}
