package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.ContaDao;
import br.com.utfpr.pb.dao.impl.ContaDaoImpl;
import br.com.utfpr.pb.enumeration.TipoConta;
import br.com.utfpr.pb.model.Conta;

import java.util.List;

/**
 * Created by João on 10/11/2016.
 */
public class ContaController extends AbstractController<Conta, Long> {

    private static ContaController ourInstance = new ContaController();

    private ContaDao dao;

    private ContaController() {
        this.dao = new ContaDaoImpl();
    }

    public List<Conta> findByTipoConta(TipoConta tipoConta) {
        return dao.findByTipoConta(tipoConta);
    }

    @Override
    protected AbstractDao<Conta, Long> getDao() {
        return dao;
    }

    public static ContaController getInstance() {
        return ourInstance;
    }
}
