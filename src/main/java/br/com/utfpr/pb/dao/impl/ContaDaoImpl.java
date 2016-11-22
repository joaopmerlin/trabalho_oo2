package br.com.utfpr.pb.dao.impl;

import br.com.utfpr.pb.config.DatabaseConnection;
import br.com.utfpr.pb.dao.CaixaDao;
import br.com.utfpr.pb.dao.ContaDao;
import br.com.utfpr.pb.enumeration.CreditoDebito;
import br.com.utfpr.pb.enumeration.TipoConta;
import br.com.utfpr.pb.model.Caixa;
import br.com.utfpr.pb.model.Conta;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by João on 10/11/2016.
 */
public class ContaDaoImpl extends AbstractDaoImpl<Conta, Long> implements ContaDao {

    private EntityManager em = DatabaseConnection.getInstance().getEntityManager();

    private CaixaDao caixaDao = new CaixaDaoImpl();

    @Override
    protected Conta postSave(Conta conta) {

        if (conta.getBaixas() != null && !conta.getBaixas().isEmpty()) {
            conta.getBaixas().forEach(baixa -> {
                Caixa caixa = caixaDao.findByContaBaixa(baixa);

                if (caixa == null) {
                    caixa = new Caixa();
                    caixa.setBaixa(baixa);
                    caixa.setData(new Date());
                    caixa.setValor(baixa.getValor());
                    if (baixa.getConta().getTipoConta().equals(TipoConta.RECEBER)){
                        caixa.setDescricao("Recebimento título n. " + baixa.getConta().getId());
                        caixa.setCreditoDebito(CreditoDebito.C);
                    } else {
                        caixa.setDescricao("Pagamento título n. " + baixa.getConta().getId());
                        caixa.setCreditoDebito(CreditoDebito.D);
                    }
                } else {
                    caixa.setValor(baixa.getValor());
                }

                caixaDao.save(caixa);
            });
        }

        return super.postSave(conta);
    }

    @Override
    public List<Conta> findByTipoConta(TipoConta tipoConta) {
        return em.createQuery("select c from Conta c where c.tipoConta = :tipo")
                .setParameter("tipo", tipoConta)
                .getResultList();
    }
}
