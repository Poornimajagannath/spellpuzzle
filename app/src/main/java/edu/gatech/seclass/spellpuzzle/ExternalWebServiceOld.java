package edu.gatech.seclass.spellpuzzle;

import android.content.Context;

import java.util.List;
import java.util.UUID;

public class ExternalWebServiceOld {

    private DataSource dataSource;

    public ExternalWebServiceOld(Context context) {
        dataSource = new DataSource(context);
    }

    public String addNewCryptogram(
            String id,
            final String encodedPhrase,
            final String solutionPhrase
    ) {
        dataSource.open();
        Cryptogram cr = new Cryptogram(encodedPhrase, solutionPhrase);
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        cr.setId(id);
        try {
            return dataSource.write(cr).getId();
        } finally {
            dataSource.close();
        }
    }

    public void addNewPlayer(
            final String username,final String firstname,final String lastname){

        dataSource.open();
        PlayerRating playerRating = new PlayerRating(firstname, lastname, 0, 0, 0);
        dataSource.write(new Player(firstname,lastname, username, playerRating));
        dataSource.close();
    }


    public List<Cryptogram> fetchCryptograms() {

        dataSource.open();
        try {
            return dataSource.fetchCryptograms();
        } finally {
            dataSource.close();
        }
    }

    public Player getPlayer(String username) {
        dataSource.open();
        try {
            return dataSource.getPlayer(username);
        } finally {
            dataSource.close();
        }
    }

    public List<CryptogramForPlayer> getListOfCryptograms(String username) {
        dataSource.open();
        try {
            return dataSource.getListOfCryptograms(username);
        } finally {
            dataSource.close();
        }
    }

    public void updateCryptogram(CryptogramForPlayer crypt, String username){
        dataSource.open();
        try {
            dataSource.updateCryptogramForPlayer(crypt,username);
        } finally {
            dataSource.close();
        }
    }

    public PlayerRating getPlayerRating(String username) {
        dataSource.open();
        try {
            return dataSource.getPlayerRating(username);
        } finally {
            dataSource.close();
        }
    }

    public List<PlayerRating> fetchPlayerRatings() {
        dataSource.open();
        try {
            return dataSource.getSortedPlayerRatings();
        } finally {
            dataSource.close();
        }
    }

    public void updatePlayerRating(PlayerRating rating, String username) {
        dataSource.open();
        try {
            dataSource.updatePlayerRatings(rating, username);
        } finally {
            dataSource.close();
        }
    }

}
