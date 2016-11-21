package br.com.utfpr.pb;

import br.com.utfpr.pb.config.DatabaseConnection;
import br.com.utfpr.pb.dao.UsuarioDao;
import br.com.utfpr.pb.dao.impl.UsuarioDaoImpl;
import br.com.utfpr.pb.model.Usuario;
import br.com.utfpr.pb.util.EncryptPasswordUtil;
import br.com.utfpr.pb.view.PrincipalView;

/**
 * Created by João on 10/11/2016.
 */
public class Application {

    public static void main(String[] args) {
        // Cria Entity Manager
        DatabaseConnection.getInstance().getEntityManager();

        // Cria usuario padrao
        UsuarioDao usuarioDao = new UsuarioDaoImpl();

        if (usuarioDao.findByLogin("admin") == null) {
            Usuario usuario = new Usuario();
            usuario.setNome("Administrador");
            usuario.setLogin("admin");
            usuario.setSenha(EncryptPasswordUtil.getInstance().encrypt("admin"));
            usuario.setAtivo(true);
            usuarioDao.save(usuario);
        }

        // Abre frame principal
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalView().setVisible(true);
            }
        });
        
    }

}
