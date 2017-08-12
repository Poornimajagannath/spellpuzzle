package spellpuzzle;
import harry.potter.R;
import edu.gatech.seclass.utilities.ExternalWebService;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link AddSpellFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddSpellFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddSpellFragment extends Fragment {
    private ExternalWebService externalWebService;
    private ExternalWebServiceOld externalWebServiceOld;

    public AddSpellFragment() {
        // Required empty public constructor
    }

    public static AddSpellFragment newInstance() {
        AddSpellFragment fragment = new AddSpellFragment();
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
        final View inflate = inflater.inflate(R.layout.fragment_add_spell, container, false);
        externalWebService = ExternalWebService.getInstance();

        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());


        // Inflate the layout for this fragment
        Button addspellButton = (Button) inflate.findViewById(R.id.addspellButton);
        addspellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(),view);
                try{
                addspell(inflate);
//                Toast.makeText(getActivity(),"Spell was added!" ,Toast.LENGTH_SHORT).show();
}
                catch(IllegalArgumentException e){
                    Log.e(this.toString(),"exception is "+ e);
                    Toast.makeText(getActivity(),"Invalid Spell,Try again!"+e,Toast.LENGTH_SHORT).show();

                }

            }
        });
        return inflate;
    }

    public void addspell(View view) {
        EditText encodedPhraseText = view.findViewById(R.id.inputPhrase);

        String sUsername = encodedPhraseText.getText().toString();

        if(TextUtils.isEmpty(sUsername)){

            encodedPhraseText.setError("Can't be empty!");
        }
        EditText solutionPhraseText = view.findViewById(R.id.solutionPhrase);

        if(TextUtils.isEmpty(sUsername)){

            solutionPhraseText.setError("Can't be empty!");
        }


        if (encodedPhraseText == null) {
            Log.e(this.toString(), "encodedPhraseText is null");
        }

        if (solutionPhraseText == null) {
            Log.e(this.toString(), "solutionPhraseText is null");
        }
        if (encodedPhraseText != null && solutionPhraseText != null) {

            String id =  externalWebService.addCryptogramService(
                    encodedPhraseText.getText().toString(), solutionPhraseText.getText().toString());
            Log.i(this.toString(), "spell is created external with id " + id);
            Toast.makeText(getActivity(),"Spell was added!" ,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),"Spell was added with ID : " + id ,Toast.LENGTH_SHORT).show();



            externalWebServiceOld.addNewspell(id,
                    encodedPhraseText.getText().toString(), solutionPhraseText.getText().toString());
            Log.i(this.toString(), "spell is created internal with id " + id + " solution = " + solutionPhraseText.getText().toString());
            Toast.makeText(getActivity(),"Spell was added!" ,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),"Spell was added with ID : " + id ,Toast.LENGTH_SHORT).show();
        }
    }


}
