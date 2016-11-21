package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "Campo 'Nome' é obrigatório")
    protected String nome;

    @Column(length = 50)
    private String telefone;

    @Column(length = 250)
    private String cidade;

    @Column(length = 500)
    private String endereco;

    @Column(nullable = false)
    private Boolean ativo;

    public String getAtivoTxt(){
        if (ativo != null){
            return ativo ? "Sim" : "Não";
        }
        return null;
    }
    
    @Override
    public String toString() {
        return nome;
    }
    
}
