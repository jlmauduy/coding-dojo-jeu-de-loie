package jeuDeLoie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import exceptions.JoueurExisteDejaException;

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
        String joueur = "Pamela75";
        jeu.addJoueur(joueur);
        String joueur2 = "JohnDoe";
        jeu.addJoueur(joueur2);
        String joueur3 = "kevin93";
        jeu.addJoueur(joueur3);

        String etat = jeu.getEtatJoueurs();
        assertEquals(joueur + ":0\n" + joueur2 + ":0\n" + joueur3 + ":0\n", etat);

    }

    @Test(expected = JoueurExisteDejaException.class)
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
    public void testJoueurJoueUneFoisSurCaseSpeciale() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        System.out.println(jeu.getPlateau().output());

        // case 4 est une case retour départ
        jeu.jouer(pseudo, 4);

        Joueur joueur = jeu.getJoueurs().get(0);
        assertEquals(0, joueur.getPosition());
    }

    @Test
    public void testJoueurJoueDeuxFois() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        System.out.println(jeu.getPlateau().output());

        // case 4 est une case retour départ
        jeu.jouer(pseudo, 4);

        Joueur joueur = jeu.getJoueurs().get(0);
        assertEquals(0, joueur.getPosition());

        // case 2 est un +3 et case 5 un retour départ
        jeu.jouer(pseudo, 2);
        assertEquals(0, joueur.getPosition());

    }

    /**
     * test d'un enchainement de cases speciales grace au plateau maitrisé.
     */
    @Test
    public void testEnchainementCasesSpeciales() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        Joueur joueur = jeu.getJoueurs().get(0);

        // la case 2 correspond à une case +3 qui amenera vers une case retour depart.
        jeu.jouer(pseudo, 2);
        assertEquals(0, joueur.getPosition());

    }

    @Test
    public void testJoueurJoueUneFoisSurCaseDefault() {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        // case 7 est une case default
        jeu.jouer(pseudo, 7);

        Joueur joueur = jeu.getJoueurs().get(0);
        assertEquals(7, joueur.getPosition());
    }

    @Test
    public void testJoueurArriveSurCaseFinale() {
        String pseudo = "kevin93";
        Joueur joueur = jeu.addJoueur(pseudo);

        // case 36 est une case finale
        jeu.jouer(pseudo, 36);

        assertEquals(36, joueur.getPosition());
    }

    @Test
    public void testJoueurDepasseLaCaseFinale() {
        String pseudo = "kevin93";
        Joueur joueur = jeu.addJoueur(pseudo);

        joueur.setPosition(34);
        // case 36 est une case finale
        jeu.jouer(pseudo, 6);

        assertEquals(36, joueur.getPosition());
    }

    @Test
    public void testReset() {
        jeu.addJoueur("claude");
        jeu.resetJoueurs();
        assertEquals(0, jeu.getJoueurs().size());
    }

}
