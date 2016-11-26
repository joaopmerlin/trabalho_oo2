package br.com.utfpr.pb.enumeration;

/**
 * Created by João on 11/11/2016.
 */
public enum Menu {

    USUARIO("cadastros", "UsuariosView", "Usuários", "usuario.png"),
    CLIENTE("cadastros", "ClientesView", "Clientes", "cliente.png"),
    FORNECEDOR("cadastros", "FornecedoresView", "Fornecedores", "fornecedor.png"),
    CATEGORIA("cadastros", "CategoriasView", "Categorias", "categoria.png"),
    PRODUTO("cadastros", "ProdutosView", "Produtos", "produto.png"),

    PEDIDO_COMPRA("pedidos", "PedidoCompraView", "Pedido de Compra", "pedidoCompra.png"),
    PEDIDO_VENDA("pedidos", "PedidoVendaView", "Pedido de Venda", "pedidoVenda.png"),

    CAIXA("financeiro", "CaixasView", "Lançamentos", "caixa.png"),
    CONTA_RECEBER("financeiro", "ContaReceberView", "Contas a Receber", "contaReceber.png"),
    CONTA_PAGAR("financeiro", "ContaPagarView", "Contas a Pagar", "contaPagar.png"),

    REL_PEDIDOS("relatorios", "RelatorioPedidosView", "Pedidos", "relatorioPedido.png"),
    REL_ESTOQUE("relatorios", "RelatorioEstoqueView", "Estoque", "relatorioEstoque.png"),
    REL_CONTAS("relatorios", "RelatorioContasView", "Contas", "relatorioEstoque.png"),
    REL_CAIXA("relatorios", "RelatorioCaixaView", "Caixa", "relatorioEstoque.png"),
    REL_PRODUTOS_MAIS_VENDIDOS("relatorios", "RelatorioProdutosMaisVendidosView", "Produtos mais Vendidos", "relatorioEstoque.png"),

    CHART_PRODUTOS_MAIS_VENDIDOS("graficos", "GraficoProdutosMaisVendidosView", "Produtos mais Vendidos / Comprados", "relatorioEstoque.png"),
    CHART_QUANTIDADE_VENDAS("graficos", "GraficoQuantidadeVendasView", "Número de Vendas / Compras", "relatorioEstoque.png"),
    CHART_TOTAL_VENDAS("graficos", "GraficoTotalVendasView", "Valor Total de Vendas / Compras", "relatorioEstoque.png");

    private String task;
    private String descricao;
    private String clazz;
    private String icon;

    Menu(String task, String clazz, String descricao, String icon) {
        this.task = task;
        this.clazz = clazz;
        this.descricao = descricao;
        this.icon = icon;
    }

    public String getTask() {
        return task;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getClazz() {
        return clazz;
    }

    public String getIcon() {
        return icon;
    }
}
