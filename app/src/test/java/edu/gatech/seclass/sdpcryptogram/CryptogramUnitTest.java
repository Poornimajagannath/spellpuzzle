package edu.gatech.seclass.sdpcryptogram;

import org.junit.Test;

import spellpuzzle.Cryptogram;


public class CryptogramUnitTest {
    private String encoded = "Encoded";
    private String solution = "Solution";

    @Test
    public void Cryptogram_Normal() throws Exception {
        Cryptogram crypt = new Cryptogram();
    }

    @Test
    public void Cryptogram_Arguments() throws Exception {
        Cryptogram crypt = new Cryptogram(encoded,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Cryptogram_NullEncoded() throws Exception {
        Cryptogram crypt = new Cryptogram(null,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Cryptogram_NullSolution() throws Exception {
        Cryptogram crypt = new Cryptogram(encoded,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Cryptogram_EmptyEncoded() throws Exception {
        Cryptogram crypt = new Cryptogram("",solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Cryptogram_EmptySolution() throws Exception {
        Cryptogram crypt = new Cryptogram(encoded,"");
    }

}