package spellpuzzle;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalWebService {

    //singleton
    private static ExternalWebService singleton = null;

    //username, first name, last name, solved #, started #, incorrect #
    private static Map<String, PlayerRating> players = new HashMap<>();

    //id, puzzle, solution
    private static List<String[]> crypt =  new ArrayList<>();


    /**
     * code block - run once on instantiation - initializes cryptogram list and player list
     */
    {
        //initialize cryptogram list
        crypt.add(new String[] {"1",
                "Eyhfx, ugq tsga ykkykkfsydfgsk ywx knxpfcfpyzzu nwgrfvfdxl vu xhxpqdfjx gwlxw sqevxw 12333.",
                "Maxie, you know assassinations are specifically prohibited by executive order number 12333."});
        crypt.add(new String[] {"2",
                "Dsj xyw \"Kayy Fxlzalwgvt Flxu dsj Esxj bsxvj dx dsj Kjyy bsxvj ex rxo wxv'd ivxz Zsjlj G au avw dsjv G abbjal xv dsj Lxxf pjsgvw rxo avw Eolblgej jcjlrxvj\" dlgki.",
                "The old \"Call Forwarding From the Shoe phone to the Cell phone so you don't know Where I am and then I appear on the Roof behind you and Surprise everyone\" trick."});
        crypt.add(new String[] {"3",
                "Gsv Xvmgizo Rmgvoortvmxv Ztvmxb dzh xivzgvw rm 1947 dsvm Kivhrwvmg Gifnzm hrtmvw Gsv Mzgrlmzo Hvxfirgb Zxg.",
                "The Central Intelligence Agency was created in 1947 when President Truman signed the National Security Act."});
        crypt.add(new String[] {"4",
                "Qvd S hiocd pqlb nimmbv qhqt hsmp sm mii, sr sm hqgv'm rie tio kbddcsvn jsdg qvd mpqm kiovmqsv ucskasvn komm, Guiiat-Dii.",
                "And I would have gotten away with it too, if it wasn't for you meddling kids and that mountain climbing mutt, Scooby-Doo."});

        //players
        players.put("example555", new PlayerRating("John", "Doe", 2, 3, 5));
        players.put("example556", new PlayerRating("James", "Bond", 1, 2, 4));
        players.put("example557", new PlayerRating("George", "Burdell", 3, 4, 2));
        players.put("example558", new PlayerRating("Nancy", "Drew", 2, 2, 0));
    }

    protected ExternalWebService() {

    }

    public static ExternalWebService getInstance() {
        if(singleton == null) {
            singleton = new ExternalWebService();
        }
        return singleton;
    }

    //inner class, used to make it easier to input/output lists of player ratings
    public class PlayerRating{
        String firstname;
        String lastname;
        int solved;
        int started;
        int incorrect;



        public PlayerRating(String firstname, String lastname, int solved, int started,
                            int incorrect) {
            super();
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
        public String getLastname() {
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



        //generated
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
            result = prime * result + incorrect;
            result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
            result = prime * result + solved;
            result = prime * result + started;
            return result;
        }



        //generated
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PlayerRating other = (PlayerRating) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (firstname == null) {
                if (other.firstname != null)
                    return false;
            } else if (!firstname.equals(other.firstname))
                return false;
            if (incorrect != other.incorrect)
                return false;
            if (lastname == null) {
                if (other.lastname != null)
                    return false;
            } else if (!lastname.equals(other.lastname))
                return false;
            if (solved != other.solved)
                return false;
            if (started != other.started)
                return false;
            return true;
        }



        //generated
        private ExternalWebService getOuterType() {
            return ExternalWebService.this;
        }



    }



    public String addspellService(String puzzle, String solution) throws IllegalArgumentException{

        for(String[] st : crypt)
        {
            //no duplicates
            if(puzzle.equals(st[1]) || solution.equals(st[2]))
                throw new IllegalArgumentException("Duplicate puzzle");
        }

        //validation
        if (puzzle.length() != solution.length())
            throw new IllegalArgumentException("Invalid puzzle");
        Map<Character, Character> lettermap = new HashMap<>();
        for (int i = 0; i < puzzle.length(); i++)
        {
            if (Character.isLetter(puzzle.charAt(i)) && Character.isLetter(solution.charAt(i)))
            {
                if(Character.isUpperCase(puzzle.charAt(i)) != Character.isUpperCase(solution.charAt(i)))
                    throw new IllegalArgumentException("Invalid puzzle");
                if(lettermap.containsKey(Character.toLowerCase(puzzle.charAt(i))))
                {
                    if(Character.toLowerCase(solution.charAt(i)) != lettermap.get(Character.toLowerCase(puzzle.charAt(i))))
                        throw new IllegalArgumentException("Invalid puzzle");
                }
                else
                {
                    if (lettermap.values().contains(Character.toLowerCase(solution.charAt(i))))
                        throw new IllegalArgumentException("Invalid puzzle");
                    lettermap.put(Character.toLowerCase(puzzle.charAt(i)), Character.toLowerCase(solution.charAt(i)));
                }

            }
            else if (puzzle.charAt(i)!=(solution.charAt(i)))
            {
                throw new IllegalArgumentException("Invalid puzzle");
            }
        }

        //add to list
        String id = String.valueOf((crypt.size() + 1));
        crypt.add(new String[]{id,puzzle,solution});
        return id;
    }

    /**
     * get the list of cryptograms
     * @return list of cryptograms
     */
    public List<String[]> syncSpellService(){
        return crypt;
    }

    /**
     * Get the list of all player ratings, unordered
     * @return list of player ratings (PlayerRating contains String firstname, String lastname, int solved, int started, int incorrect)
     */
    public List<PlayerRating> syncRatingService(){
        return new ArrayList<PlayerRating>(players.values());
    }

    /**
     * input the player username plus parts of one player rating
     * @param username player username
     * @param firstname player first name
     * @param lastname player last name
     * @param solved total number of cryptograms ever solved
     * @param started total number of cryptograms ever started/attempted
     * @param incorrect total number of cryptogram solutions ever submitted incorrectly
     * @return true if successfully updated or added player rating, false if any values are null or empty or cannot add to player ratings
     */
    public boolean updateRatingService(String username,	String firstname, String lastname, int solved, int started,	int incorrect){
        if(username == null || username.isEmpty() || firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty())
            return false;

        PlayerRating add = new PlayerRating(firstname, lastname, solved, started, incorrect);
        players.put(username, add);
        return true;
    }

    /**
     * get list of player usernames, unordered
     * @return list of player usernames
     */
    public List<String> playernameService(){
        return new ArrayList<String>(players.keySet());
    }

}
