package jeuDeLoie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JeuDelOie {

    private static final int MAX_VALEUR_DE = 6;

    // FIXME changer la liste en map, pour changer la fonction getJoueurFromPseudo
    private List<Joueur> joueurs;

    private Plateau plateau;

    public JeuDelOie() {
        this(new Plateau());
    }

    public JeuDelOie(Plateau plateau) {
        joueurs = new ArrayList<Joueur>();
        this.plateau = plateau;
    }

    public void addJoueur(String pseudo) {
        Joueur nouveauJoueur = new Joueur(pseudo);
        if (joueurs.contains(nouveauJoueur)) {
            throw new IllegalArgumentException("Le joueur a déjà été ajouté.");
        }
        joueurs.add(nouveauJoueur);
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public String getEtatJoueurs() {
        StringBuilder builder = new StringBuilder();
        for (Joueur joueur : joueurs) {
            builder.append(joueur.getPseudo());
            builder.append(":");
            builder.append(joueur.getPosition());
            builder.append("\n");
        }
        return builder.toString();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    private Joueur getJoueurFromPseudo(String pseudo) {
        return joueurs.stream().filter(j -> pseudo.equals(j.getPseudo())).findFirst().get();
    }

    public boolean canStart() {
        return !joueurs.isEmpty() && joueurs.size() > 1;
    }

    public void jouer(String pseudo) {
        // Lancer le dé
        int rand = new Random().nextInt(MAX_VALEUR_DE) + 1;

        // Jouer
        jouer(pseudo, rand);
    }

    void jouer(String pseudo, int i) {
        Joueur joueur = getJoueurFromPseudo(pseudo);
        // lancement dé
        joueur.incrementPosition(i);

        // resolution case
        // la case nous permet d'avoir la nouvelle position du joueur
        // il faut a partir de la, que le plateau nous dise sur quelle case il se trouve
        // si il se trouve de nouveau sur une case spécifique, il faudra faire une recursivité
    }

}
