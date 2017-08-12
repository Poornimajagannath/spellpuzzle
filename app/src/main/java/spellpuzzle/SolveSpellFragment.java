package spellpuzzle;

import harry.potter.R;
import spellpuzzle.ExternalWebService;

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


public class SolveSpellFragment extends Fragment {

    private static ExternalWebService externalWebService;
    private static ExternalWebServiceOld externalWebServiceOld;
    private static String username;
    private static Player currentPlayer;

    private SpellForPlayer currentSpell;

    public SolveSpellFragment() {

    }

    public static SolveSpellFragment newInstance() {
        SolveSpellFragment fragment = new SolveSpellFragment();
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
        final View inflate = inflater.inflate(R.layout.fragment_solve_spell_new, container, false);
        externalWebService = ExternalWebService.getInstance();
        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());

        solvespell(inflate);
        return inflate;
    }

    public void onSavePressed() {
        TextView edit = getView().findViewById(R.id.EditSolution);

        currentSpell.setCurrentSolution(edit.getText().toString());
        boolean isSolved = currentSpell.getSolutionPhrase().equalsIgnoreCase(currentSpell.getCurrentSolution());
        currentSpell.setSolved(isSolved);
//        showMessage(isSolved, currentSpell);
        Toast.makeText(getActivity(),"Spell is saved",Toast.LENGTH_SHORT).show();
        spellpuzzle.PlayerRating playerRating = externalWebServiceOld.getPlayerRating(username);
        if (!isSolved) {
            playerRating.setStarted(playerRating.getStarted()+1);
        }
        externalWebServiceOld.updatespell(currentSpell,username);
        externalWebServiceOld.updatePlayerRating(playerRating, username);
        externalWebService.updateRatingService(currentPlayer.getUsername(), currentPlayer.getFirstname(), currentPlayer.getLastname(),
                playerRating.getSolved(), playerRating.getStarted(), playerRating.getIncorrect());
    }

    public void onSubmitPressed() {
        TextView edit = getView().findViewById(R.id.EditSolution);
        currentSpell.setCurrentSolution(edit.getText().toString());
        boolean isSolved = currentSpell.getSolutionPhrase().equalsIgnoreCase(currentSpell.getCurrentSolution());
        currentSpell.setSolved(isSolved);
        if (!isSolved) {
            currentSpell.setNumberOfInCorrectSubmissions(currentSpell.getNumberOfInCorrectSubmissions()+1);
        }
        showMessage(isSolved, currentSpell);

//        currentPlayer.submitSolution(currentSpell);
        externalWebServiceOld.updatespell(currentSpell,username);
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

    private void showMessage(boolean isSolved, SpellForPlayer currentspell) {
        if (isSolved) {
            Toast.makeText(getActivity(),"You solved it!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"That's incorrect, please try again.",Toast.LENGTH_SHORT).show();
            Log.e(this.toString(), "Current soln = " + currentspell.getCurrentSolution() + " actual soln = " + currentspell.getSolutionPhrase());
        }
    }
    public void solvespell(View view) {

        hideKeyboardFrom(getContext(),view);
        //Get Player
        SharedPreferences sharedPreferences =
                this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username", "");
        currentPlayer = externalWebServiceOld.getPlayer(username);

        //Get Spell
        Bundle bundle = getArguments();
        currentSpell = (SpellForPlayer) bundle.getSerializable("chosenspell");

        Log.i(this.toString(), " Spell : " + currentSpell.getSolutionPhrase());
        //set ID
        TextView cryptID = view.findViewById(R.id.spellID);
        cryptID.setText(currentSpell.getId());//why is this a long anyway?

        //populate encodedphrase
        TextView encoded = view.findViewById(R.id.DisplayEncodedPhrase);
        encoded.setText(currentSpell.getEncodedPhrase());

        //populate currentsolution
        TextView solution = view.findViewById(R.id.EditSolution);
        solution.setText(currentSpell.getCurrentSolution());

        //set up save listener
        final Button save = view.findViewById(R.id.Savespell);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSavePressed();
            }
        });

        //set up submit listener
        final Button submit = view.findViewById(R.id.Submitspell);
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
