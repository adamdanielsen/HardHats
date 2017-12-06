package project.senior.hardhats;

import android.app.Activity;
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

        String username;
        String password;
        String firstName;
        String lastName;
        String phoneNumber;
        String faxNumber;
        String licenseNumber;
        String emailAddress;
        String companyName;
        String addressToUse;



    Place address;
        String backupAddress;
        boolean googleError;

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
            googleError=false;

        }



    private void Continue() {

            if(!Validate())
            {

                return;
            }
        backupAddress=null;
         username= usernameEditText.getText().toString();
         password= passwordEditText.getText().toString();
         firstName = firstNameEditText.getText().toString();
         lastName = lastNameEditText.getText().toString();
         phoneNumber = phoneNumberEditText.getText().toString();
         faxNumber = faxNumberEditText.getText().toString();
         licenseNumber = licenseNumberEditText.getText().toString();
         emailAddress = emailAddressEditText.getText().toString();
         companyName = companyNameEditText.getText().toString();

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
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            googleError=true;
            Toast.makeText(this, "Error starting Google Places, launcing backup", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, BackupAddressHandlerActivity.class);
            startActivityForResult(i, 1);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



            if (!googleError)
        {
            if (resultCode == RESULT_OK) {
            address = PlaceAutocomplete.getPlace(this, data);
            backupAddress=null;
            }

            if (resultCode == RESULT_CANCELED) {
                address = null;
            }

        if (address == null) {

            Toast.makeText(this, "No Address Selected", Toast.LENGTH_SHORT).show();
            return;
            }

            addressToUse = address.getAddress().toString();
            hitDatabase();
            return;
        }

                if(resultCode == Activity.RESULT_OK){

                    backupAddress=data.getStringExtra("address");
                    address=null;
                }

                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Please enter an Address", Toast.LENGTH_SHORT).show();
                    return;

                }
                addressToUse=backupAddress;
        }

    private void hitDatabase() {

        BackgroundWorker createUser = new BackgroundWorker();
        DataContainer dataContainer = new DataContainer();
        dataContainer.type="register";
        dataContainer.dataPassedIn.add(username);
        dataContainer.dataPassedIn.add(password);
        dataContainer.dataPassedIn.add(firstName);
        dataContainer.dataPassedIn.add(lastName);
        dataContainer.dataPassedIn.add(phoneNumber);
        dataContainer.dataPassedIn.add(faxNumber);
        dataContainer.dataPassedIn.add(licenseNumber);
        dataContainer.dataPassedIn.add(emailAddress);
        dataContainer.dataPassedIn.add(companyName);
        dataContainer.dataPassedIn.add(addressToUse);
        dataContainer.phpVariableNames.add("user_name");
        dataContainer.phpVariableNames.add("user_pass");
        dataContainer.phpVariableNames.add("firstname");
        dataContainer.phpVariableNames.add("lastname");
        dataContainer.phpVariableNames.add("phonenumber");
        dataContainer.phpVariableNames.add("faxnumber");
        dataContainer.phpVariableNames.add("licensenumber");
        dataContainer.phpVariableNames.add("emailaddress");
        dataContainer.phpVariableNames.add("companyname");
        dataContainer.phpVariableNames.add("address");
        createUser.execute(dataContainer);
        Toast.makeText(this, "User Created!", Toast.LENGTH_SHORT).show();
        finish();

    }


    private void Cancel()
    {
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(registerIntent);
    }
    private boolean Validate() {

        boolean validationFlag = true;
        validationFlag=true;
        DataContainer dataContainer = new DataContainer();
        dataContainer.type="checkusername";
        dataContainer.phpVariableNames.add("user_name");
        dataContainer.dataPassedIn.add(usernameEditText.getText().toString());
        BackgroundWorker backgroundWorker = new BackgroundWorker();

        if (usernameEditText.getText().toString().trim().isEmpty()) {
            usernameTextInputLayout.setError("Please fill out your username");
            requestFocus(usernameEditText);
            validationFlag=false;
        }
        else {
            usernameTextInputLayout.setErrorEnabled(false);
        }
        if ((usernameEditText.getText().toString().trim().length())<getResources().getInteger(R.integer.MINUSERNAMELENGTH)) {
            usernameTextInputLayout.setError("Your Username is too short");
            requestFocus(usernameEditText);
            validationFlag=false;
        }
        else {
            usernameTextInputLayout.setErrorEnabled(false);
        }

        try {
            String registrationResult = backgroundWorker.execute(dataContainer).get();

            if (registrationResult==null)
            {
                Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show();
                return false;
            }

            switch (registrationResult) {


                    case "BAD":
                    usernameTextInputLayout.setError("Username Taken");
                    requestFocus(usernameEditText);
                    validationFlag=false;
                    break;
                    case "GOOD":
                    usernameTextInputLayout.setErrorEnabled(false);
                    break;
                default:
                    usernameTextInputLayout.setError("Connection error");
                    requestFocus(usernameEditText);
                    validationFlag=false;
            }



        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            usernameTextInputLayout.setError("Connection error");
            requestFocus(usernameEditText);
            validationFlag=false;
        }

        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordTextInputLayout.setError("Please fill out your password");
            requestFocus(passwordEditText);
            validationFlag=false;
        }
        else {
            passwordTextInputLayout.setErrorEnabled(false);
        }


        if (passwordEditText.getText().toString().trim().length()<getResources().getInteger(R.integer.MINPASSWORDLENGTH)) {
            passwordTextInputLayout.setError("Your Password is too short");
            requestFocus(usernameEditText);
            validationFlag=false;
        }
        else {
            passwordTextInputLayout.setErrorEnabled(false);
        }




        if (confirmpasswordEditText.getText().toString().trim().isEmpty()) {
            confirmpasswordTextInputLayout.setError("Please confirm your password");
            requestFocus(confirmpasswordEditText);
            validationFlag=false;
        }
        else if (confirmpasswordEditText.getText().toString().equals(passwordEditText.toString()))
        {
            confirmpasswordTextInputLayout.setError("Passwords don't match");
            requestFocus(passwordEditText);
            validationFlag=false;
        }
        else {
            passwordTextInputLayout.setErrorEnabled(false);
            confirmpasswordTextInputLayout.setErrorEnabled(false);
        }


        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            firstNameTextInputLayout.setError("Please fill out your First Name");
            requestFocus(firstNameEditText);
            validationFlag=false;
        }
        else {
            firstNameTextInputLayout.setErrorEnabled(false);
        }


        if (lastNameEditText.getText().toString().trim().isEmpty()) {
            lastNameTextInputLayout.setError("Please fill out your Last Name");
            requestFocus(lastNameEditText);
            validationFlag=false;
        }
        else {
            lastNameTextInputLayout.setErrorEnabled(false);
        }


        if (phoneNumberEditText.getText().toString().trim().isEmpty()) {
            phoneNumberTextInputLayout.setError("Please fill out your Phone Number");
            requestFocus(phoneNumberEditText);
            validationFlag=false;
        }
        else {
            phoneNumberTextInputLayout.setErrorEnabled(false);
        }

        if (emailAddressEditText.getText().toString().trim().isEmpty()) {
            emailAddressTextInputLayout.setError("Please fill out your Email Address");
            requestFocus(emailAddressEditText);
            validationFlag=false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }


        if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddressEditText.getText().toString().trim()).matches())) {
            emailAddressTextInputLayout.setError("Please enter a valid Email");
            requestFocus(emailAddressEditText);
            validationFlag=false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }

        if (companyNameEditText.getText().toString().trim().isEmpty()) {
            companyNameTextInputLayout.setError("Please fill out your Company Name");
            requestFocus(companyNameEditText);
            validationFlag=false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }
        return validationFlag;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
