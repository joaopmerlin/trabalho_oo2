package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.FornecedorDao;
import br.com.utfpr.pb.dao.impl.FornecedorDaoImpl;
import br.com.utfpr.pb.model.Fornecedor;

/**
 * Created by João on 10/11/2016.
 */
public class FornecedorController extends AbstractController<Fornecedor, Long> {

    private static FornecedorController ourInstance = new FornecedorController();

    private FornecedorDao dao;

    private FornecedorController() {
        this.dao = new FornecedorDaoImpl();
    }

    @Override
    protected AbstractDao<Fornecedor, Long> getDao() {
        return dao;
    }

    public static FornecedorController getInstance() {
        return ourInstance;
    }
}
