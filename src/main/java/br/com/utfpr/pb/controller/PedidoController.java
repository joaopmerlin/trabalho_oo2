package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.PedidoDao;
import br.com.utfpr.pb.dao.impl.PedidoDaoImpl;
import br.com.utfpr.pb.model.Pedido;

/**
 * Created by João on 10/11/2016.
 */
public class PedidoController extends AbstractController<Pedido, Long> {

    private static PedidoController ourInstance = new PedidoController();
    private PedidoDao dao;

    private PedidoController() {
        this.dao = new PedidoDaoImpl();
    }

    @Override
    protected AbstractDao<Pedido, Long> getDao() {
        return dao;
    }

    public static PedidoController getInstance() {
        return ourInstance;
    }
}
