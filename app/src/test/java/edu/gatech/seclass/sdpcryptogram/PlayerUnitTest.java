package edu.gatech.seclass.sdpcryptogram;

/**
 * Created by Tanner on 7/6/2017.
 */

import org.junit.Test;

import edu.gatech.seclass.spellpuzzle.CryptogramForPlayer;
import edu.gatech.seclass.spellpuzzle.Player;
import edu.gatech.seclass.spellpuzzle.PlayerRating;
import edu.gatech.seclass.utilities.ExternalWebService;
//import edu.gatech.seclass.utilities.ExternalWebService.PlayerRating;

import static org.junit.Assert.*;

/*
*     public PlayerRating(String firstname, String lastname, int solved, int started, int incorrect) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.solved = solved;
        this.started = started;
        this.incorrect = incorrect;
    }
*
* */

public class PlayerUnitTest {
    private static ExternalWebService externalWebService = ExternalWebService.getInstance();
    CryptogramForPlayer crypt = new CryptogramForPlayer();
    PlayerRating playerRating = new PlayerRating("Bob", "Test", 0, 0, 0);
    Player playa = new Player("Bob","Test","Bobby", playerRating);

    @Test
    public void SubmitSolution_Incorrect() throws Exception {
        crypt.setCurrentSolution("Solution");
        crypt.setSolutionPhrase("NotSolution");

        playa.submitSolution(crypt);
        assertEquals(playa.submitSolution(crypt),false);
    }

    @Test
    public void SubmitSolution_Correct() throws Exception {
        crypt.setCurrentSolution("And I would have gotten away with it too, if it wasn't for you meddling kids and that mountain climbing mutt, Scooby-Doo.");
        crypt.setId("4");
        crypt.setSolutionPhrase("And I would have gotten away with it too, if it wasn't for you meddling kids and that mountain climbing mutt, Scooby-Doo.");

        assertEquals(playa.submitSolution(crypt),true);
    }

    @Test (expected = IllegalArgumentException.class)
    public void SubmitSolution_Null() throws Exception {
        crypt.setCurrentSolution(null);

        playa.submitSolution(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Player_IllegalArgument1() throws Exception{
        Player player = new Player(null,null,"bobby",playerRating);
    }

    @Test (expected = IllegalArgumentException.class)
    public void Player_IllegalArgument2() throws Exception{
        Player player = new Player("herp","derp",null,null);
    }

   /* @Test
    public void getListOfSolvedCryptograms_Normal() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void getListOfSolvedCryptograms_NoSolvedCryptograms() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void getSortedPlayerRankings_Normal() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void getSortedPlayerRankings_AssertSorted() throws Exception {
        fail("Not yet implemented");
    }*/

/*    @Test
    public void ChooseCryptogram_Normal() throws Exception {

        playa.choseCryptogram("12345");//must be existing cryptogram
    }

    @Test(expected = IllegalArgumentException.class)
    public void ChooseCryptogram_Null() throws Exception {

        playa.choseCryptogram(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ChooseCryptogram_Empty() throws Exception {

        playa.choseCryptogram("");
    }

    @Test
    public void ChooseCryptogram_InvalidID() throws Exception {

        playa.choseCryptogram("This is probably an ID right?");
    }*/



}