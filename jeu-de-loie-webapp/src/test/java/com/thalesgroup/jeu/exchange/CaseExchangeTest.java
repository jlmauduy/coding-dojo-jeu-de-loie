package com.thalesgroup.jeu.exchange;

import java.util.List;

import jeuDeLoie.Case;
import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Plateau;

import org.junit.Assert;
import org.junit.Test;

public class CaseExchangeTest {

    @Test
    public void testCaseToCaseExchange() {
        Plateau plateau = JeuDelOie.getInstance().getPlateau();

        PlateauExchange plateauExchange = new PlateauExchange(plateau);

        List<Case> cases = plateau.getCases();

        List<CaseExchange> casesEx = plateauExchange.getCases();

        Assert.assertEquals(cases.size(), casesEx.size());

        for (int i = 0; i < cases.size(); i++) {
            Assert.assertEquals(i, casesEx.get(i).getNumero());
        }
    }
}
