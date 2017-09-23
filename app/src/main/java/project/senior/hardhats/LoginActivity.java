package project.senior.hardhats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button buttonLogin;
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText= (EditText) findViewById(R.id.login_UserNameEditText);
        passwordEditText= (EditText) findViewById(R.id.login_PasswordEditText);
        buttonLogin = (Button) findViewById(R.id.login_LoginButton);
        buttonRegister= (Button) findViewById(R.id.login_RegisterButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLogin(v);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister(v);
            }


        });
    }

    private void onRegister(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    public void OnLogin(View view)
    {
        String username =usernameEditText.getText().toString();
        String password =passwordEditText.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        try {

            //very important to use ".get" after an execute so it waits until its done
            backgroundWorker.execute(type,username,password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (!SessionData.getInstance().getUsername().equals("BAD"))
        {
            //TODO this toast should be the intent for the next activity
            Toast.makeText(this, SessionData.getInstance().getUsername(), Toast.LENGTH_SHORT).show();

        }

        if (SessionData.getInstance().getUsername().equals("BAD"))
        {

            Toast.makeText(this, "Username/Password not found", Toast.LENGTH_SHORT).show();

        }

    }
}
