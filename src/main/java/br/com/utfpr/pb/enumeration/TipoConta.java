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
public enum TipoConta {

    RECEBER("Conta a Receber"),
    PAGAR("Conta a Pagar");

    private String label;

    private TipoConta(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
    
}
