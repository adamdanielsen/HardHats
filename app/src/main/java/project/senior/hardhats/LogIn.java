package project.senior.hardhats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    String testString;
    EditText usernameEditText;
    EditText passwordEditText;
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        usernameEditText= (EditText) findViewById(R.id.login_UserNameEditText);
        passwordEditText= (EditText) findViewById(R.id.login_PasswordEditText);
        buttonLogin = (Button) findViewById(R.id.login_LogInButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLogin(v);
            }
        });
    }

    public void OnLogin(View view)
    {
        String username =usernameEditText.getText().toString();
        String password =passwordEditText.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute (type,username,password);
    }
}
