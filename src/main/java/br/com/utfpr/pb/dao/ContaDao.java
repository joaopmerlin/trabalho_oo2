package br.com.utfpr.pb.dao;

import br.com.utfpr.pb.enumeration.TipoConta;
import br.com.utfpr.pb.model.Conta;

import java.util.List;

/**
 * Created by João on 10/11/2016.
 */
public interface ContaDao extends AbstractDao<Conta, Long> {

    List<Conta> findByTipoConta(TipoConta tipoConta);

}
