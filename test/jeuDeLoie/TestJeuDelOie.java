package jeuDeLoie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestJeuDelOie {

    private JeuDelOie jeu;

    @Before
    public void setUp() {
        jeu = new JeuDelOie(new PlateauMaitrise());
    }

    @Test
    public void testPresencePlateau() {
        assertNotNull(jeu.getPlateau());
    }

    @Test
    public void testAjoutJoueur() {
        String pseudo = "kevin93";

        jeu.addJoueur(pseudo);
        assertTrue(jeu.getJoueurs().contains(new Joueur(pseudo)));
    }

    @Test
    public void testJoueursPositionne() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        for (Joueur joueur : jeu.getJoueurs()) {
            assertTrue(joueur.getPosition() > -1 && joueur.getPosition() < Plateau.TAILLE_JEU);
        }
    }

    @Test
    public void testgetZeroJoueur() {
        String etat = jeu.getEtatJoueurs();
        assertEquals("", etat);
    }

    @Test
    public void testgetUnJoueur() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        String etat = jeu.getEtatJoueurs();
        assertEquals(pseudo + ":0\n", etat);
    }

    @Test
    public void testgetTroisJoueurs() {
        String joueur = "kevin93";
        jeu.addJoueur(joueur);
        String joueur2 = "Pamela75";
        jeu.addJoueur(joueur2);
        String joueur3 = "JohnDoe";
        jeu.addJoueur(joueur3);

        String etat = jeu.getEtatJoueurs();
        assertEquals(joueur + ":0\n" + joueur2 + ":0\n" + joueur3 + ":0\n", etat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjoutDoublePseudo() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);
        jeu.addJoueur(pseudo);
    }

    @Test
    public void testCannotStart() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);
        assertFalse(jeu.canStart());
    }

    @Test
    public void testCanStart() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);
        String joueur2 = "Pamela75";
        jeu.addJoueur(joueur2);
        assertTrue(jeu.canStart());
    }

    @Test
    // TODO : Revoir lorsque les règles seront implémentées
    public void testJoueurJoue() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        jeu.jouer(pseudo, 4);

        Joueur joueur = jeu.getJoueurs().get(0);
        assertEquals(7, joueur.getPosition());
    }

}
