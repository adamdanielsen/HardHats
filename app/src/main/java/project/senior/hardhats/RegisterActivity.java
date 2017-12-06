package project.senior.hardhats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.concurrent.ExecutionException;
public class RegisterActivity extends AppCompatActivity {

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

        TextInputLayout usernameTextInputLayout;
        TextInputLayout passwordTextInputLayout;
        TextInputLayout confirmpasswordTextInputLayout;
        TextInputLayout firstNameTextInputLayout;
        TextInputLayout lastNameTextInputLayout;
        TextInputLayout phoneNumberTextInputLayout;
        TextInputLayout faxNumberTextInputLayout;
        TextInputLayout licenseNumberTextInputLayout;
        TextInputLayout emailAddressTextInputLayout;
        TextInputLayout companyNameTextInputLayout;

        Place address;




        Button continueButton;
        Button cancelButton;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            usernameEditText =(EditText) findViewById(R.id.register_UserNameEditText);
            passwordEditText = (EditText)findViewById(R.id.register_PasswordEditText);
            confirmpasswordEditText =(EditText) findViewById(R.id.register_ConfirmPasswordEditText);
            firstNameEditText = (EditText) findViewById(R.id.user_firstName_editText);
            lastNameEditText = (EditText) findViewById(R.id.user_lastName_editText);
            phoneNumberEditText = (EditText) findViewById(R.id.user_phoneNumber_editText);
            faxNumberEditText = (EditText) findViewById(R.id.user_faxNumber_editText);
            licenseNumberEditText = (EditText) findViewById(R.id.user_licenseNumber_editText);
            emailAddressEditText = (EditText) findViewById(R.id.user_emailAddress_editText);
            companyNameEditText = (EditText) findViewById(R.id.user_CompanyName_editText);

            usernameTextInputLayout =(TextInputLayout) findViewById(R.id.register_usernameTextInputLayout);
            passwordTextInputLayout = (TextInputLayout)findViewById(R.id.register_passwordTextInputLayout);
            confirmpasswordTextInputLayout =(TextInputLayout) findViewById(R.id.register_confirmPasswordTextInputLayout);
            firstNameTextInputLayout = (TextInputLayout) findViewById(R.id.register_firstNameTextInputLayout);
            lastNameTextInputLayout = (TextInputLayout) findViewById(R.id.register_lastNameTextInputLayout);
            phoneNumberTextInputLayout = (TextInputLayout) findViewById(R.id.register_phoneNumberTextInputLayout);
            faxNumberTextInputLayout = (TextInputLayout) findViewById(R.id.register_faxNumberTextInputLayout);
            licenseNumberTextInputLayout = (TextInputLayout) findViewById(R.id.register_licenseNumberTextInputLayout);
            emailAddressTextInputLayout = (TextInputLayout) findViewById(R.id.register_emailAddressTextInputLayout);
            companyNameTextInputLayout = (TextInputLayout) findViewById(R.id.register_companyNameTextInputLayout);

