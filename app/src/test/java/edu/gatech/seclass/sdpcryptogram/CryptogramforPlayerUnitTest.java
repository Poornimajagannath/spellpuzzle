package edu.gatech.seclass.sdpspell;

import org.junit.Test;

import spellpuzzle.SpellForPlayer;

import static org.junit.Assert.*;

public class spellforPlayerUnitTest {

    protected String firstSolution = "Solution1";
    protected String emptySolution = "";
    protected String nullSolution = null;

    @Test
    public void replaceStandard() throws Exception {
        SpellForPlayer spell = new SpellForPlayer();
        spell.setCurrentSolution("zolution1");

        assertEquals(spell.getCurrentSolution(),"zolution1");
    }

    @Test
    public void replaceNull() throws Exception {
        SpellForPlayer spell = new SpellForPlayer();
        spell.setCurrentSolution(nullSolution);

        assertEquals(spell.getCurrentSolution(),"");
    }

    @Test
    public void replaceEmpty() throws Exception {
        SpellForPlayer spell = new SpellForPlayer();
        spell.setCurrentSolution(emptySolution);

        assertEquals(spell.getCurrentSolution(),"");
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorNull1() throws Exception{
        SpellForPlayer spell = new SpellForPlayer(null,false,0,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorNull2() throws Exception{
        SpellForPlayer spell = new SpellForPlayer("derp",null,0,false);
    }


}