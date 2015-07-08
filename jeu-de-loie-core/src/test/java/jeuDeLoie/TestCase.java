package jeuDeLoie;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestCase {

    private static final String OUTPUT_DEPART = "[D]";
    private static final String OUTPUT_ARRIVEE = "[A]";
    private static final String OUTPUT_AVANCE_TROIS_CASES = "[+3]";
    private static final String OUTPUT_RETOUR_AU_DEPART = "[<-]";
    private static final String OUTPUT_DEFAULT = "[]";

    @Parameters
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[] { CaseType.DEPART, OUTPUT_DEPART }, new Object[] { CaseType.ARRIVEE,
                OUTPUT_ARRIVEE }, new Object[] { CaseType.AVANCE_TROIS_CASES, OUTPUT_AVANCE_TROIS_CASES },
                new Object[] { CaseType.RETOUR_AU_DEPART, OUTPUT_RETOUR_AU_DEPART }, new Object[] { CaseType.DEFAULT,
                        OUTPUT_DEFAULT });
    }

    private CaseType caseType;
    private String output;

    public TestCase(CaseType caseType, String output) {
        this.caseType = caseType;
        this.output = output;
    }

    @Test
    public void test() {
        Case caseDepart = new Case(caseType);
        assertEquals(output, caseDepart.getOutput());
    }

    @Test
    public void testGetPosition() {
        Case caseDefault = new Case(CaseType.DEFAULT);
        assertEquals(4, caseDefault.getNewPosition(4));

        Case caseAvance3 = new Case(CaseType.AVANCE_TROIS_CASES);
        assertEquals(7, caseAvance3.getNewPosition(4));

        Case caseRetour = new Case(CaseType.RETOUR_AU_DEPART);
        assertEquals(0, caseRetour.getNewPosition(4));

        Case caseDepart = new Case(CaseType.DEPART);
        assertEquals(4, caseDepart.getNewPosition(4));

        Case caseArrivee = new Case(CaseType.ARRIVEE);
        assertEquals(4, caseArrivee.getNewPosition(4));
    }

}
