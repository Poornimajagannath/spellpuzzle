package edu.gatech.seclass.sdpspell;

import org.junit.Test;

import spellpuzzle.Spell;


public class SpellUnitTest {
    private String encoded = "Encoded";
    private String solution = "Solution";

    @Test
    public void spell_Normal() throws Exception {
        Spell crypt = new Spell();
    }

    @Test
    public void spell_Arguments() throws Exception {
        Spell crypt = new Spell(encoded,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void spell_NullEncoded() throws Exception {
        Spell crypt = new Spell(null,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void spell_NullSolution() throws Exception {
        Spell crypt = new Spell(encoded,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void spell_EmptyEncoded() throws Exception {
        Spell crypt = new Spell("",solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void spell_EmptySolution() throws Exception {
        Spell crypt = new Spell(encoded,"");
    }

}