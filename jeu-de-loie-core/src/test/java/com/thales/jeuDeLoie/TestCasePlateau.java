package com.thales.jeuDeLoie;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.thales.jeuDeLoie.Case;
import com.thales.jeuDeLoie.CaseType;

@RunWith(Parameterized.class)
public class TestCasePlateau {

	private static final String OUTPUT_DEPART = "[D]";
	private static final String OUTPUT_ARRIVEE = "[A]";
	private static final String OUTPUT_AVANCE_TROIS_CASES = "[+3]";
	private static final String OUTPUT_RETOUR_AU_DEPART = "[<-]";
	private static final String OUTPUT_DEFAULT = "[]";
	
	@Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(
                new Object[] { CaseType.DEPART, OUTPUT_DEPART },
                new Object[] { CaseType.ARRIVEE, OUTPUT_ARRIVEE },
                new Object[] { CaseType.AVANCE_TROIS_CASES, OUTPUT_AVANCE_TROIS_CASES },
                new Object[] { CaseType.RETOUR_AU_DEPART, OUTPUT_RETOUR_AU_DEPART },
                new Object[] { CaseType.DEFAULT, OUTPUT_DEFAULT }
            );
    }
    
    private CaseType caseType;
    private String output;
    
	
	public TestCasePlateau(CaseType caseType, String output) {
		this.caseType = caseType;
		this.output = output;
	}

	@Test
	public void test() {
		Case caseDepart= new Case(caseType);
		assertEquals(output, caseDepart.getOutput());
	}

}
