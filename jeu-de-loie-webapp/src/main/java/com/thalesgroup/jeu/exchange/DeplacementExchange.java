package com.thalesgroup.jeu.exchange;

import java.util.UUID;

import jeuDeLoie.Deplacement;

public class DeplacementExchange {

    private UUID id;
    private String joueur;
    private int positionInitiale;
    private int positionFinale;

    public DeplacementExchange() {
        // TODO Auto-generated constructor stub
    }

    public DeplacementExchange(Deplacement deplacement) {
        this.positionInitiale = deplacement.getPositionInitiale();
        this.positionFinale = deplacement.getPositionFinale();
        this.id = deplacement.getId();
        this.joueur = deplacement.getJoueur();
    }

    public UUID getId() {
        return id;
    }

    public String getJoueur() {
        return joueur;
    }

    public int getPositionInitiale() {
        return positionInitiale;
    }

    public int getPositionFinale() {
        return positionFinale;
    }

}
