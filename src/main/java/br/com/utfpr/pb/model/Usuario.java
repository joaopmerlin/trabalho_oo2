package br.com.utfpr.pb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "Campo 'Nome' é obrigatório")
    private String nome;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Campo 'Login' é obrigatório")
    private String login;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Campo 'Senha' é obrigatório")
    private String senha;
    
    @Column(nullable = false)
    private Boolean ativo;

}
