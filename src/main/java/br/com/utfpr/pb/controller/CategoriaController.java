package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.CategoriaDao;
import br.com.utfpr.pb.dao.impl.CategoriaDaoImpl;
import br.com.utfpr.pb.model.Categoria;

/**
 * Created by João on 10/11/2016.
 */
public class CategoriaController extends AbstractController<Categoria, Long> {

    private static CategoriaController ourInstance = new CategoriaController();

    private CategoriaDao dao;

    private CategoriaController() {
        this.dao = new CategoriaDaoImpl();
    }

    @Override
    protected AbstractDao<Categoria, Long> getDao() {
        return dao;
    }

    public static CategoriaController getInstance() {
        return ourInstance;
    }
}
