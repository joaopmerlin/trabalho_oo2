package br.com.utfpr.pb.dao;

import br.com.utfpr.pb.model.Usuario;

/**
 * Created by João on 10/11/2016.
 */
public interface UsuarioDao extends AbstractDao<Usuario, Long> {

    Usuario findByLoginAndSenha(String login, String senha);

    Usuario findByLogin(String login);

}
