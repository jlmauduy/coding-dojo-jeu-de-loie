package com.thales.jeuDeLoie;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thales.jeuDeLoie.JeuDelOie;
import com.thales.jeuDeLoie.Joueur;

public class TestJeuDelOie {

	
	
	@Test
	public void testAjoutJoueur() {
		JeuDelOie jeu = new JeuDelOie();
		String pseudo = "kevin93";
		
		jeu.addJoueur(pseudo);
		assertTrue(jeu.getJoueurs().contains(new Joueur(pseudo)));
	}
	
}
