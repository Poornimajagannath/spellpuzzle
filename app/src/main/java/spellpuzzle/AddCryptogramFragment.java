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
// * {@link AddCryptogramFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddCryptogramFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddCryptogramFragment extends Fragment {
    private ExternalWebService externalWebService;
    private ExternalWebServiceOld externalWebServiceOld;

    public AddCryptogramFragment() {
        // Required empty public constructor
    }

    public static AddCryptogramFragment newInstance() {
        AddCryptogramFragment fragment = new AddCryptogramFragment();
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
        final View inflate = inflater.inflate(R.layout.fragment_add_cryptogram, container, false);
        externalWebService = ExternalWebService.getInstance();

        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());


        // Inflate the layout for this fragment
        Button addCryptogramButton = (Button) inflate.findViewById(R.id.addCryptogramButton);
        addCryptogramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(),view);
                try{
                addCryptogram(inflate);
//                Toast.makeText(getActivity(),"Cryptogram was added!" ,Toast.LENGTH_SHORT).show();
}
                catch(IllegalArgumentException e){
                    Log.e(this.toString(),"exception is "+ e);
                    Toast.makeText(getActivity(),"Invalid Cryptogram,Try again!"+e,Toast.LENGTH_SHORT).show();

                }

            }
        });
        return inflate;
    }

    public void addCryptogram(View view) {
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
            Log.i(this.toString(), "cryptogram is created external with id " + id);
            Toast.makeText(getActivity(),"Cryptogram was added!" ,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),"Cryptogram was added with ID : " + id ,Toast.LENGTH_SHORT).show();



            externalWebServiceOld.addNewCryptogram(id,
                    encodedPhraseText.getText().toString(), solutionPhraseText.getText().toString());
            Log.i(this.toString(), "cryptogram is created internal with id " + id + " solution = " + solutionPhraseText.getText().toString());
            Toast.makeText(getActivity(),"Cryptogram was added!" ,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),"Cryptogram was added with ID : " + id ,Toast.LENGTH_SHORT).show();
        }
    }


}
