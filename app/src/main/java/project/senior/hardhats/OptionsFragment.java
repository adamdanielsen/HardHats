package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private Button logoutButton;
    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private EditText faxNumberEditText;
    private EditText licenseNumberEditText;
    private EditText emailAddressEditText;
    private EditText companyNameEditText;
    private EditText address;
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
        firstNameEditText = (EditText) getView().findViewById(R.id.option_firstName_editText);
        lastNameEditText = (EditText) getView().findViewById(R.id.option_lastName_editText);
        phoneNumberEditText = (EditText) getView().findViewById(R.id.option_phoneNumber_editText);
        faxNumberEditText = (EditText) getView().findViewById(R.id.option_faxNumber_editText);
        licenseNumberEditText = (EditText) getView().findViewById(R.id.option_licenseNumber_editText);
        emailAddressEditText = (EditText) getView().findViewById(R.id.option_emailAddress_editText);
        companyNameEditText = (EditText) getView().findViewById(R.id.option_CompanyName_editText);
        address = (EditText) getView().findViewById(R.id.option_address);
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
        phoneNumberEditText.setText(SessionData.getInstance().getPhoneNumber());
        companyNameEditText.setText(SessionData.getInstance().getCompanyName());
        faxNumberEditText.setText(SessionData.getInstance().getFaxNumber());
        licenseNumberEditText.setText(SessionData.getInstance().getLicenseNumber());
        emailAddressEditText.setText(SessionData.getInstance().getEmailAddress());
        address.setText(SessionData.getInstance().getAddress());

        usernameEditText.setEnabled(false);
        companyNameEditText.setEnabled(false);
        firstNameEditText.setEnabled(false);
        lastNameEditText.setEnabled(false);
        phoneNumberEditText.setEnabled(false);
        faxNumberEditText.setEnabled(false);
        licenseNumberEditText.setEnabled(false);
        emailAddressEditText.setEnabled(false);
        address.setEnabled(false);

    }

}
