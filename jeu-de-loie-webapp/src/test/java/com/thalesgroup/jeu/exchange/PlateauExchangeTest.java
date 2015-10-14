package com.thalesgroup.jeu.exchange;

import java.util.List;

import jeuDeLoie.Case;
import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Plateau;

import org.junit.Assert;
import org.junit.Test;

import com.thalesgroup.jeu.exchange.CaseExchange;
import com.thalesgroup.jeu.exchange.PlateauExchange;

public class PlateauExchangeTest {

    @Test
    public void testPlateauToPlateauExchange() {
        Plateau plateau = JeuDelOie.getInstance().getPlateau();

        PlateauExchange plateauExchange = new PlateauExchange(plateau);

        List<Case> cases = plateau.getCases();

        List<CaseExchange> casesEx = plateauExchange.getCases();

        Assert.assertEquals(cases.size(), casesEx.size());

        for (int i = 0; i < cases.size(); i++) {
            Assert.assertEquals(cases.get(i).getCaseType(), casesEx.get(i).getType());
        }
    }
}
