package com.thalesgroup.jeu.exchange;

import java.util.List;
import java.util.stream.Collectors;

import jeuDeLoie.Case;
import jeuDeLoie.Plateau;

public class PlateauExchange {
    private List<CaseExchange> cases;

    public PlateauExchange() {
        // Pour la serialisation
    }

    public PlateauExchange(Plateau plateau) {
        List<Case> casesOrigin = plateau.getCases();
        cases = casesOrigin.stream().map(caze -> new CaseExchange(caze.getCaseType(), casesOrigin.indexOf(caze)))
                .collect(Collectors.toList());
    }

    public List<CaseExchange> getCases() {
        return cases;
    }

    public void setCases(List<CaseExchange> cases) {
        this.cases = cases;
    }
}
