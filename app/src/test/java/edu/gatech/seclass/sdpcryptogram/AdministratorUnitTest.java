package edu.gatech.seclass.sdpcryptogram;

import spellpuzzle.Administrator;
import edu.gatech.seclass.utilities.ExternalWebService;
import edu.gatech.seclass.utilities.ExternalWebService.PlayerRating;

//TODO: delet ths
public class AdministratorUnitTest {
    protected String encoded = "Encoded";
    protected String solution = "Solution";
    private Administrator admin = new Administrator();
    private PlayerRating playerRating =
            ExternalWebService.getInstance().new PlayerRating("derp","derpina", 0,0,0);
/*
    @Test
    public void AddNewCryptogram_Normal() throws Exception {
        admin.addNewCryptogram(encoded,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewCryptogram_NullEncoded() throws Exception {
        admin.addNewCryptogram(null,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewCryptogram_NullSolution() throws Exception {
        admin.addNewCryptogram(encoded,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewCryptogram_EmptyEncoded() throws Exception {
        admin.addNewCryptogram("",solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewCryptogram_EmptySolution() throws Exception {
        admin.addNewCryptogram(encoded,"");
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewPlayer_Normal() throws Exception {
        admin.addNewPlayer(playa);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewPlayer_Normal() throws Exception {
        admin.addNewPlayer(null);
    }
    */
}