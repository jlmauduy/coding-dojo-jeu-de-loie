package com.thales.jeuDeLoie;

public class Joueur {

    private int position;

    private String pseudo;

    private EtatJoueurEnum etatDuJoueur;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.position = 0;
        etatDuJoueur = EtatJoueurEnum.EN_ATTENTE;
    }

    public EtatJoueurEnum getEtatDuJoueur() {
        return etatDuJoueur;
    }

    public void setEtatDuJoueur(EtatJoueurEnum etatDuJoueur) {
        this.etatDuJoueur = etatDuJoueur;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void incrementPosition(int valeurDe) {
        position = position + valeurDe;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Joueur other = (Joueur) obj;
        if (pseudo == null) {
            if (other.pseudo != null)
                return false;
        } else if (!pseudo.equals(other.pseudo))
            return false;
        return true;
    }

}
