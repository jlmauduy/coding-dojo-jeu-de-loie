package jeuDeLoie;

import java.util.UUID;

public class Deplacement {

    private UUID id;
    private String joueur;
    private int positionInitiale;
    private int positionFinale;

    public Deplacement(String joueur, int positionInitiale, int positionFinale) {
        super();
        this.id = UUID.randomUUID();
        this.joueur = joueur;
        this.positionInitiale = positionInitiale;
        this.positionFinale = positionFinale;
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
