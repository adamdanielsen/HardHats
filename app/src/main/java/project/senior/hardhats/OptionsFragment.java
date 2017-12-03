package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    Button logoutButton;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmpasswordEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText phoneNumberEditText;
    EditText faxNumberEditText;
    EditText licenseNumberEditText;
    EditText emailAddressEditText;
    EditText companyNameEditText;
    EditText streetAddressEditText;
    EditText cityEditText;
    EditText zipCodeEditText;
    Spinner state;
    String user_state;
    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //change stuff here to give logic to layout
        usernameEditText =(EditText) getView().findViewById(R.id.option_UserNameEditText);
        passwordEditText = (EditText) getView().findViewById(R.id.option_PasswordEditText);
        confirmpasswordEditText =(EditText) getView().findViewById(R.id.option_ConfirmPasswordEditText);
        firstNameEditText = (EditText) getView().findViewById(R.id.option_firstName_editText);
        lastNameEditText = (EditText) getView().findViewById(R.id.option_lastName_editText);
        phoneNumberEditText = (EditText) getView().findViewById(R.id.option_phoneNumber_editText);
        faxNumberEditText = (EditText) getView().findViewById(R.id.option_faxNumber_editText);
        licenseNumberEditText = (EditText) getView().findViewById(R.id.option_licenseNumber_editText);
        emailAddressEditText = (EditText) getView().findViewById(R.id.option_emailAddress_editText);
        companyNameEditText = (EditText) getView().findViewById(R.id.option_CompanyName_editText);
        streetAddressEditText = (EditText) getView().findViewById(R.id.option_streetAddress_editText);
        cityEditText = (EditText) getView().findViewById(R.id.option_city_editText);
        zipCodeEditText = (EditText) getView().findViewById(R.id.option_zipCode_editText);
        state = (Spinner) getView().findViewById(R.id.option_state_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(state_listener);
        logoutButton = (Button) getView().findViewById(R.id.fragmentOptions_LogoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity)getActivity()).Logout();
            }
        });


    }

    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                user_state = (String) state.getItemAtPosition(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
