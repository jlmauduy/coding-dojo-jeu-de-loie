package jeuDeLoie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Plateau {

    protected List<Case> cases;
    public static final int TAILLE_JEU = 37;
    protected final int NB_MAX_RETOUR_DEPART = 3;
    protected final int NB_MAX_AVANCEE_TROIS_CASES = 3;

    public Plateau() {
        cases = new ArrayList<Case>(TAILLE_JEU);
        initPlateau();
    }

    protected void initPlateau() {

        for (int i = 0; i < NB_MAX_RETOUR_DEPART; i++) {
            cases.add(new Case(CaseType.RETOUR_AU_DEPART));
        }

        for (int i = 0; i < NB_MAX_AVANCEE_TROIS_CASES; i++) {
            cases.add(new Case(CaseType.AVANCE_TROIS_CASES));
        }

        int nbDefaultCases = TAILLE_JEU - NB_MAX_AVANCEE_TROIS_CASES - NB_MAX_RETOUR_DEPART - 2;

        for (int i = 0; i < nbDefaultCases; i++) {
            cases.add(new Case(CaseType.DEFAULT));
        }

        cases.add(0, new Case(CaseType.DEPART));
        cases.add(new Case(CaseType.ARRIVEE));

    }

    public List<Case> getCases() {
        return cases;
    }

    public String output() {
        return cases.stream().map(Case::getOutput).collect(Collectors.joining(""));
    }

}
