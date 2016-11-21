package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @Column
    private Integer quantidade;

    @Column
    private Double valorUnitario;

    @Transient
    private Double valorTotal;

    public Double getValorTotal() {
        if (valorUnitario != null && quantidade != null)
            return valorUnitario * quantidade;
        return null;
    }

}
