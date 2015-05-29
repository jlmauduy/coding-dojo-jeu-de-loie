package jeuDeLoie;

public class PlateauMaitrise extends Plateau {

    @Override
    protected void initPlateau() {
        cases.add(0, new Case(CaseType.DEPART));

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

        cases.add(new Case(CaseType.ARRIVEE));
    }

}
