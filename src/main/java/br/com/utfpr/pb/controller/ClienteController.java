package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.ClienteDao;
import br.com.utfpr.pb.dao.impl.ClienteDaoImpl;
import br.com.utfpr.pb.model.Cliente;

/**
 * Created by João on 10/11/2016.
 */
public class ClienteController extends AbstractController<Cliente, Long> {

    private static ClienteController ourInstance = new ClienteController();

    private ClienteDao dao;

    private ClienteController() {
        this.dao = new ClienteDaoImpl();
    }

    @Override
    protected AbstractDao<Cliente, Long> getDao() {
        return dao;
    }

    public static ClienteController getInstance() {
        return ourInstance;
    }
}
