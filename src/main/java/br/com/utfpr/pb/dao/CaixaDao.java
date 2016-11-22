package br.com.utfpr.pb.dao;

import br.com.utfpr.pb.model.Caixa;
import br.com.utfpr.pb.model.Conta;
import br.com.utfpr.pb.model.ContaBaixa;

/**
 * Created by João on 10/11/2016.
 */
public interface CaixaDao extends AbstractDao<Caixa, Long> {

    Caixa findByContaBaixa(ContaBaixa contaBaixa);

}
