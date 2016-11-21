package br.com.utfpr.pb.dao.impl;

import br.com.utfpr.pb.dao.ContaDao;
import br.com.utfpr.pb.dao.PedidoDao;
import br.com.utfpr.pb.dao.ProdutoDao;
import br.com.utfpr.pb.enumeration.TipoConta;
import br.com.utfpr.pb.enumeration.TipoPedido;
import br.com.utfpr.pb.model.Conta;
import br.com.utfpr.pb.model.Pedido;
import br.com.utfpr.pb.model.Produto;

import java.util.Date;

/**
 * Created by João on 10/11/2016.
 */
public class PedidoDaoImpl extends AbstractDaoImpl<Pedido, Long> implements PedidoDao {

    private ProdutoDao produtoDao = new ProdutoDaoImpl();
    private ContaDao contaDao = new ContaDaoImpl();

    @Override
    protected Pedido postSave(Pedido pedido) {
        atualizaProduto(pedido);
        criaConta(pedido);
        return super.preSave(pedido);
    }

    private void criaConta(Pedido pedido) {
        Conta conta = new Conta();
        conta.setPessoa(pedido.getPessoa());
        conta.setPedido(pedido);
        conta.setEmissao(new Date());
        conta.setValor(pedido.getTotal());
        conta.setVencimento(new Date());
        if (pedido.getTipoPedido().equals(TipoPedido.VENDA)) {
            conta.setTipoConta(TipoConta.RECEBER);
        } else {
            conta.setTipoConta(TipoConta.PAGAR);
        }
        contaDao.save(conta);
    }

    private void atualizaProduto(Pedido pedido) {
        pedido.getProdutos().forEach(item -> {
            Produto produto = produtoDao.find(item.getProduto().getId());
            if (pedido.getTipoPedido().equals(TipoPedido.VENDA)) {
                produto.setEstoque(produto.getEstoque() - item.getQuantidade());
                produto.setValor(item.getValorUnitario());
            } else {
                produto.setEstoque(produto.getEstoque() + item.getQuantidade());
                produto.setCusto(item.getValorUnitario());
            }
            item.setProduto(produtoDao.save(produto));
        });
    }

}
