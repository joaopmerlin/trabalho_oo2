package br.com.utfpr.pb.model;

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

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContaBaixa baixa;

    @Column(nullable = false)
    @NotEmpty
    private Double valor;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date data;
}
