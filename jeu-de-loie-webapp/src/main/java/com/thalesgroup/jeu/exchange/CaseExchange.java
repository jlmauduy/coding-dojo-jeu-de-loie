package com.thalesgroup.jeu.exchange;

import jeuDeLoie.CaseType;

/**
 * Case exchange
 * 
 * @author Julien GROCH (T0122018)
 *
 */
public class CaseExchange {

    private CaseType type;
    private int numero;

    public CaseExchange() {
        // Pour la serialisation
    }

    public CaseExchange(CaseType caseType, int index) {
        this.type = caseType;
        this.numero = index;
    }

    public CaseType getType() {
        return type;
    }

    public void setType(CaseType type) {
        this.type = type;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

}
