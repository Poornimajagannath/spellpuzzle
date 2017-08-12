package edu.gatech.seclass.sdpspell;

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
    public void AddNewspell_Normal() throws Exception {
        admin.addNewspell(encoded,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewspell_NullEncoded() throws Exception {
        admin.addNewspell(null,solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewspell_NullSolution() throws Exception {
        admin.addNewspell(encoded,null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewspell_EmptyEncoded() throws Exception {
        admin.addNewspell("",solution);
    }

    @Test (expected = IllegalArgumentException.class)
    public void AddNewspell_EmptySolution() throws Exception {
        admin.addNewspell(encoded,"");
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