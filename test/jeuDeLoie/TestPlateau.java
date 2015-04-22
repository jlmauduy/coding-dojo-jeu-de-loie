package jeuDeLoie;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestPlateau {
	
	private static final int TAILLE_JEU = 37;
	private final int NB_MAX_RETOUR_DEPART=3;
	private final int NB_MAX_AVANCEE_TROIS_CASES=3;
	

	@Test
	public void testCases() {
		Plateau plateau = new Plateau();
		
		Map<CaseType,Long> mapCaseType = plateau.getCases().stream().map(Case :: getCaseType).collect(Collectors.groupingBy(z->z,Collectors.counting()));
		
		assertEquals(3, mapCaseType.get(CaseType.RETOUR_AU_DEPART).longValue());
		assertEquals(3, mapCaseType.get(CaseType.AVANCE_TROIS_CASES).longValue());
		assertEquals(1, mapCaseType.get(CaseType.DEPART).longValue());
		assertEquals(1, mapCaseType.get(CaseType.ARRIVEE).longValue());
		assertEquals(CaseType.DEPART, plateau.getCases().get(0).getCaseType());
		assertEquals(CaseType.ARRIVEE, plateau.getCases().get(plateau.getCases().size()-1).getCaseType());
		assertEquals(37, plateau.getCases().size());
	}
	
	@Test
	public void testOutputPlateau(){
		Plateau plateau = new Plateau();
		int nombreCharacter = 29 * 2 + 3*4 + 3*4  + 3*2;
		System.out.println(plateau.output());
		assertEquals(nombreCharacter, plateau.output().length());
	}
	


}
