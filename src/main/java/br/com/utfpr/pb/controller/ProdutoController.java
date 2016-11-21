package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.ProdutoDao;
import br.com.utfpr.pb.dao.impl.ProdutoDaoImpl;
import br.com.utfpr.pb.model.Produto;

/**
 * Created by João on 10/11/2016.
 */
public class ProdutoController extends AbstractController<Produto, Long> {

    private static ProdutoController ourInstance = new ProdutoController();

    private ProdutoDao dao;

    private ProdutoController() {
        this.dao = new ProdutoDaoImpl();
    }

    @Override
    protected AbstractDao<Produto, Long> getDao() {
        return dao;
    }

    public static ProdutoController getInstance() {
        return ourInstance;
    }
}
