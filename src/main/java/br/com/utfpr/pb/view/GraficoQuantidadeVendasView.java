/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.pb.view;

import br.com.utfpr.pb.dao.GraficoDao;
import br.com.utfpr.pb.dao.impl.GraficoDaoImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

/**
 * @author João
 */
public class GraficoQuantidadeVendasView {

    private GraficoDao graficoDao = new GraficoDaoImpl();

    public GraficoQuantidadeVendasView() {
        try {
            // cria o gráfico
            JFreeChart grafico = ChartFactory.createBarChart("Número de Vendas vs Compras por Data", "Data",
                    "Quantidade", graficoDao.quantidadeVendasPorData(), PlotOrientation.VERTICAL, true, true, false);

            //exibe o gráfico
            ChartFrame frame = new ChartFrame("Gráfico", grafico);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
