package project.senior.hardhats;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    TextView aboutTextView;
    EditText usernameEditText;
    EditText passwordEditText;
    Button buttonLogin;
    Button buttonRegister;
    String returnedUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SessionData.getInstance().eraseUsername();
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        aboutTextView=(TextView) findViewById(R.id.login_AboutTextView);
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
                onRegister();
            }


        });
        aboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAbout();
            }
        });
        SessionData.Reset();


    }



    private void onAbout() {
        Intent aboutIntent = new Intent(LoginActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    private void onRegister() {
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
            returnedUsername = backgroundWorker.execute(type, username, password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (returnedUsername.equals(""))
        {
            Toast.makeText(this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!((returnedUsername.equals("BAD"))||(returnedUsername.equals(""))))
        {

            Toast.makeText(this, "Welcome "+returnedUsername, Toast.LENGTH_SHORT).show();
            SessionData.getInstance().setUsername(returnedUsername);
            Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
            menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(menuIntent);
        }
        if (returnedUsername.equals("BAD"))
        {
            Toast.makeText(this, "Username/Password not found", Toast.LENGTH_SHORT).show();
        }

    }

}
