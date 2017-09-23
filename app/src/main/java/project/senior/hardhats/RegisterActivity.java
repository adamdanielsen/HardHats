package project.senior.hardhats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by theev on 9/21/2017.
 */

public class RegisterActivity extends AppCompatActivity {
        EditText usernameEditText;
        EditText passwordEditText;
        EditText confirmpasswordEditText;
        Button registerButton;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            usernameEditText =(EditText) findViewById(R.id.register_UserNameEditText);
            passwordEditText = (EditText)findViewById(R.id.register_PasswordEditText);
            confirmpasswordEditText =(EditText) findViewById(R.id.register_ConfirmPasswordEditText);
            registerButton =(Button) findViewById(R.id.register_RegisterButton);
            registerButton.setEnabled(false);
            registerButton.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View v) {
                    Register();
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

        if(password.length()>R.integer.MAXPASSWORDLENGTH)
        {
            registerButton.setEnabled(false);
            return;
        }

        if(password.length()<getResources().getInteger(R.integer.MINPASSWORDLENGTH))
        {
            registerButton.setEnabled(false);
            return;
        }

        if(username.length()>getResources().getInteger(R.integer.MAXUSERNAMELENGTH))
        {
            registerButton.setEnabled(false);
            return;
        }
        if(username.length()<getResources().getInteger(R.integer.MINUSERNAMELENGTH))
        {
            registerButton.setEnabled(false);
            return;
        }
        if(!(password.equals(confirmPassword)))
        {
            registerButton.setEnabled(false);
            return;
        }

        if((password.length()<=getResources().getInteger(R.integer.MAXPASSWORDLENGTH))&&(password.length()>=getResources().getInteger(R.integer.MINPASSWORDLENGTH))&&(username.length()<=getResources().getInteger(R.integer.MAXUSERNAMELENGTH))&&(username.length()>=getResources().getInteger(R.integer.MINUSERNAMELENGTH))&&(password.equals(confirmPassword)))
        {
            registerButton.setEnabled(true);
            return;
        }

    }

    private void Register() {

        //TODO this should pop up if the script returns some undetermined variable and if not then exit this screen because it succeeded
        Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();


    }


}
