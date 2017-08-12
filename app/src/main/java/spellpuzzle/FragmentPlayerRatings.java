package spellpuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import harry.potter.R;
import spellpuzzle.ExternalWebService;

public class FragmentPlayerRatings extends Fragment {
    private ExternalWebService externalWebService;
    private ExternalWebServiceOld externalWebServiceOld;

    public FragmentPlayerRatings() {
        // Required empty public constructor
    }

    public static FragmentPlayerRatings newInstance() {
        FragmentPlayerRatings fragment = new FragmentPlayerRatings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_player_ratings, container, false);
        final Context context = inflate.getContext();
        externalWebService = ExternalWebService.getInstance();

        //for DB
        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.notify();
        Log.i(this.toString(),"Hello" + sharedPreferences.getAll());
        editor.commit();
        Log.i(this.toString(), editor.toString());

        playerRatings(inflate, context, username);

        return inflate;
    }
    
    public void playerRatings(View view, Context context, String username){
        List<ExternalWebService.PlayerRating> playerRatingList = externalWebService.syncRatingService();


        List<String> playersAdded = new ArrayList<>();
        List<PlayerRating> playerRatings = new ArrayList<>();
        for (ExternalWebService.PlayerRating pr : playerRatingList) {
            playerRatings.add(new PlayerRating(pr.getFirstname(), pr.getLastname(), pr.getSolved(), pr.getStarted(), pr.getIncorrect()));
            playersAdded.add(pr.getFirstname() + pr.getLastname());
        }

        List<PlayerRating> tempPlayerRatings = externalWebServiceOld.fetchPlayerRatings();
        for (PlayerRating pr : tempPlayerRatings) {
            if (!playersAdded.contains(pr.getFirstname() + pr.getLastname())) {
                playerRatings.add(pr);
            }
        }
        Collections.sort(playerRatings, new Comparator<PlayerRating>() {
            @Override
            public int compare(PlayerRating playerRating, PlayerRating t1) {
                return t1.getSolved() - playerRating.getSolved();
            }
        });

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.table_of_player_ratings);
        tableLayout.removeAllViews();
        // Add header row
        TableRow tbrow0 = new TableRow(view.getContext());
        TextView tv0 = new TextView(view.getContext());
        tv0.setText(" Firstname ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(view.getContext());
        tv1.setText(" Solved ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(view.getContext());
        tv2.setText(" Started ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(view.getContext());
        tv3.setText(" Incorrect Submissions ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        tableLayout.addView(tbrow0);

        tableLayout.setColumnStretchable(0,true);
        tableLayout.setColumnStretchable(1,true);
        tableLayout.setColumnStretchable(2,true);
        tableLayout.setColumnStretchable(3,true);

        // Add data to rows
        for (PlayerRating playerRating : playerRatings) {
            TableRow tbrow = new TableRow(context);
            TextView t1v = new TextView(context);
            t1v.setText(playerRating.getFirstname());
            t1v.setTextColor(Color.BLUE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(context);
            t2v.setText("" + playerRating.getSolved());
            t2v.setTextColor(Color.BLUE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(context);
            t3v.setText("" + playerRating.getStarted());
            t3v.setTextColor(Color.BLUE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(context);
            t4v.setText("" + playerRating.getIncorrect());
            t4v.setTextColor(Color.BLUE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            tableLayout.addView(tbrow,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
}
