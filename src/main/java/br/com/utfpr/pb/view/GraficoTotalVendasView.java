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
public class GraficoTotalVendasView {

    private GraficoDao graficoDao = new GraficoDaoImpl();

    public GraficoTotalVendasView() {
        try {
            // cria o gráfico
            JFreeChart grafico = ChartFactory.createBarChart("Total de Vendas vs Compras por Data", "Data",
                    "Valor Total", graficoDao.totalVendasPorData(), PlotOrientation.VERTICAL, true, true, false);

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
