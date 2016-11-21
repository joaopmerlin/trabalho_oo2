/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.pb.view;

import br.com.utfpr.pb.controller.*;
import br.com.utfpr.pb.enumeration.TipoPedido;
import br.com.utfpr.pb.model.Pedido;
import br.com.utfpr.pb.model.PedidoItem;
import br.com.utfpr.pb.model.Pessoa;
import br.com.utfpr.pb.model.Produto;
import br.com.utfpr.pb.util.DoubleUtil;
import br.com.utfpr.pb.util.ValidationUtil;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.validation.ConstraintViolationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author João
 */
public class PedidoVendaView extends javax.swing.JInternalFrame {

    private DefaultTableModel model;
    private DefaultTableModel modelItens;
    private CategoriaController categoriaController;
    private ProdutoController produtoController;
    private ClienteController clienteController;
    private PedidoController pedidoController;
    private List<Produto> data;
    private Pedido pedido;
    private DoubleUtil doubleUtil;

    /**
     * Creates new form UsuariosView
     */
    public PedidoVendaView() {
        try {
            pedido = new Pedido();
            pedido.setTipoPedido(TipoPedido.VENDA);
            pedido.setUsuario(UsuarioController.getInstance().getUsuarioLogado());

            categoriaController = CategoriaController.getInstance();
            produtoController = ProdutoController.getInstance();
            clienteController = ClienteController.getInstance();
            pedidoController = PedidoController.getInstance();
            doubleUtil = DoubleUtil.getInstance();

            initComponents();
            initTable();
            pessoa.setModel(new DefaultComboBoxModel(clienteController.findAll().toArray()));
            modelItens = (DefaultTableModel) jTableItens.getModel();

            Dimension desktopSize = PrincipalView.jDesktopPane.getSize();
            Dimension jInternalFrameSize = getSize();
            setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            PrincipalView.jDesktopPane.add(this);
            setMaximum(true);
            setVisible(true);
            moveToFront();
            setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        data = produtoController.findAll();

        model = (DefaultTableModel) jTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        data.forEach(item -> add(item));
        model.fireTableDataChanged();

        tableEvent();
    }

    private void tableEvent() {
        if (jTable.getSelectedRowCount() > 0) {

        } else {

        }
    }

    private void add(Produto produto) {
        Object[] vetor = new Object[4];
        vetor[0] = produto.getId();
        vetor[1] = produto.getDescricao();
        vetor[2] = produto.getCategoria().getDescricao();
        vetor[3] = doubleUtil.format(produto.getValor());
        model.addRow(vetor);
    }

    private void addItens() {
        modelItens.getDataVector().removeAllElements();
        modelItens.fireTableDataChanged();
        pedido.getProdutos().forEach(pedidoItem -> {
            Object[] vetor = new Object[4];
            vetor[0] = pedidoItem.getProduto().getDescricao();
            vetor[1] = doubleUtil.format(pedidoItem.getValorUnitario());
            vetor[2] = pedidoItem.getQuantidade();
            vetor[3] = doubleUtil.format(pedidoItem.getValorUnitario() * pedidoItem.getQuantidade());
            modelItens.addRow(vetor);
        });
        totalPedido.setText(doubleUtil.format(pedido.getTotal()));
    }

    private void openModal() {
        PedidoAddView pedidoAddView = new PedidoAddView(null, true);
        pedidoAddView.setPedidoItem(getSelecionado());
        pedidoAddView.setVisible(true);

        adicionaItem(pedidoAddView.getPedidoItem());
    }

    private void adicionaItem(PedidoItem pedidoItem) {
        if (pedidoItem != null) {
            pedido.addProduto(pedidoItem);
            addItens();
        }
    }

    private PedidoItem getSelecionado() {
        if (jTable.getSelectedRowCount() > 0) {
            Object value = model.getValueAt(jTable.getSelectedRow(), 0);
            PedidoItem pedidoItem = new PedidoItem();
            Produto produto = produtoController.find(Long.parseLong(value.toString()));
            pedidoItem.setProduto(produto);
            pedidoItem.setQuantidade(1);
            pedidoItem.setValorUnitario(produto.getValor());
            pedidoItem.setValorTotal(produto.getValor());
            pedidoItem.setPedido(pedido);
            return pedidoItem;
        }
        return null;
    }

    private void zerarPedido() {
        pedido = new Pedido();
        pedido.setTipoPedido(TipoPedido.VENDA);
        pedido.setUsuario(UsuarioController.getInstance().getUsuarioLogado());
        pedido.setProdutos(new ArrayList<>());
        addItens();
    }

    private void salvar() {
        if (JOptionPane.showConfirmDialog(null, "Deseja finalizar o pedido?") == 0) {
            try {
                pedido.setEmissao(new Date());
                pedido.setPessoa((Pessoa) pessoa.getSelectedItem());
                pedido.getProdutos().forEach(e -> e.setPedido(pedido));
                pedido = pedidoController.save(pedido);
                /*if (JOptionPane.showConfirmDialog(null, "Pedido realizado com sucesso! Deseja imprimir?") == 0) {
                    imprimePedido(pedido);
                }*/
                zerarPedido();
            } catch (Exception e) {
                if (e instanceof ConstraintViolationException) {
                    JOptionPane.showMessageDialog(null, ValidationUtil.getInstance()
                            .getMessage((ConstraintViolationException) e));
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um problema ao salvar.\n\n" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private void imprimePedido(Pedido pedido) {

    }

    private void pesquisa() {
        String query = pesquisa.getText();
        model = (DefaultTableModel) jTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        data.stream()
                .filter(e -> StringUtils.containsIgnoreCase(e.getId().toString(), query) ||
                        StringUtils.containsIgnoreCase(e.getDescricao(), query) ||
                        StringUtils.containsIgnoreCase(e.getCategoria().getDescricao(), query))
                .forEach(item -> add(item));
        model.fireTableDataChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        pesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pessoa = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableItens = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        totalPedido = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        fechar = new javax.swing.JButton();
        finalizarPedido = new javax.swing.JButton();
        cancelarPedido = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Pedido de Venda");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Listagem de Produtos"));

        pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pesquisaKeyReleased(evt);
            }
        });

