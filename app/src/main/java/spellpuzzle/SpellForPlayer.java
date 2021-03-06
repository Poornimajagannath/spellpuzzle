package spellpuzzle;


import java.io.Serializable;
import java.util.List;

public class SpellForPlayer extends Spell implements Serializable{

    private String currentSolution;
    private int numberOfInCorrectSubmissions;
    private boolean isSolved;
    private List<String> priorSolutions;

    public SpellForPlayer() {

    }

    public SpellForPlayer(String spellId,
                          boolean isSolved,
                          int numberOfInCorrectSubmissions,
                          String encodedPhrase) {
        super(spellId);
        if(spellId == null || encodedPhrase == null){
            throw new IllegalArgumentException();
        }
        this.currentSolution = currentSolution;
        this.numberOfInCorrectSubmissions = numberOfInCorrectSubmissions;
        this.isSolved = isSolved;
        this.setEncodedPhrase(encodedPhrase);
    }

    public SpellForPlayer(String spellId, String currentSolution,
                          int numberOfInCorrectSubmissions, boolean isSolved) {
        super(spellId);
        if(spellId == null || currentSolution == null){
            throw new IllegalArgumentException();
        }
        this.currentSolution = currentSolution;
        this.numberOfInCorrectSubmissions = numberOfInCorrectSubmissions;
        this.isSolved = isSolved;
    }

    public SpellForPlayer(String spellId, String currentSolution,
                          int numberOfInCorrectSubmissions, boolean isSolved,
                          List<String> priorSolutions) {
        super(spellId);
        if(spellId == null || currentSolution == null){
            throw new IllegalArgumentException();
        }
        this.currentSolution = currentSolution;
        this.numberOfInCorrectSubmissions = numberOfInCorrectSubmissions;
        this.isSolved = isSolved;

    }

    public String getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(String currentSolution) {
        //dinner = ((dinner = cage.getChicken()) != null) ? dinner : getFreeRangeChicken();
        //this.currentSolution = currentSolution;
        this.currentSolution = ((this.currentSolution = currentSolution) != null) ? this.currentSolution : "";
    }

    public int getNumberOfInCorrectSubmissions() {
        return numberOfInCorrectSubmissions;
    }

    public void setNumberOfInCorrectSubmissions(int numberOfInCorrectSubmissions) {
        this.numberOfInCorrectSubmissions = numberOfInCorrectSubmissions;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public List<String> getPriorSolutions() {
        return priorSolutions;
    }

    public void setPriorSolutions(List<String> priorSolutions) {
        this.priorSolutions = priorSolutions;
    }

}
