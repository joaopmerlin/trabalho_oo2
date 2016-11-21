/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.pb.util;

import br.com.utfpr.pb.config.DatabaseConnection;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

/**
 *
 * @author Joao
 */
public class GeraRelatorio {
    
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    
    public static void jasper(String template, Map parametros) throws JRException{
        InputStream arquivo = GeraRelatorio.class.getResourceAsStream("/jasper/"+ template +".jasper");
        
        JasperReport report = (JasperReport) JRLoader.loadObject(arquivo);
        JasperPrint impressao = JasperFillManager.fillReport(report, parametros, conn);
        JasperViewer viewer =  new JasperViewer(impressao, false);
        viewer.setDefaultCloseOperation( JasperViewer.DO_NOTHING_ON_CLOSE);
        viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
        viewer.setVisible(true);
    }
    
}
