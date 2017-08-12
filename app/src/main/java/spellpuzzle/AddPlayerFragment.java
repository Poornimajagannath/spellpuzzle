package spellpuzzle;

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

import harry.potter.R;
import edu.gatech.seclass.utilities.ExternalWebService;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link AddPlayerFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddPlayerFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddPlayerFragment extends Fragment {




    private ExternalWebService externalWebService;
    //Save to local db

    private ExternalWebServiceOld externalWebServiceOld;

    public AddPlayerFragment(){

        //Constructor
    }

    public  void hideKeyboardFrom(Context context, View view) {


        InputMethodManager inputmanager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputmanager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        if (getActivity().getCurrentFocus() != null) {
            inputmanager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);


        }}
    public static AddPlayerFragment newInstance() {
        AddPlayerFragment fragment = new AddPlayerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View inflate =  inflater.inflate(R.layout.fragment_add_player, container, false);
        externalWebService = ExternalWebService.getInstance();

        //connection to DB

        externalWebServiceOld = new ExternalWebServiceOld(inflate.getContext());



        Button addplayerButton = (Button) inflate.findViewById(R.id.addplayer);

        addplayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getContext(),view);
                addplayerButton(inflate);
//                Toast.makeText(getActivity(),"Player was added!",Toast.LENGTH_SHORT).show();
            }

        });
        return inflate;
    }

        public void addplayerButton(View view){
            EditText usernameText = view.findViewById(R.id.username);
            String sUsername = usernameText.getText().toString();

            if(TextUtils.isEmpty(sUsername)){

                usernameText.setError("Can't be empty!");
            }

            EditText firstnameText = view.findViewById(R.id.firstname);

            String sfirstname = firstnameText.getText().toString();

            if(TextUtils.isEmpty(sfirstname)){

                firstnameText.setError("Can't be empty!");
            }
            EditText lastnameText = view.findViewById(R.id.lastname);


            String slastname = lastnameText.getText().toString();

            if(TextUtils.isEmpty(slastname)){

                lastnameText.setError("Can't be empty!");
            }

            if (usernameText.getText().toString().isEmpty()) {
//                Log.e(this.toString(), "usernameText is null");
                Toast.makeText(getActivity()," is null",Toast.LENGTH_SHORT).show();
            }

            if (firstnameText.getText().toString().isEmpty()) {
                Toast.makeText(getActivity()," is null",Toast.LENGTH_SHORT).show();
            }

            if (lastnameText.getText().toString().isEmpty()) {
                Toast.makeText(getActivity()," is null",Toast.LENGTH_SHORT).show();
            }
            if (usernameText != null && firstnameText != null && lastnameText != null){
                Log.e(this.toString(), "firstnameText is null"+ firstnameText.getText().toString());
                // to DB Local
//
                externalWebServiceOld.addNewPlayer(
                        usernameText.getText().toString(), firstnameText.getText().toString(),lastnameText.getText().toString());}


                //From EWS mock jar

                boolean result =  externalWebService.updateRatingService(
                        usernameText.getText().toString(), firstnameText.getText().toString(),lastnameText.getText().toString(),0,0,0);
                if(result != true){

                    Toast.makeText(getActivity(),"Player not Added!Please provide all the fields ",Toast.LENGTH_SHORT).show();

                }

                else{

                    Toast.makeText(getActivity(),"Player has been added",Toast.LENGTH_SHORT).show();
                }




//            }


}}
