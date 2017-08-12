package spellpuzzle;

import java.io.Serializable;
//import edu.gatech.seclass.utilities.ExternalWebService.PlayerRating;

public class Player extends User implements Serializable {

    private PlayerRating playerRating;

    public Player() {
    }

    public Player(String firstname, String lastname, String username, PlayerRating playerRating) {
        if(firstname == null || lastname == null || username == null || playerRating == null){
            throw new IllegalArgumentException();
        }
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setUsername(username);
        this.setPlayerRating(playerRating);
    }

    private void setPlayerRating(PlayerRating playerRating) {
        this.playerRating = playerRating;
    }

    private PlayerRating getPlayerRating() {
        return this.playerRating;
    }

    public boolean submitSolution(CryptogramForPlayer cryptogramForPlayer){
        if(cryptogramForPlayer == null){
            throw new IllegalArgumentException();
        }
        if(cryptogramForPlayer.getCurrentSolution() == cryptogramForPlayer.getSolutionPhrase()){
            cryptogramForPlayer.setSolved(true);
            playerRating.setSolved(playerRating.getStarted()+1);
            return true;
        } else{
            cryptogramForPlayer.setNumberOfInCorrectSubmissions(cryptogramForPlayer.getNumberOfInCorrectSubmissions()+1);
            playerRating.setIncorrect(playerRating.getIncorrect()+1);
            return false;
        }
    }
}
