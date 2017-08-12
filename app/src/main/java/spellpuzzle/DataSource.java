package spellpuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import spellpuzzle.ExternalWebService;


// TODO: Delete in favor of calling the new ExternalWebService directly
public class DataSource {

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Spell write(Spell spell) {
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("ENCODED_PHRASE", spell.getEncodedPhrase());
        values.put("SOLUTION_PHRASE", spell.getSolutionPhrase());
        values.put("ID", spell.getId());
        // Insert the new row, returning the primary key value of the new row
        String newRowId = String.valueOf(dbw.insert("spell", null, values));
        spell.setId(newRowId);
        return spell;
    }

    public Player write(Player player) {
        SQLiteDatabase dbplayerwriter = dbHelper.getWritableDatabase();

        ContentValues playervalues = new ContentValues();

        playervalues.put("FIRSTNAME", player.getFirstname());
        playervalues.put("LASTNAME", player.getLastname());
        playervalues.put("USERNAME", player.getUsername());

        dbplayerwriter.insert("PLAYER", null, playervalues);

        PlayerRating playerRating = new PlayerRating(player.getFirstname(), player.getLastname(), 0, 0, 0);
        updatePlayerRatings(playerRating, player.getUsername());
        return player;
    }

    public Player getPlayer(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String projection[] = {
                "USERNAME",
                "FIRSTNAME",
                "LASTNAME"
        };

        Cursor cursor = db.query(
                "PLAYER", // The table to query
                projection, // The columns to return
                "USERNAME=?", // The columns for the WHERE clause
                new String[] {username}, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        Player player = null;

        int count = cursor.getCount();
        Log.i("tag", "count of cursor: " + cursor.getCount());
        while (cursor.moveToNext()) {
            String firstname = cursor.getString(cursor.getColumnIndexOrThrow("FIRSTNAME"));
            String lastname = cursor.getString(cursor.getColumnIndexOrThrow("LASTNAME"));
            PlayerRating playerRating = new PlayerRating(firstname, lastname, 0, 0, 0);
            player = new Player(firstname, lastname, username, playerRating);
            break;
        }

        return player;
    }

    public PlayerRating getPlayerRating(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String projection[] = {
                "USERNAME",
                "FIRSTNAME",
                "LASTNAME",
                "NUM_SPELLS_SOLVED",
                "NUM_SPELLS_STARTED",
                "NUM_INCORRECT_SOLUTIONS"
        };

        Cursor cursor = db.query(
                "PLAYERRATINGS", // The table to query
                projection, // The columns to return
                "USERNAME=?", // The columns for the WHERE clause
                new String[] {username}, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        PlayerRating playerRating = null;

        int count = cursor.getCount();
        Log.i("tag", "count of cursor: " + cursor.getCount());
        while (cursor.moveToNext()) {

            String firstname = cursor.getString(cursor.getColumnIndexOrThrow("FIRSTNAME"));
            String lastname = cursor.getString(cursor.getColumnIndexOrThrow("LASTNAME"));
            int numspellsSolved = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_SPELLS_SOLVED"));
            int numspellsStarted = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_SPELLS_STARTED"));
            int numIncorrectSolutions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SOLUTIONS"));

            playerRating = new PlayerRating(firstname, lastname, numspellsSolved, numspellsStarted, numIncorrectSolutions);

            break;
        }

        return playerRating;
    }

    public List<Spell> fetchspells() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String projection[] = {"ID",
                "ENCODED_PHRASE", "SOLUTION_PHRASE"
        };


        Cursor cursor = db.query(
                "SPELL",                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        Spell spell = null;
        List<Spell> spellList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String Id = cursor.getString(
                    cursor.getColumnIndexOrThrow("ID"));
            String encodedPhrase = cursor.getString(cursor.getColumnIndexOrThrow("ENCODED_PHRASE"));
            String solutionPhrase =cursor.getString(cursor.getColumnIndexOrThrow("SOLUTION_PHRASE"));

            spell = new Spell();
            spell.setId(Id);
            spell.setEncodedPhrase(encodedPhrase);
            spell.setSolutionPhrase(solutionPhrase);

            spellList.add(spell);
            Log.i(this.toString(), "spell is created fetched id " + spell.getId());
            Log.i(this.toString(), spell.getEncodedPhrase());
            Log.i(this.toString(), spell.getSolutionPhrase());






        }
        cursor.close();

        return spellList;
    }

    public List<PlayerRating> getSortedPlayerRatings() {
//        ExternalWebService externalWebService = ExternalWebService.getInstance();
//        List<ExternalWebService.PlayerRating> playerRatings = externalWebService.syncRatingService();
//        Collections.sort(playerRatings, new Comparator<ExternalWebService.PlayerRating>() {
//            @Override
//            public int compare(ExternalWebService.PlayerRating playerRating, ExternalWebService.PlayerRating t1) {
//                return t1.getSolved() - playerRating.getSolved();
//            }
//        });
//        return playerRatings;


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "USERNAME",
                "FIRSTNAME",
                "LASTNAME",
                "NUM_SPELLS_SOLVED",
                "NUM_SPELLS_STARTED",
                "NUM_INCORRECT_SOLUTIONS"
        };

        String sortOrder = "NUM_SPELLS_SOLVED DESC";

        Cursor cursor = db.query(
                "PLAYERRATINGS", // The table to query
                projection, // The columns to return
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder // The sort order
        );

        List<PlayerRating> playerRatings = new ArrayList<>();

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("USERNAME"));
            String firstname = cursor.getString(cursor.getColumnIndexOrThrow("FIRSTNAME"));
            String lastname = cursor.getString(cursor.getColumnIndexOrThrow("LASTNAME"));
            int numspellsSolved = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_SPELLS_SOLVED"));
            int numspellsStarted = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_SPELLS_STARTED"));
            int numIncorrectSolutions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SOLUTIONS"));

