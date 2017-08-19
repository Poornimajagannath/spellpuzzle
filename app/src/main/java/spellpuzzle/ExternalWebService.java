package spellpuzzle;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalWebService {


    private static ExternalWebService singleton = null;


    private static Map<String, PlayerRating> players = new HashMap<>();


    private static List<String[]> crypt =  new ArrayList<>();



    {

        crypt.add(new String[] {"1",
                "gtaeimnAu",
                "Aguamenti"});
        crypt.add(new String[] {"2",
                "haomoolAr",
                "Alohomora"});
        crypt.add(new String[] {"3",
                "aadKvAavrdea",
                "AvadaKedavra"});
        crypt.add(new String[] {"4",
                "Enrigoog",
                "Engorgio"});

        
        players.put("eg1", new PlayerRating("Lily", "Evans", 2, 3, 5));
        players.put("eg2", new PlayerRating("James", "Potter", 1, 2, 4));
        players.put("eg3", new PlayerRating("Harry", "Potter", 3, 4, 2));
        players.put("eg4", new PlayerRating("Ron", "Weasley", 2, 2, 0));
    }

    protected ExternalWebService() {

    }

    public static ExternalWebService getInstance() {
        if(singleton == null) {
            singleton = new ExternalWebService();
        }
        return singleton;
    }


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




        private ExternalWebService getOuterType() {
            return ExternalWebService.this;
        }



    }



    public String addspellService(String puzzle, String solution) throws IllegalArgumentException{

        for(String[] st : crypt)
        {

            if(puzzle.equals(st[1]) || solution.equals(st[2]))
                throw new IllegalArgumentException("Duplicate puzzle");
        }


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


        String id = String.valueOf((crypt.size() + 1));
        crypt.add(new String[]{id,puzzle,solution});
        return id;
    }


    public List<String[]> syncSpellService(){
        return crypt;
    }


    public List<PlayerRating> syncRatingService(){
        return new ArrayList<PlayerRating>(players.values());
    }


    public boolean updateRatingService(String username,	String firstname, String lastname, int solved, int started,	int incorrect){
        if(username == null || username.isEmpty() || firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty())
            return false;

        PlayerRating add = new PlayerRating(firstname, lastname, solved, started, incorrect);
        players.put(username, add);
        return true;
    }


    public List<String> playernameService(){
        return new ArrayList<String>(players.keySet());
    }

}
