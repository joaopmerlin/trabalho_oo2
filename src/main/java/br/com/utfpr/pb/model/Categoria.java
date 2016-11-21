package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
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
public class Categoria implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "Campo 'Descrição' é obrigatório")
    private String descricao;

    @Override
    public String toString() {
        return descricao;
    }
}
