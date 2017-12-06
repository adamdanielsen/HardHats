package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private Button logoutButton;
    private EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmpasswordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private EditText faxNumberEditText;
    private EditText licenseNumberEditText;
    private EditText emailAddressEditText;
    private EditText companyNameEditText;
    private EditText streetAddressEditText;
    private EditText cityEditText;
    private EditText zipCodeEditText;
    private Spinner state;
    private String user_state;
    private EditText State;
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
        //passwordEditText = (EditText) getView().findViewById(R.id.option_PasswordEditText);
        //confirmpasswordEditText =(EditText) getView().findViewById(R.id.option_ConfirmPasswordEditText);
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
        State = (EditText) getView().findViewById(R.id.option_state_editText);
        /*state = (Spinner) getView().findViewById(R.id.option_state_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(state_listener);*/
        logoutButton = (Button) getView().findViewById(R.id.fragmentOptions_LogoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity)getActivity()).Logout();
            }
        });
        usernameEditText.setText(SessionData.getInstance().getUsername());
        firstNameEditText.setText(SessionData.getInstance().getFirstName());
        lastNameEditText.setText(SessionData.getInstance().getLastName());
        //passwordEditText.setText(SessionData.getInstance().getPassword());
        phoneNumberEditText.setText(SessionData.getInstance().getPhoneNumber());
        companyNameEditText.setText(SessionData.getInstance().getCompanyName());
        faxNumberEditText.setText(SessionData.getInstance().getFaxNumber());
        licenseNumberEditText.setText(SessionData.getInstance().getFaxNumber());
        emailAddressEditText.setText(SessionData.getInstance().getEmailAddress());
        streetAddressEditText.setText(SessionData.getInstance().getStreet());
        cityEditText.setText(SessionData.getInstance().getCity());
        zipCodeEditText.setText(SessionData.getInstance().getZipCode());
        State.setText(SessionData.getInstance().getState());
        //confirmpasswordEditText.setText(SessionData.getInstance().getPassword());

        usernameEditText.setEnabled(false);
        //passwordEditText.setEnabled(false);
        //confirmpasswordEditText.setEnabled(false);
        State.setEnabled(false);
        companyNameEditText.setEnabled(false);
        firstNameEditText.setEnabled(false);
        lastNameEditText.setEnabled(false);
        phoneNumberEditText.setEnabled(false);
        faxNumberEditText.setEnabled(false);
        licenseNumberEditText.setEnabled(false);
        emailAddressEditText.setEnabled(false);
        streetAddressEditText.setEnabled(false);
        cityEditText.setEnabled(false);
        zipCodeEditText.setEnabled(false);

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
