package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "Campo 'Descrição' é obrigatório")
    private String descricao;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Campo 'Categoria' é obrigatório")
    private Categoria categoria;

    @Column
    private Double custo;

    @Column
    private Double valor;

    @Column(nullable = false)
    @NotNull(message = "Campo 'Estoque' é obrigatório")
    private Integer estoque;

    @Column(nullable = false)
    private Boolean ativo;

    public String getAtivoTxt(){
        if (ativo != null){
            return ativo ? "Sim" : "Não";
        }
        return null;
    }

}
