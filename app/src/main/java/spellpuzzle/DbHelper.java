package spellpuzzle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Crypto.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE CRYPTOGRAM (ID TEXT PRIMARY KEY, ENCODED_PHRASE TEXT, SOLUTION_PHRASE TEXT)";
    private static final String SQL_CREATE_ENTRIES_PLAYERS = "CREATE TABLE PLAYER ( USERNAME TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS CRYPTOGRAM";
    private static final String SQL_DELETE_ENTRIES_PLAYERS =
            "DROP TABLE IF EXISTS PLAYER";
    private static final String SQL_CREATE_PLAYER_RATINGS =
            "CREATE TABLE PLAYERRATINGS (USERNAME TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, NUM_CRYPTOGRAMS_SOLVED INTEGER,"
            + " NUM_CRYPTOGRAMS_STARTED INTEGER, NUM_INCORRECT_SOLUTIONS INTEGER)";
    private static final String SQL_CREATE_CRYPTOGRAM_FOR_PLAYERS =
            "CREATE TABLE CRYPTOGRAMFORPLAYERS (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "CRYPTOGRAM_ID INTEGER, USERNAME TEXT, CURRENT_SOLUTION TEXT, "
            + "NUM_INCORRECT_SUBMISSIONS INTEGER, IS_SOLVED INTEGER, PRIOR_SOLUTIONS TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES_PLAYERS);
        db.execSQL(SQL_CREATE_PLAYER_RATINGS);
        db.execSQL(SQL_CREATE_CRYPTOGRAM_FOR_PLAYERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_PLAYERS);
        db.execSQL(SQL_CREATE_PLAYER_RATINGS);
        db.execSQL(SQL_CREATE_CRYPTOGRAM_FOR_PLAYERS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
