package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.PessoaDao;
import br.com.utfpr.pb.dao.impl.PessoaDaoImpl;
import br.com.utfpr.pb.model.Pessoa;

/**
 * Created by João on 10/11/2016.
 */
public class PessoaController extends AbstractController<Pessoa, Long> {

    private static PessoaController ourInstance = new PessoaController();

    private PessoaDao dao;

    private PessoaController() {
        this.dao = new PessoaDaoImpl();
    }

    @Override
    protected AbstractDao<Pessoa, Long> getDao() {
        return dao;
    }

    public static PessoaController getInstance() {
        return ourInstance;
    }
}
