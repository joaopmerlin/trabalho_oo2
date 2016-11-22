package br.com.utfpr.pb.model;

import br.com.utfpr.pb.enumeration.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty
    private String descricao;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Campo 'Pessoa' é obrigatório")
    private Pessoa pessoa;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private TipoConta tipoConta;

    @Column(nullable = false)
    @NotNull
    private Double valor;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date emissao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "conta")
    private List<ContaBaixa> baixas;

    public void addBaixa(ContaBaixa contaBaixa) {
        if (baixas == null) {
            baixas = new ArrayList<>();
        }
        this.baixas.add(contaBaixa);
    }

    public Boolean isPaga() {
        if (baixas == null || baixas.isEmpty()) {
            return false;
        }
        return baixas.stream().mapToDouble(ContaBaixa::getValor).sum() >= valor;
    }

    public Double getSaldo() {
        if (baixas == null || baixas.isEmpty()) {
            return valor;
        }
        return valor - baixas.stream().mapToDouble(ContaBaixa::getValor).sum();
    }
}
