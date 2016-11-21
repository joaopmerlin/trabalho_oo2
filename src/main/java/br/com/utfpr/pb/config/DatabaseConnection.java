/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.pb.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 *
 * @author Vinicius
 */
public class DatabaseConnection {
    private static DatabaseConnection dbConnection;
    private EntityManagerFactory emf;
    private Connection conn;
    
    private DatabaseConnection(){
        this.emf = Persistence.createEntityManagerFactory("default");
        
        Session session = getEntityManager().unwrap(Session.class);
        SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory();
        ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
        try {
            this.conn = connectionProvider.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static DatabaseConnection getInstance(){
        if (dbConnection == null){
            dbConnection = new DatabaseConnection();
        }
        return dbConnection;
    }
    
    public EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public Connection getConnection(){
        return conn;
    }
    
}
