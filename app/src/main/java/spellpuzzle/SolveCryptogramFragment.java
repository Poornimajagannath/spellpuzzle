package spellpuzzle;

import harry.potter.R;
import edu.gatech.seclass.utilities.ExternalWebService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SolveCryptogramFragment extends Fragment {

    private static ExternalWebService externalWebService;
    private static ExternalWebServiceOld externalWebServiceOld;
    private static String username;
    private static Player currentPlayer;

    private CryptogramForPlayer currentCryptogram;

    public SolveCryptogramFragment() {

    }

    public static SolveCryptogramFragment newInstance() {
        SolveCryptogramFragment fragment = new SolveCryptogramFragment();
        return fragment;
    }

    public static void hideKeyboardFrom(Context context, View view) {


        InputMethodManager inputmanager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputmanager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_solve_cryptogram2, container, false);
        externalWebService = ExternalWebService.getInstance();
        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());

        solveCryptogram(inflate);
        return inflate;
    }

    public void onSavePressed() {
        TextView edit = getView().findViewById(R.id.EditSolution);

        currentCryptogram.setCurrentSolution(edit.getText().toString());
        boolean isSolved = currentCryptogram.getSolutionPhrase().equalsIgnoreCase(currentCryptogram.getCurrentSolution());
        currentCryptogram.setSolved(isSolved);
//        showMessage(isSolved, currentCryptogram);
        Toast.makeText(getActivity(),"Cryptogram is saved",Toast.LENGTH_SHORT).show();
        spellpuzzle.PlayerRating playerRating = externalWebServiceOld.getPlayerRating(username);
        if (!isSolved) {
            playerRating.setStarted(playerRating.getStarted()+1);
        }
        externalWebServiceOld.updateCryptogram(currentCryptogram,username);
        externalWebServiceOld.updatePlayerRating(playerRating, username);
        externalWebService.updateRatingService(currentPlayer.getUsername(), currentPlayer.getFirstname(), currentPlayer.getLastname(),
                playerRating.getSolved(), playerRating.getStarted(), playerRating.getIncorrect());
    }

    public void onSubmitPressed() {
        TextView edit = getView().findViewById(R.id.EditSolution);
        currentCryptogram.setCurrentSolution(edit.getText().toString());
        boolean isSolved = currentCryptogram.getSolutionPhrase().equalsIgnoreCase(currentCryptogram.getCurrentSolution());
        currentCryptogram.setSolved(isSolved);
        if (!isSolved) {
            currentCryptogram.setNumberOfInCorrectSubmissions(currentCryptogram.getNumberOfInCorrectSubmissions()+1);
        }
        showMessage(isSolved, currentCryptogram);

//        currentPlayer.submitSolution(currentCryptogram);
        externalWebServiceOld.updateCryptogram(currentCryptogram,username);
        spellpuzzle.PlayerRating playerRating = externalWebServiceOld.getPlayerRating(username);
        if (playerRating == null) {
            playerRating = new spellpuzzle.PlayerRating(currentPlayer.getFirstname(), currentPlayer.getLastname(), 0, 0, 0);
        }
        if (isSolved) {
            playerRating.setSolved(playerRating.getSolved()+1);
        } else {
            playerRating.setIncorrect(playerRating.getIncorrect()+1);
        }
        externalWebServiceOld.updatePlayerRating(playerRating, username);
        externalWebService.updateRatingService(currentPlayer.getUsername(), currentPlayer.getFirstname(), currentPlayer.getLastname(),
                playerRating.getSolved(), playerRating.getStarted(), playerRating.getIncorrect());
    }

    private void showMessage(boolean isSolved, CryptogramForPlayer currentCryptogram) {
        if (isSolved) {
            Toast.makeText(getActivity(),"You solved it!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"That's incorrect, please try again.",Toast.LENGTH_SHORT).show();
            Log.e(this.toString(), "Current soln = " + currentCryptogram.getCurrentSolution() + " actual soln = " + currentCryptogram.getSolutionPhrase());
        }
    }
    public void solveCryptogram(View view) {

        hideKeyboardFrom(getContext(),view);
        //Get Player
        SharedPreferences sharedPreferences =
                this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username", "");
        currentPlayer = externalWebServiceOld.getPlayer(username);

        //Get Cryptogram
        Bundle bundle = getArguments();
        currentCryptogram = (CryptogramForPlayer) bundle.getSerializable("chosenCryptogram");

        Log.i(this.toString(), " Cryptogram : " + currentCryptogram.getSolutionPhrase());
        //set ID
        TextView cryptID = view.findViewById(R.id.CryptogramID);
        cryptID.setText(currentCryptogram.getId());//why is this a long anyway?

        //populate encodedphrase
        TextView encoded = view.findViewById(R.id.DisplayEncodedPhrase);
        encoded.setText(currentCryptogram.getEncodedPhrase());

        //populate currentsolution
        TextView solution = view.findViewById(R.id.EditSolution);
        solution.setText(currentCryptogram.getCurrentSolution());

        //set up save listener
        final Button save = view.findViewById(R.id.SaveCryptogram);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSavePressed();
            }
        });

        //set up submit listener
        final Button submit = view.findViewById(R.id.SubmitCryptogram);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSubmitPressed();
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
