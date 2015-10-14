package jeuDeLoie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import exceptions.JoueurExisteDejaException;
import exceptions.PartieDemarreException;

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
    public void testAjoutJoueur() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";

        jeu.addJoueur(pseudo);
        assertTrue(jeu.getJoueurs().contains(new Joueur(pseudo)));
    }

    @Test
    public void testJoueursPositionne() throws JoueurExisteDejaException, PartieDemarreException {
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
    public void testgetUnJoueur() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        String etat = jeu.getEtatJoueurs();
        assertEquals(pseudo + ":0\n", etat);
    }

    @Test
    public void testgetTroisJoueurs() throws JoueurExisteDejaException, PartieDemarreException {
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
    public void testAjoutDoublePseudo() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);
        jeu.addJoueur(pseudo);
    }

    @Test
    public void testCannotStart() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);
        assertFalse(jeu.canStart());
    }

    @Test
    public void testCanStart() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);
        String joueur2 = "Pamela75";
        jeu.addJoueur(joueur2);
        assertTrue(jeu.canStart());
    }

    @Test
    public void testJoueurJoueUneFoisSurCaseSpeciale() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        System.out.println(jeu.getPlateau().output());

        // case 4 est une case retour départ
        jeu.jouer(pseudo, 4);

        Joueur joueur = jeu.getJoueurs().get(0);
        assertEquals(0, joueur.getPosition());
    }

    @Test
    public void testJoueurJoueDeuxFois() throws JoueurExisteDejaException, PartieDemarreException {
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
     * 
     * @throws PartieDemarreException
     * @throws JoueurExisteDejaException
     */
    @Test
    public void testEnchainementCasesSpeciales() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        Joueur joueur = jeu.getJoueurs().get(0);

        // la case 2 correspond à une case +3 qui amenera vers une case retour depart.
        jeu.jouer(pseudo, 2);
        assertEquals(0, joueur.getPosition());

    }

    @Test
    public void testJoueurJoueUneFoisSurCaseDefault() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        // case 7 est une case default
        jeu.jouer(pseudo, 7);

        Joueur joueur = jeu.getJoueurs().get(0);
        assertEquals(7, joueur.getPosition());
    }

    @Test
    public void testJouerCreerDeplacement() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        // case 7 est une case default
        Deplacement deplacement = jeu.jouer(pseudo, 7);

        assertNotNull(deplacement);
        assertNotNull(deplacement.getId());
        assertEquals(pseudo, deplacement.getJoueur());
        assertEquals(0, deplacement.getPositionInitiale());
        assertEquals(7, deplacement.getPositionFinale());
    }

    @Test
    public void testJouer() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        // case 7 est une case default
        Deplacement deplacement = jeu.jouer(pseudo);

        assertNotNull(deplacement);
        assertNotNull(deplacement.getId());
        assertEquals(pseudo, deplacement.getJoueur());
        assertEquals(0, deplacement.getPositionInitiale());
        // impossible de tester la position finale car elle est aléatoire
    }

    @Test
    public void testJoueurArriveSurCaseFinale() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        Joueur joueur = jeu.addJoueur(pseudo);

        // case 36 est une case finale
        jeu.jouer(pseudo, 36);

        assertEquals(36, joueur.getPosition());
    }

    @Test
    public void testJoueurDepasseLaCaseFinale() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        Joueur joueur = jeu.addJoueur(pseudo);

        joueur.setPosition(34);
        // case 36 est une case finale
        jeu.jouer(pseudo, 6);

        assertEquals(36, joueur.getPosition());
    }

    @Test
    public void testReset() throws JoueurExisteDejaException, PartieDemarreException {
        jeu.addJoueur("claude");
        jeu.reset();
        assertEquals(0, jeu.getJoueurs().size());
    }

    @Test
    public void testGetDeplacement() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        jeu.addJoueur(pseudo);

        // case 7 est une case default
        Deplacement deplacementCree = jeu.jouer(pseudo, 7);

        UUID id = deplacementCree.getId();

        Deplacement deplacementTrouve = jeu.getDeplacement(id.toString());
        assertNotNull(deplacementTrouve);
        assertEquals(id, deplacementTrouve.getId());
        assertEquals(pseudo, deplacementTrouve.getJoueur());
        assertEquals(0, deplacementTrouve.getPositionInitiale());
        assertEquals(7, deplacementTrouve.getPositionFinale());
    }

    @Test(expected = PartieDemarreException.class)
    public void testImpossibleAddJoueurPartieDemarre() throws JoueurExisteDejaException, PartieDemarreException {
        String pseudo = "kevin93";
        String pseudoBis = "kevin92";

        jeu.addJoueur(pseudo);
        jeu.jouer(pseudo);
        jeu.addJoueur(pseudoBis);

    }

}
