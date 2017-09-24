package project.senior.hardhats;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    TextView aboutTextView;
    EditText usernameEditText;
    EditText passwordEditText;
    Button buttonLogin;
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SessionData.getInstance().eraseUsername();

        getWindow().getDecorView().setBackgroundColor(Color.CYAN);
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

            backgroundWorker.execute(type,username,password).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Toast.makeText(this, "Request timed out", Toast.LENGTH_SHORT).show();;
        }


        if (SessionData.getInstance().getUsername().equals(""))
        {
            Toast.makeText(this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!((SessionData.getInstance().getUsername().equals("BAD"))&&(SessionData.getInstance().getUsername().equals(""))))
        {
            //TODO this toast should be the intent for the next activity
            Toast.makeText(this, "Success: "+SessionData.getInstance().getUsername(), Toast.LENGTH_SHORT).show();

        }

        if (SessionData.getInstance().getUsername().equals("BAD"))
        {

            Toast.makeText(this, "Username/Password not found", Toast.LENGTH_SHORT).show();

        }

    }
}
