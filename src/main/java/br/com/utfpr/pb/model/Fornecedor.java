package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by João on 16/09/2016.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor extends Pessoa {

    @Column(length = 20)
    @NotBlank(message = "Campo 'CNPJ' é obrigatório")
    private String cnpj;

    @Override
    public String toString() {
        return nome;
    }
    
}
