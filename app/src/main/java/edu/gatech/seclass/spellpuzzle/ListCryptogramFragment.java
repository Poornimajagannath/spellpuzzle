package edu.gatech.seclass.spellpuzzle;

import edu.gatech.seclass.sdpcryptogram.R;
import edu.gatech.seclass.utilities.ExternalWebService;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link ListCryptogramFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link ListCryptogramFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ListCryptogramFragment extends Fragment {

    private ExternalWebService externalWebService;
    private ExternalWebServiceOld externalWebServiceOld;

    public ListCryptogramFragment() {
        // Required empty public constructor
    }

    public static ListCryptogramFragment newInstance() {
        ListCryptogramFragment fragment = new ListCryptogramFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_solve_cryptogram);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View inflate = inflater.inflate(R.layout.fragment_solve_cryptogram, container, false);
        externalWebService = ExternalWebService.getInstance();

        // write to DB

        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());


        listCryptogram(inflate);


        return inflate;
    }

    public void listCryptogram(View view) {

        final List<String[]> cryptogramList = externalWebService.syncCryptogramService();

        final List<Cryptogram> cryptList = externalWebServiceOld.fetchCryptograms();
        final Set<String> cryptogramIdSet = new HashSet<>();
        for (String[] arr : cryptogramList) {
            cryptogramIdSet.add(arr[0]);
        }

        //Fetch the cryptograms added Locally present in the DB
        for (Cryptogram cr : cryptList) {
            if (!cryptogramIdSet.contains(cr.getId())) {
                cryptogramList.add(cr.toStringArray());
            }

        }

        Log.i(this.toString(),"number of cryptograms in list="+ cryptogramList.size() );

        SharedPreferences sharedPreferences =
                this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString("Username", "");

        final List<CryptogramForPlayer> cryptogramForPlayerList = externalWebServiceOld.getListOfCryptograms(username);

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableofcryptogram);
        tableLayout.removeAllViews();

        TableRow tableHeader = new TableRow(view.getContext());
        tableHeader.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView tableHeaderCol = new TextView(view.getContext());
        tableHeaderCol.setText("ID");
        tableHeaderCol.setTypeface(Typeface.DEFAULT_BOLD);
        tableHeaderCol.setTextColor(Color.BLACK);
        tableHeader.addView(tableHeaderCol);

        TextView tableHeaderCol1 = new TextView(view.getContext());
        tableHeaderCol1.setText("Solved?");
        tableHeaderCol1.setPadding(5,5,5,5);
        tableHeaderCol1.setTypeface(Typeface.DEFAULT_BOLD);
        tableHeaderCol1.setTextColor(Color.BLACK);
        tableHeader.addView(tableHeaderCol1);

        TextView tableHeaderCol2 = new TextView(view.getContext());
        tableHeaderCol2.setText("Incorrect Submissions");
        tableHeaderCol2.setPadding(5,5,5,5);
        tableHeaderCol2.setTypeface(Typeface.DEFAULT_BOLD);
        tableHeaderCol2.setTextColor(Color.BLACK);
        tableHeader.addView(tableHeaderCol2);

        TextView tableHeaderCol3 = new TextView(view.getContext());
        tableHeaderCol3.setText("Encoded Phrase");
        tableHeaderCol3.setPadding(5,5,5,5);
        tableHeaderCol3.setTypeface(Typeface.DEFAULT_BOLD);
        tableHeaderCol3.setTextColor(Color.BLACK);
        tableHeader.addView(tableHeaderCol3);

        TextView tableHeaderCol4 = new TextView(view.getContext());
        tableHeaderCol4.setVisibility(View.GONE);
        tableHeader.addView(tableHeaderCol4);

        tableLayout.setColumnStretchable(0,true);
        tableLayout.setColumnStretchable(1,true);
        tableLayout.setColumnStretchable(2,true);
        tableLayout.setColumnStretchable(3,true);
        tableLayout.addView(tableHeader,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        Log.i(this.toString(),"Header code done");

        //CREATE ROWS
        for (final String[] cryptogram : cryptogramList){
            TableRow tableRow = new TableRow(view.getContext());
            tableRow.setClickable(true);

            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // CREATE COLUMNS

            TextView IDView = new TextView(view.getContext());
            IDView.setText("" + cryptogram[0]);
            IDView.setTextColor(Color.BLUE);

            TextView solvedView = new TextView(view.getContext());
            boolean isSolved = false;

            TextView incorrectSubmissionsView = new TextView(view.getContext());
            int incorrectSubmissions = 0;
            Log.i(this.toString(), "list before for" +  cryptogramForPlayerList.size());

            for (CryptogramForPlayer cryptogramForPlayer : cryptogramForPlayerList) {
                Log.i(this.toString(), "id = " +  cryptogramForPlayer.getId() + " isSolved = " + cryptogramForPlayer.isSolved());
                if (cryptogramForPlayer.getId().equalsIgnoreCase(cryptogram[0])) {
                    incorrectSubmissions = cryptogramForPlayer.getNumberOfInCorrectSubmissions();
                    isSolved = cryptogramForPlayer.isSolved();

                    Log.i(this.toString(), "marking solved" + cryptogramForPlayer.isSolved());
                    break;
                }
            }
            solvedView.setText(String.valueOf(isSolved));
            solvedView.setTextColor(Color.BLUE);

            incorrectSubmissionsView.setText(String.valueOf(incorrectSubmissions));
            incorrectSubmissionsView.setTextColor(Color.BLUE);

            TextView encodedPhraseView = new TextView(view.getContext());
            encodedPhraseView.setText(cryptogram[1]);
            encodedPhraseView.setTextColor(Color.BLUE);

            TextView solutionPhraseView = new TextView(view.getContext());
            solutionPhraseView.setText(cryptogram[2]);
            solutionPhraseView.setVisibility(View.GONE);

            tableRow.addView(IDView);
            tableRow.addView(solvedView);
            tableRow.addView(incorrectSubmissionsView);
            tableRow.addView(encodedPhraseView);
            tableRow.addView(solutionPhraseView);

            tableRow.setOnClickListener(new View.OnClickListener(){
                @Override
            public void onClick(View view) {
                    view.setBackgroundColor(Color.BLUE);
                    //TODO: pick the right cryptogram and pass it in
                    //TODO: check if cryptogram has already been started and use that one instead of assuming its new
                    Player player = externalWebServiceOld.getPlayer(username);

                    TableRow t = (TableRow) view;
                    String[] rowInfo = new String[((TableRow) view).getChildCount()];
                    for (int i =0;i < 5; i++) {
                        TextView firstTextView = (TextView) t.getChildAt(i);
                        if (firstTextView != null) {
                            rowInfo[i] = firstTextView.getText() != null ? firstTextView.getText().toString(): "";
                        }

                    }

                    Log.i(this.toString(), "table info = " + Arrays.toString(rowInfo));

                    CryptogramForPlayer chosenCryptogram = new CryptogramForPlayer(
                            rowInfo[0],
                            Boolean.parseBoolean(rowInfo[1]),
                            Integer.parseInt(rowInfo[2]),
                            rowInfo[3]
                    );
                    chosenCryptogram.setSolutionPhrase(rowInfo[4]);



                    for (CryptogramForPlayer cryptogramForPlayer : cryptogramForPlayerList) {

                        if (cryptogramForPlayer.getId().equalsIgnoreCase(chosenCryptogram.getId())) {
                            chosenCryptogram.setCurrentSolution(cryptogramForPlayer.getCurrentSolution());
                            Log.i(this.toString(), "marking solved" + cryptogramForPlayer.isSolved());
                            break;
                        }
                    }

                    Fragment fragment = new SolveCryptogramFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("chosenCryptogram", chosenCryptogram);
                    fragment.setArguments(bundle);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(this.toString());//TODO: fix backstack (currently goes back to login screen)
                    ft.replace(R.id.content, fragment);
                    ft.commit();

                    // TODO : USE THE SELECTED CRYPTOGRAM to SOLVE. YOU CAN USE THIS OnClick Listener to change the page to Solve the selected Cryptogram
                    // For now, I've just changed color to Blue to demo that it works.
//                    Fragment fragment = SolveCryptogramFragment.newInstance();
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.addToBackStack(null);//TODO: fix backstack (currently goes back to login screen)
//                    ft.replace(R.id.content, fragment);
//                    ft.commit();

            }

            });
            tableLayout.addView(tableRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableLayout.setColumnStretchable(0,true);
            tableLayout.setColumnStretchable(1,true);
            tableLayout.setColumnStretchable(2,true);
            tableLayout.setColumnStretchable(3,true);
        }

    }

}
