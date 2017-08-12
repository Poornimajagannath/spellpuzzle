package edu.gatech.seclass.sdpcryptogram;

import org.junit.Test;

import edu.gatech.seclass.spellpuzzle.CryptogramForPlayer;

import static org.junit.Assert.*;

public class CryptogramforPlayerUnitTest {

    protected String firstSolution = "Solution1";
    protected String emptySolution = "";
    protected String nullSolution = null;

    @Test
    public void replaceStandard() throws Exception {
        CryptogramForPlayer cryptogram = new CryptogramForPlayer();
        cryptogram.setCurrentSolution("zolution1");

        assertEquals(cryptogram.getCurrentSolution(),"zolution1");
    }

    @Test
    public void replaceNull() throws Exception {
        CryptogramForPlayer cryptogram = new CryptogramForPlayer();
        cryptogram.setCurrentSolution(nullSolution);

        assertEquals(cryptogram.getCurrentSolution(),"");
    }

    @Test
    public void replaceEmpty() throws Exception {
        CryptogramForPlayer cryptogram = new CryptogramForPlayer();
        cryptogram.setCurrentSolution(emptySolution);

        assertEquals(cryptogram.getCurrentSolution(),"");
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorNull1() throws Exception{
        CryptogramForPlayer cryptogram = new CryptogramForPlayer(null,false,0,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorNull2() throws Exception{
        CryptogramForPlayer cryptogram = new CryptogramForPlayer("derp",null,0,false);
    }


}