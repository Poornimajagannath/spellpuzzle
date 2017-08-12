package edu.gatech.seclass.spellpuzzle;



public class Cryptogram {

    private String encodedPhrase;
    private String solutionPhrase;
    private String id;

    public Cryptogram() {

    }

    public Cryptogram(String encodedPhrase, String solutionPhrase) {
        if(encodedPhrase == null || solutionPhrase == null || encodedPhrase == "" || solutionPhrase == ""){
            throw new IllegalArgumentException();
        }
        this.encodedPhrase = encodedPhrase;
        this.solutionPhrase = solutionPhrase;
    }

    public Cryptogram(String id) {
        this.setId(id);
    }

    public String getEncodedPhrase() {
        return encodedPhrase;
    }

    public void setEncodedPhrase(String encodedPhrase) {
        this.encodedPhrase = encodedPhrase;
    }

    public String getSolutionPhrase() {
        return solutionPhrase;
    }

    public void setSolutionPhrase(String solutionPhrase) {
        this.solutionPhrase = solutionPhrase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] toStringArray() {
        return new String[]{""+id, encodedPhrase, solutionPhrase};
    }
}
