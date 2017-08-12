package edu.gatech.seclass.spellpuzzle;

public class PlayerRating {
    String firstname;
    String lastname;
    int solved;
    int started;
    int incorrect;

    public PlayerRating(String firstname, String lastname, int solved, int started, int incorrect) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.solved = solved;
        this.started = started;
        this.incorrect = incorrect;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getStarted() {
        return started;
    }

    public void setStarted(int started) {
        this.started = started;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }
}
