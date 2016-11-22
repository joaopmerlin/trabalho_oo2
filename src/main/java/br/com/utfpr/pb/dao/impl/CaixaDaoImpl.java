package br.com.utfpr.pb.dao.impl;

import br.com.utfpr.pb.config.DatabaseConnection;
import br.com.utfpr.pb.dao.CaixaDao;
import br.com.utfpr.pb.model.Caixa;
import br.com.utfpr.pb.model.ContaBaixa;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by João on 10/11/2016.
 */
public class CaixaDaoImpl extends AbstractDaoImpl<Caixa, Long> implements CaixaDao {

    private EntityManager em = DatabaseConnection.getInstance().getEntityManager();

    @Override
    public Caixa findByContaBaixa(ContaBaixa contaBaixa) {
        List<Caixa> result = em.createQuery("select c from Caixa c where c.baixa.id = :baixa")
                .setParameter("baixa", contaBaixa.getId())
                .getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
