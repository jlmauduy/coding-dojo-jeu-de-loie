package jeuDeLoie;

import java.util.ArrayList;
import java.util.List;

public class JeuDelOie {

	private List<Joueur> joueurs;
	
	public JeuDelOie() {
		joueurs = new ArrayList<Joueur>();
	}
	
	public void addJoueur(String pseudo) {
		joueurs.add(new Joueur(pseudo));
		
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public String getEtatPlateau() {
		
		return null;
	}

}
