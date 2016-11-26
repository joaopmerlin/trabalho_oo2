package br.com.utfpr.pb.dao.impl;

import br.com.utfpr.pb.dao.GraficoDao;
import br.com.utfpr.pb.dao.PedidoDao;
import br.com.utfpr.pb.enumeration.TipoPedido;
import br.com.utfpr.pb.model.Pedido;
import br.com.utfpr.pb.model.PedidoItem;
import br.com.utfpr.pb.model.Produto;
import org.jfree.data.category.DefaultCategoryDataset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Joao on 25/11/2016.
 */
public class GraficoDaoImpl implements GraficoDao {

    private PedidoDao pedidoDao = new PedidoDaoImpl();

    @Override
    public DefaultCategoryDataset produtosMaisVendidos() {
        List<PedidoItem> itens = new ArrayList<>();
        pedidoDao.findAll().stream().map(Pedido::getProdutos).forEach(e -> itens.addAll(e));

        Map<Produto, Double> vendas = itens.stream()
                .filter(e -> e.getPedido().getTipoPedido().equals(TipoPedido.VENDA))
                .collect(Collectors.groupingBy(PedidoItem::getProduto, Collectors.summingDouble(PedidoItem::getValorTotal)));

        Map<Produto, Double> compras = itens.stream()
                .filter(e -> e.getPedido().getTipoPedido().equals(TipoPedido.COMPRA))
                .collect(Collectors.groupingBy(PedidoItem::getProduto, Collectors.summingDouble(PedidoItem::getValorTotal)));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        vendas.entrySet().forEach(obj -> dataset.addValue(obj.getValue(), "Venda", obj.getKey().getDescricao()));
        compras.entrySet().forEach(obj -> dataset.addValue(obj.getValue(), "Compra", obj.getKey().getDescricao()));

        return dataset;
    }

    @Override
    public DefaultCategoryDataset quantidadeVendasPorData() {
        List<Pedido> pedidos = pedidoDao.findAll();

        Map<Date, Long> vendas = pedidos.stream()
                .filter(e -> e.getTipoPedido().equals(TipoPedido.VENDA))
                .collect(Collectors.groupingBy(Pedido::getEmissao, Collectors.counting()));

        Map<Date, Long> compras = pedidos.stream()
                .filter(e -> e.getTipoPedido().equals(TipoPedido.COMPRA))
                .collect(Collectors.groupingBy(Pedido::getEmissao, Collectors.counting()));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        vendas.entrySet().forEach(obj -> dataset.addValue(obj.getValue(), "Quantidade Venda",
                new SimpleDateFormat("dd/MM/yyyy").format(obj.getKey())));
        compras.entrySet().forEach(obj -> dataset.addValue(obj.getValue(), "Quantidade Compra",
                new SimpleDateFormat("dd/MM/yyyy").format(obj.getKey())));

        return dataset;
    }

    @Override
    public DefaultCategoryDataset totalVendasPorData() {
        List<Pedido> pedidos = pedidoDao.findAll();

        Map<Date, Double> vendas = pedidos.stream()
                .filter(e -> e.getTipoPedido().equals(TipoPedido.VENDA))
                .collect(Collectors.groupingBy(Pedido::getEmissao, Collectors.summingDouble(Pedido::getTotal)));

        Map<Date, Double> compras = pedidos.stream()
                .filter(e -> e.getTipoPedido().equals(TipoPedido.COMPRA))
                .collect(Collectors.groupingBy(Pedido::getEmissao, Collectors.summingDouble(Pedido::getTotal)));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        vendas.entrySet().forEach(obj -> dataset.addValue(obj.getValue(), "Total Vendas",
                new SimpleDateFormat("dd/MM/yyyy").format(obj.getKey())));
        compras.entrySet().forEach(obj -> dataset.addValue(obj.getValue(), "Total Compras",
                new SimpleDateFormat("dd/MM/yyyy").format(obj.getKey())));

        return dataset;
    }
}
