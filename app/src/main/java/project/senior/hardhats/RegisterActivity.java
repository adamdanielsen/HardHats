package project.senior.hardhats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
public class RegisterActivity extends AppCompatActivity {
        EditText usernameEditText;
        EditText passwordEditText;
        EditText confirmpasswordEditText;
        Button registerButton;
        Button cancelButton;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            ArrayList<String> stateList = new ArrayList<>(Arrays.asList("AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"));
            usernameEditText =(EditText) findViewById(R.id.register_UserNameEditText);
            passwordEditText = (EditText)findViewById(R.id.register_PasswordEditText);
            confirmpasswordEditText =(EditText) findViewById(R.id.register_ConfirmPasswordEditText);
            registerButton =(Button) findViewById(R.id.register_RegisterButton);
            cancelButton=(Button) findViewById(R.id.register_CancelButton);
            registerButton.setEnabled(false);
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

        DataContainer dataContainer = new DataContainer();
        dataContainer.type="register";
        dataContainer.phpVariableNames.add("user_name");
        dataContainer.phpVariableNames.add("user_pass");
        dataContainer.dataPassedIn.add(username);
        dataContainer.dataPassedIn.add(password);

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
                registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(registerIntent);
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

}