            continueButton =(Button) findViewById(R.id.register_ContinueButton);
            cancelButton=(Button) findViewById(R.id.register_CancelButton);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Continue();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cancel();
                }
            });


        }



    private void Continue() {

            if(!Validate())
            {

                return;
            }

        String username= usernameEditText.getText().toString();
        String password= passwordEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String faxNumber = faxNumberEditText.getText().toString();
        String licenseNumber = licenseNumberEditText.getText().toString();
        String emailAddress = emailAddressEditText.getText().toString();
        String companyName = companyNameEditText.getText().toString();

        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                address = PlaceAutocomplete.getPlace(this, data);
            }
            if (resultCode == RESULT_CANCELED) {
                address=null;
            }


        if (address==null)
        {
            Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();

        }

        Toast.makeText(this, address.getAddress(), Toast.LENGTH_SHORT).show();





        }







    private void Cancel()
    {
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(registerIntent);
    }
    private boolean Validate() {

        DataContainer dataContainer = new DataContainer();
        dataContainer.type="checkusername";
        dataContainer.phpVariableNames.add("user_name");
        dataContainer.dataPassedIn.add(usernameEditText.getText().toString());
        BackgroundWorker backgroundWorker = new BackgroundWorker();

        if (usernameEditText.getText().toString().trim().isEmpty()) {
            usernameTextInputLayout.setError("Please fill out your username");
            requestFocus(usernameEditText);
         //   usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            usernameTextInputLayout.setErrorEnabled(false);
        }
        if ((usernameEditText.getText().toString().trim().length())<getResources().getInteger(R.integer.MINUSERNAMELENGTH)) {
            usernameTextInputLayout.setError("Your Username is too short");
            requestFocus(usernameEditText);
            //   usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            usernameTextInputLayout.setErrorEnabled(false);
        }



        try {
            String registrationResult = backgroundWorker.execute(dataContainer).get();
            switch (registrationResult) {
                case "BAD":
                    usernameTextInputLayout.setError("Username Taken");
                    requestFocus(usernameEditText);
                    //   usernameTextInputLayout.setErrorEnabled(false);
                    passwordTextInputLayout.setErrorEnabled(false);
                    confirmpasswordTextInputLayout.setErrorEnabled(false);
                    firstNameTextInputLayout.setErrorEnabled(false);
                    lastNameTextInputLayout.setErrorEnabled(false);
                    phoneNumberTextInputLayout.setErrorEnabled(false);
                    faxNumberTextInputLayout.setErrorEnabled(false);
                    licenseNumberTextInputLayout.setErrorEnabled(false);
                    emailAddressTextInputLayout.setErrorEnabled(false);
                    companyNameTextInputLayout.setErrorEnabled(false);
                    return false;
                case "GOOD":
                    usernameTextInputLayout.setErrorEnabled(false);
                    break;
                default:
                    usernameTextInputLayout.setError("Connection error");
                    requestFocus(usernameEditText);
                    //   usernameTextInputLayout.setErrorEnabled(false);
                    passwordTextInputLayout.setErrorEnabled(false);
                    confirmpasswordTextInputLayout.setErrorEnabled(false);
                    firstNameTextInputLayout.setErrorEnabled(false);
                    lastNameTextInputLayout.setErrorEnabled(false);
                    phoneNumberTextInputLayout.setErrorEnabled(false);
                    faxNumberTextInputLayout.setErrorEnabled(false);
                    licenseNumberTextInputLayout.setErrorEnabled(false);
                    emailAddressTextInputLayout.setErrorEnabled(false);
                    companyNameTextInputLayout.setErrorEnabled(false);
                    return false;
            }



        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            usernameTextInputLayout.setError("Connection error");
            requestFocus(usernameEditText);
            //   usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }

        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordTextInputLayout.setError("Please fill out your password");
            requestFocus(passwordEditText);
            usernameTextInputLayout.setErrorEnabled(false);
        //    passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            passwordTextInputLayout.setErrorEnabled(false);
        }


        if (passwordEditText.getText().toString().trim().length()<getResources().getInteger(R.integer.MINPASSWORDLENGTH)) {
            passwordTextInputLayout.setError("Your Password is too short");
            requestFocus(usernameEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            //passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            passwordTextInputLayout.setErrorEnabled(false);
        }




        if (confirmpasswordEditText.getText().toString().trim().isEmpty()) {
            confirmpasswordTextInputLayout.setError("Please confirm your password");
            requestFocus(confirmpasswordEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            //confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else if (confirmpasswordEditText.getText().toString().equals(passwordEditText.toString()))
        {
            confirmpasswordTextInputLayout.setError("Passwords don't match");
            requestFocus(passwordEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            //confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
        }


        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            firstNameTextInputLayout.setError("Please fill out your First Name");
            requestFocus(firstNameEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            //firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            firstNameTextInputLayout.setErrorEnabled(false);
        }


        if (lastNameEditText.getText().toString().trim().isEmpty()) {
            lastNameTextInputLayout.setError("Please fill out your Last Name");
            requestFocus(lastNameEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            //lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            lastNameTextInputLayout.setErrorEnabled(false);
        }


        if (phoneNumberEditText.getText().toString().trim().isEmpty()) {
            phoneNumberTextInputLayout.setError("Please fill out your Phone Number");
            requestFocus(phoneNumberEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            //phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            phoneNumberTextInputLayout.setErrorEnabled(false);
        }

        if (emailAddressEditText.getText().toString().trim().isEmpty()) {
            emailAddressTextInputLayout.setError("Please fill out your Email Address");
            requestFocus(emailAddressEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            //emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }


        if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddressEditText.getText().toString().trim()).matches())) {
            emailAddressTextInputLayout.setError("Please enter a valid Email");
            requestFocus(emailAddressEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            //emailAddressTextInputLayout.setErrorEnabled(false);
            companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }

        if (companyNameEditText.getText().toString().trim().isEmpty()) {
            companyNameTextInputLayout.setError("Please fill out your Company Name");
            requestFocus(companyNameEditText);
            usernameTextInputLayout.setErrorEnabled(false);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            licenseNumberTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            //companyNameTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }
        return true;
    }




    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
