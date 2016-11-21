package br.com.utfpr.pb.controller;

import br.com.utfpr.pb.dao.AbstractDao;
import br.com.utfpr.pb.dao.UsuarioDao;
import br.com.utfpr.pb.dao.impl.UsuarioDaoImpl;
import br.com.utfpr.pb.model.Usuario;

/**
 * Created by João on 10/11/2016.
 */
public class UsuarioController extends AbstractController<Usuario, Long> {

    private static UsuarioController ourInstance = new UsuarioController();
    private UsuarioDao dao;
    private Usuario usuarioLogado;

    private UsuarioController() {
        this.dao = new UsuarioDaoImpl();
    }

    public Boolean login(String login, String senha) {
        Usuario usuario = dao.findByLoginAndSenha(login, senha);
        if (usuario != null) {
            setUsuarioLogado(usuario);
            return true;
        }
        return false;
    }

    @Override
    protected AbstractDao<Usuario, Long> getDao() {
        return dao;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public static UsuarioController getInstance() {
        return ourInstance;
    }
}
