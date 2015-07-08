package jeuDeLoie;


public class Main {
    public static void main(String[] args) {
        JeuDelOie jeu = JeuDelOie.getInstance();

        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        System.out.println("Etat du plateau");
        System.out.println(jeu.getPlateau().output());

        System.out.println("Etat des joueurs");
        System.out.println(jeu.getEtatJoueurs());

    }
}
