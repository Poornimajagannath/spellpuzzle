package edu.gatech.seclass.spellpuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;


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

    public Cryptogram write(Cryptogram cryptogram) {
        SQLiteDatabase dbw = dbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("ENCODED_PHRASE", cryptogram.getEncodedPhrase());
        values.put("SOLUTION_PHRASE", cryptogram.getSolutionPhrase());
        values.put("ID", cryptogram.getId());
        // Insert the new row, returning the primary key value of the new row
        String newRowId = String.valueOf(dbw.insert("CRYPTOGRAM", null, values));
        cryptogram.setId(newRowId);
        return cryptogram;
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
                "NUM_CRYPTOGRAMS_SOLVED",
                "NUM_CRYPTOGRAMS_STARTED",
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
            int numCryptogramsSolved = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_CRYPTOGRAMS_SOLVED"));
            int numCryptogramsStarted = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_CRYPTOGRAMS_STARTED"));
            int numIncorrectSolutions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SOLUTIONS"));

            playerRating = new PlayerRating(firstname, lastname, numCryptogramsSolved, numCryptogramsStarted, numIncorrectSolutions);

            break;
        }

        return playerRating;
    }

    public List<Cryptogram> fetchCryptograms() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String projection[] = {"ID",
                "ENCODED_PHRASE", "SOLUTION_PHRASE"
        };


        Cursor cursor = db.query(
                "CRYPTOGRAM",                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        Cryptogram cryptogram = null;
        List<Cryptogram> cryptogramList = new ArrayList<>();
        while(cursor.moveToNext()) {
            String Id = cursor.getString(
                    cursor.getColumnIndexOrThrow("ID"));
            String encodedPhrase = cursor.getString(cursor.getColumnIndexOrThrow("ENCODED_PHRASE"));
            String solutionPhrase =cursor.getString(cursor.getColumnIndexOrThrow("SOLUTION_PHRASE"));

            cryptogram = new Cryptogram();
            cryptogram.setId(Id);
            cryptogram.setEncodedPhrase(encodedPhrase);
            cryptogram.setSolutionPhrase(solutionPhrase);

            cryptogramList.add(cryptogram);
            Log.i(this.toString(), "cryptogram is created fetched id " + cryptogram.getId());
            Log.i(this.toString(),cryptogram.getEncodedPhrase());
            Log.i(this.toString(),cryptogram.getSolutionPhrase());






        }
        cursor.close();

        return cryptogramList;
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
                "NUM_CRYPTOGRAMS_SOLVED",
                "NUM_CRYPTOGRAMS_STARTED",
                "NUM_INCORRECT_SOLUTIONS"
        };

        String sortOrder = "NUM_CRYPTOGRAMS_SOLVED DESC";

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
            int numCryptogramsSolved = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_CRYPTOGRAMS_SOLVED"));
            int numCryptogramsStarted = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_CRYPTOGRAMS_STARTED"));
            int numIncorrectSolutions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SOLUTIONS"));

            PlayerRating playerRating = new PlayerRating(firstname, lastname, numCryptogramsSolved, numCryptogramsStarted, numIncorrectSolutions);

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
        playerRatingValues.put("NUM_CRYPTOGRAMS_SOLVED", playerRating.getSolved());
        playerRatingValues.put("NUM_CRYPTOGRAMS_STARTED", playerRating.getStarted());
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

    public void updateCryptogramForPlayer(CryptogramForPlayer crypt, String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where = "CRYPTOGRAM_ID="+crypt.getId();

        ContentValues cryptValues = new ContentValues();


        cryptValues.put("USERNAME",username);
        cryptValues.put("CRYPTOGRAM_ID",crypt.getId());
        cryptValues.put("CURRENT_SOLUTION",crypt.getCurrentSolution());
        cryptValues.put("NUM_INCORRECT_SUBMISSIONS",crypt.getNumberOfInCorrectSubmissions());
        cryptValues.put("IS_SOLVED",crypt.isSolved());

        CryptogramForPlayer c = getCryptogramForPlayer(username, crypt.getId());
        if (c == null) {
            db.insert("CRYPTOGRAMFORPLAYERS", null, cryptValues);
        } else {
            db.update("CRYPTOGRAMFORPLAYERS",cryptValues,where,null);
        }

    }

    public CryptogramForPlayer getCryptogramForPlayer(String username, String cid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "ID",
                "CRYPTOGRAM_ID",
                "USERNAME",
                "CURRENT_SOLUTION",
                "NUM_INCORRECT_SUBMISSIONS",
                "IS_SOLVED",
                "PRIOR_SOLUTIONS"
        };

        Cursor cursor = db.query(
                "CRYPTOGRAMFORPLAYERS", // The table to query
                projection, // The columns to return
                "username=? and CRYPTOGRAM_ID=?", // The columns for the WHERE clause
                new String[] { username, cid }, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        CryptogramForPlayer cryptogramForPlayer = null;

        while (cursor.moveToNext()) {
            String cryptogramId = cursor.getString(cursor.getColumnIndexOrThrow("CRYPTOGRAM_ID"));
            String currentSolution = cursor.getString(cursor.getColumnIndexOrThrow("CURRENT_SOLUTION"));
            int numIncorrectSubmissions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SUBMISSIONS"));
            boolean isSolved = cursor.getInt(cursor.getColumnIndexOrThrow("IS_SOLVED")) == 1;

            cryptogramForPlayer = new CryptogramForPlayer(cryptogramId,
                    currentSolution, numIncorrectSubmissions, isSolved);

        }
        cursor.close();

        return cryptogramForPlayer;
    }

    public List<CryptogramForPlayer> getListOfCryptograms(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "ID",
                "CRYPTOGRAM_ID",
                "USERNAME",
                "CURRENT_SOLUTION",
                "NUM_INCORRECT_SUBMISSIONS",
                "IS_SOLVED",
                "PRIOR_SOLUTIONS"
        };

        Cursor cursor = db.query(
                "CRYPTOGRAMFORPLAYERS", // The table to query
                projection, // The columns to return
                "username=?", // The columns for the WHERE clause
                new String[] { username }, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        List<CryptogramForPlayer> listCryptogramForPlayer = new ArrayList<>();

        while (cursor.moveToNext()) {
            String cryptogramId = cursor.getString(cursor.getColumnIndexOrThrow("CRYPTOGRAM_ID"));
            String currentSolution = cursor.getString(cursor.getColumnIndexOrThrow("CURRENT_SOLUTION"));
            int numIncorrectSubmissions = cursor.getInt(cursor.getColumnIndexOrThrow("NUM_INCORRECT_SUBMISSIONS"));
            boolean isSolved = cursor.getInt(cursor.getColumnIndexOrThrow("IS_SOLVED")) == 1;
//            List<String> priorSolutions = convertStringToArray(cursor.getString(cursor.getColumnIndexOrThrow("PRIOR_SOLUTIONS")));

            CryptogramForPlayer cryptogramForPlayer = new CryptogramForPlayer(cryptogramId,
                    currentSolution, numIncorrectSubmissions, isSolved);

            listCryptogramForPlayer.add(cryptogramForPlayer);
        }
        cursor.close();

        Log.i(this.toString(), "Is this working ? " + listCryptogramForPlayer.size());
        return listCryptogramForPlayer;
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