            PlayerRating playerRating = new PlayerRating(firstname, lastname, numspellsSolved, numspellsStarted, numIncorrectSolutions);

            playerRatings.add(playerRating);
        }
        cursor.close();

        return playerRatings;
    }

    public void updatePlayerRatings(PlayerRating playerRating, final String username) {
        ExternalWebService externalWebService = ExternalWebService.getInstance();

        SQLiteDatabase dbPlayerRatingsWriter = dbHelper.getWritableDatabase();

        ContentValues playerRatingValues = new ContentValues();

        playerRatingValues.put("USERNAME", username);
        playerRatingValues.put("FIRSTNAME", playerRating.getFirstname());
        playerRatingValues.put("LASTNAME", playerRating.getLastname());
        playerRatingValues.put("NUM_SPELLS_SOLVED", playerRating.getSolved());
        playerRatingValues.put("NUM_SPELLS_STARTED", playerRating.getStarted());
        playerRatingValues.put("NUM_INCORRECT_SOLUTIONS", playerRating.getIncorrect());

        int id = (int) dbPlayerRatingsWriter.insertWithOnConflict(
                "PLAYERRATINGS",
                null,
                playerRatingValues,
                SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1) {
            dbPlayerRatingsWriter.update(
                    "PLAYERRATINGS",
                    playerRatingValues,
                    "username=?",
                    new String[] { username });
        }
    }

    public void updatespellForPlayer(SpellForPlayer crypt, String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = "SPELL_ID="+crypt.getId();

        ContentValues cryptValues = new ContentValues();


        cryptValues.put("USERNAME",username);
        cryptValues.put("SPELL_ID",crypt.getId());
        cryptValues.put("CURRENT_SOLUTION",crypt.getCurrentSolution());
        cryptValues.put("NUM_INCORRECT_SUBMISSIONS",crypt.getNumberOfInCorrectSubmissions());
        cryptValues.put("IS_SOLVED",crypt.isSolved());

        SpellForPlayer c = getspellForPlayer(username, crypt.getId());
        if (c == null) {
            db.insert("SPELLFORPLAYERS", null, cryptValues);
        } else {
            db.update("SPELLFORPLAYERS",cryptValues,where,null);
        }

    }

    public SpellForPlayer getspellForPlayer(String username, String cid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "ID",
                "SPELL_ID",
                "USERNAME",
                "CURRENT_SOLUTION",
                "NUM_INCORRECT_SUBMISSIONS",
                "IS_SOLVED",
                "PRIOR_SOLUTIONS"
        };

        Cursor cursor = db.query(
                "SPELLFORPLAYERS", // The table to query
                projection, // The columns to return
                "username=? and SPELL_ID=?", // The columns for the WHERE clause
                new String[] { username, cid }, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        SpellForPlayer spellForPlayer = null;

        while (cursor.moveToNext()) {
            String spellId = cursor.getString(cursor.getColumnIndexOrThrow("SPELL_ID"));
            String currentSolution = cursor.getString(cursor.getColumnIndexOrThrow("CURRENT_SOLUTION"));
            int numIncorrectSubmissions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SUBMISSIONS"));
            boolean isSolved = cursor.getInt(cursor.getColumnIndexOrThrow("IS_SOLVED")) == 1;

            spellForPlayer = new SpellForPlayer(spellId,
                    currentSolution, numIncorrectSubmissions, isSolved);

        }
        cursor.close();

        return spellForPlayer;
    }

    public List<SpellForPlayer> getListOfspells(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "ID",
                "SPELL_ID",
                "USERNAME",
                "CURRENT_SOLUTION",
                "NUM_INCORRECT_SUBMISSIONS",
                "IS_SOLVED",
                "PRIOR_SOLUTIONS"
        };

        Cursor cursor = db.query(
                "SPELLFORPLAYERS", // The table to query
                projection, // The columns to return
                "username=?", // The columns for the WHERE clause
                new String[] { username }, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        List<SpellForPlayer> listspellForPlayer = new ArrayList<>();

        while (cursor.moveToNext()) {
            String spellId = cursor.getString(cursor.getColumnIndexOrThrow("SPELL_ID"));
            String currentSolution = cursor.getString(cursor.getColumnIndexOrThrow("CURRENT_SOLUTION"));
            int numIncorrectSubmissions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SUBMISSIONS"));
            boolean isSolved = cursor.getInt(cursor.getColumnIndexOrThrow("IS_SOLVED")) == 1;
//            List<String> priorSolutions = convertStringToArray(cursor.getString(cursor.getColumnIndexOrThrow("PRIOR_SOLUTIONS")));

            SpellForPlayer spellForPlayer = new SpellForPlayer(spellId,
                    currentSolution, numIncorrectSubmissions, isSolved);

            listspellForPlayer.add(spellForPlayer);
        }
        cursor.close();

        Log.i(this.toString(), "Is this working ? " + listspellForPlayer.size());
        return listspellForPlayer;
    }

    public static String strSeparator = " , ";

    public static String convertArrayToString(List<String> array){
        String str = "";
        for (int i = 0; i < array.size(); i++) {
            str = str + array.get(i);
            if (i < array.size() - 1){
                str = str+strSeparator;
            }
        }
        return str;
    }

    public static List<String> convertStringToArray(String str){
        List<String> arr = Arrays.asList(str.split("\\s*,\\s*"));
        return arr;
    }
}