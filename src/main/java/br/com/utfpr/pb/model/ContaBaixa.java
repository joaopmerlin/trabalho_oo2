package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaBaixa implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Conta conta;

    @Column(nullable = false)
    @NotNull
    private Double valor;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date data;
}
