/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.utfpr.pb.enumeration;

/**
 *
 * @author Joao
 */
public enum CreditoDebito {

    C("Crédito"),
    D("Débito");

    private String label;

    private CreditoDebito(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
