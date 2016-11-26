/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.pb.dao;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 * @author Joao
 */
public interface GraficoDao {

    DefaultCategoryDataset produtosMaisVendidos();

    DefaultCategoryDataset quantidadeVendasPorData();

    DefaultCategoryDataset totalVendasPorData();

}