        jLabel1.setText("Procurar");

        jTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Código", "Produto", "Categoria", "Valor"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pesquisa)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Pedido"));

        jLabel2.setText("Pessoa");

        pessoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(pessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Produtos Adicionados"));

        jTableItens.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Produto", "Valor", "Quantidade", "Total"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableItens);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("TOTAL DO PEDIDO");

        totalPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalPedido.setText("0,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(totalPedido)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(totalPedido))
                                .addContainerGap())
        );

        fechar.setText("Fechar");
        fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecharActionPerformed(evt);
            }
        });

        finalizarPedido.setText("Finalizar Pedido");
        finalizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarPedidoActionPerformed(evt);
            }
        });

        cancelarPedido.setText("Cancelar Pedido");
        cancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(finalizarPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelarPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fechar)
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fechar)
                                        .addComponent(finalizarPedido)
                                        .addComponent(cancelarPedido))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesquisaKeyReleased
        // TODO add your handling code here:
        pesquisa();
    }//GEN-LAST:event_pesquisaKeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        // TODO add your handling code here:
        tableEvent();
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            //handle double click event.
            openModal();
        }
    }//GEN-LAST:event_jTableMouseClicked

    private void fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_fecharActionPerformed

    private void cancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarPedidoActionPerformed
        // TODO add your handling code here:
        zerarPedido();
    }//GEN-LAST:event_cancelarPedidoActionPerformed

    private void finalizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarPedidoActionPerformed
        // TODO add your handling code here:
        salvar();
    }//GEN-LAST:event_finalizarPedidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarPedido;
    private javax.swing.JButton fechar;
    private javax.swing.JButton finalizarPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    private javax.swing.JTable jTableItens;
    private javax.swing.JTextField pesquisa;
    private javax.swing.JComboBox<String> pessoa;
    private javax.swing.JLabel totalPedido;
    // End of variables declaration//GEN-END:variables
}
