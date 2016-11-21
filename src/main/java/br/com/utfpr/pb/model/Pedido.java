package br.com.utfpr.pb.model;

import br.com.utfpr.pb.enumeration.TipoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Campo 'Pessoa' é obrigatório")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private TipoPedido tipoPedido;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "pedido")
    @NotEmpty(message = "Nenhum produto foi adicionado")
    private List<PedidoItem> produtos;

    public void addProduto(PedidoItem peditoItem) {
        if (produtos == null) {
            produtos = new ArrayList<>();
        }
        this.produtos.add(peditoItem);
    }
    
    public Double getTotal(){
        if (produtos != null && !produtos.isEmpty()){
            return produtos.stream().mapToDouble(e -> e.getValorUnitario() * e.getQuantidade()).sum();
        }
        return 0.0;
    }

}
