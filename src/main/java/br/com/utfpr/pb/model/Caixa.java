package br.com.utfpr.pb.model;

import br.com.utfpr.pb.enumeration.CreditoDebito;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Joao on 18/11/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caixa implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Campo 'Descrição' é obrigatório")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Campo 'Crédito / Débito' é obrigatório")
    private CreditoDebito creditoDebito;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContaBaixa baixa;

    @Column(nullable = false)
    @NotNull(message = "Campo 'Valor' é obrigatório")
    private Double valor;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date data;
}
