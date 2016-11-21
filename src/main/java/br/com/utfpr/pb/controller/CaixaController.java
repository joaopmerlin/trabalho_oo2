package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.CaixaDao;
import br.com.utfpr.pb.dao.impl.CaixaDaoImpl;
import br.com.utfpr.pb.model.Caixa;

/**
 * Created by João on 10/11/2016.
 */
public class CaixaController extends AbstractController<Caixa, Long> {

    private static CaixaController ourInstance = new CaixaController();

    private CaixaDao dao;

    private CaixaController() {
        this.dao = new CaixaDaoImpl();
    }

    @Override
    protected AbstractDao<Caixa, Long> getDao() {
        return dao;
    }

    public static CaixaController getInstance() {
        return ourInstance;
    }
}
