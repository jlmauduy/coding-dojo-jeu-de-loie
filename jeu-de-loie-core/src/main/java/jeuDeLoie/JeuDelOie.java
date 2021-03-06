package jeuDeLoie;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import exceptions.JoueurExisteDejaException;
import exceptions.PartieDemarreException;

public class JeuDelOie {

    private static final int MAX_VALEUR_DE = 6;

    private Map<String, Joueur> joueurs;

    private Map<String, Deplacement> deplacements;

    private static JeuDelOie instance;

    private Plateau plateau;

    private boolean partieDemarree = false;

    private JeuDelOie() {

        this(new Plateau());
    }

    public static JeuDelOie getInstance() {
        if (instance == null) {
            instance = new JeuDelOie();
        }
        return instance;
    }

    JeuDelOie(Plateau plateau) {
        joueurs = new HashMap<String, Joueur>();
        deplacements = new HashMap<String, Deplacement>();
        this.plateau = plateau;
    }

    public Joueur addJoueur(String pseudo) throws JoueurExisteDejaException, PartieDemarreException {
        if (partieDemarree) {
            throw new PartieDemarreException(pseudo);
        }

        Joueur nouveauJoueur = new Joueur(pseudo);
        if (joueurs.containsKey(pseudo)) {
            throw new JoueurExisteDejaException(pseudo);
        }
        joueurs.put(pseudo, nouveauJoueur);
        return nouveauJoueur;
    }

    public List<Joueur> getJoueurs() {
        return new ArrayList<Joueur>(joueurs.values());
    }

    public String getEtatJoueurs() {
        StringBuilder builder = new StringBuilder();
        for (Joueur joueur : joueurs.values()) {
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

    public Joueur getJoueurFromPseudo(String pseudo) {
        return joueurs.get(pseudo);
    }

    public boolean canStart() {
        return !joueurs.isEmpty() && joueurs.size() > 1;
    }

    public Deplacement jouer(String pseudo) {
        // Lancer le dé
        int rand = new Random().nextInt(MAX_VALEUR_DE) + 1;

        // Jouer
        return jouer(pseudo, rand);
    }

    Deplacement jouer(String pseudo, int lanceDe) {
        Joueur joueur = getJoueurFromPseudo(pseudo);

        if (!partieDemarree) {
            partieDemarree = true;
        }

        // position du joueur
        int positionInitiale = joueur.getPosition();

        logEtatJoueur(pseudo, "position initiale :" + positionInitiale + ", dé : " + lanceDe);

        // lancement dé
        int positionCourante = positionInitiale + lanceDe;

        int positionResolue = resolutionComplete(positionCourante, pseudo);

        joueur.setPosition(positionResolue);

        Deplacement deplacement = new Deplacement(pseudo, positionInitiale, positionResolue);

        deplacements.put(deplacement.getId().toString(), deplacement);

        return deplacement;
    }

    private int resolutionComplete(int positionCourante, String pseudo) {
        // resolution case
        int positionSuivante = positionCourante;

        do {
            positionCourante = positionSuivante;
            positionSuivante = resolutionCaseUnitaire(positionCourante, pseudo);

        } while (positionSuivante != positionCourante);

        return positionSuivante;
    }

    private int resolutionCaseUnitaire(int positionCourante, String pseudo) {
        int positionRectifie = Math.min(positionCourante, Plateau.TAILLE_JEU - 1);
        Case caze = plateau.getCases().get(positionRectifie);

        // Si on depasse la taille du plateau on retourne sur la derniere case
        int positionSuivante = caze.getNewPosition(positionRectifie);

        logEtatJoueur(pseudo, MessageFormat.format("Avancement de {0} vers {1} depuis case {2}", positionCourante,
                positionSuivante, caze.getCaseType()));
        return positionSuivante;
    }

    private void logEtatJoueur(String pseudo, String message) {
        System.out.println(MessageFormat.format("[{0}] {1}", pseudo, message));
    }

    public void reset() {
        joueurs.clear();
        deplacements.clear();
        partieDemarree = false;
    }

    public Deplacement getDeplacement(String id) {
        return deplacements.get(id);
    }
}
