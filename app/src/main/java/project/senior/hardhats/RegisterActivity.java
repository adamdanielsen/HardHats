package project.senior.hardhats;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        EditText streetAddressEditText;
        EditText cityEditText;
        EditText zipCodeEditText;
        Spinner state;
        String user_state;
        Button registerButton;
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
            streetAddressEditText = (EditText) findViewById(R.id.user_streetAddress_editText);
            cityEditText = (EditText) findViewById(R.id.user_city_editText);
            zipCodeEditText = (EditText) findViewById(R.id.user_zipCode_editText);
            registerButton =(Button) findViewById(R.id.register_RegisterButton);
            cancelButton=(Button) findViewById(R.id.register_CancelButton);
            registerButton.setEnabled(false);
            state = (Spinner) findViewById(R.id.user_state_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            state.setAdapter(adapter);
            state.setOnItemSelectedListener(state_listener);
            registerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Register();
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cancel();
                }
            });
            usernameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Refresh();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Refresh();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Refresh();
                }
            });

            passwordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Refresh();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Refresh();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Refresh();
                }
            });
            confirmpasswordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Refresh();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Refresh();

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Refresh();
                }
            });
        }



    private void Refresh(){

        String password=passwordEditText.getText().toString();
        String username=usernameEditText.getText().toString();
        String confirmPassword=confirmpasswordEditText.getText().toString();

        if((password.length()<=getResources().getInteger(R.integer.MAXPASSWORDLENGTH))&&(password.length()>=getResources().getInteger(R.integer.MINPASSWORDLENGTH))&&(username.length()<=getResources().getInteger(R.integer.MAXUSERNAMELENGTH))&&(username.length()>=getResources().getInteger(R.integer.MINUSERNAMELENGTH))&&(password.equals(confirmPassword)))
        {
            registerButton.setEnabled(true);
        }

        else
        {
            registerButton.setEnabled(false);
        }

    }

    private void Register() {

        String registrationResult;
        String username= usernameEditText.getText().toString();
        String password= passwordEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String faxNumber = faxNumberEditText.getText().toString();
        String licenseNumber = licenseNumberEditText.getText().toString();
        String emailAddress = emailAddressEditText.getText().toString();
        String companyName = companyNameEditText.getText().toString();
        String streetAddress = streetAddressEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();
        String selectedState = state.getSelectedItem().toString();


        DataContainer dataContainer = new DataContainer();
        dataContainer.type="register";
        dataContainer.phpVariableNames.add("user_name");
        dataContainer.phpVariableNames.add("user_pass");
        dataContainer.phpVariableNames.add("firstname");
        dataContainer.phpVariableNames.add("lastname");
        dataContainer.phpVariableNames.add("phonenumber");
        dataContainer.phpVariableNames.add("faxnumber");
        dataContainer.phpVariableNames.add("emailaddress");
        dataContainer.phpVariableNames.add("licensenumber");
        dataContainer.phpVariableNames.add("companyname");
        dataContainer.phpVariableNames.add("street");
        dataContainer.phpVariableNames.add("city");
        dataContainer.phpVariableNames.add("zipcode");
        dataContainer.phpVariableNames.add("state");

        dataContainer.dataPassedIn.add(username);
        dataContainer.dataPassedIn.add(password);
        dataContainer.dataPassedIn.add(firstName);
        dataContainer.dataPassedIn.add(lastName);
        dataContainer.dataPassedIn.add(phoneNumber);
        dataContainer.dataPassedIn.add(faxNumber);
        dataContainer.dataPassedIn.add(emailAddress);
        dataContainer.dataPassedIn.add(licenseNumber);
        dataContainer.dataPassedIn.add(companyName);
        dataContainer.dataPassedIn.add(streetAddress);
        dataContainer.dataPassedIn.add(city);
        dataContainer.dataPassedIn.add(zipCode);
        dataContainer.dataPassedIn.add(selectedState);

        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try {
           registrationResult = backgroundWorker.execute(dataContainer).get();
            if (registrationResult.equals("BAD")) {
                Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (registrationResult.equals("GOOD"))
            {
                Toast.makeText(this, "Username created!", Toast.LENGTH_SHORT).show();
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                //how not to add something to the backstack below
                AlertDialog.Builder a = new AlertDialog.Builder(this);
                a.setTitle("Returned info");
                a.setMessage(registrationResult);
                a.show();


                //registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(registerIntent);
            }
            else
            {
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private void Cancel()
    {
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(registerIntent);
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
