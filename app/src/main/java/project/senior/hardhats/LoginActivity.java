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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import project.senior.hardhats.Documents.Invoice;

public class LoginActivity extends AppCompatActivity {
    TextView aboutTextView;
    EditText usernameEditText;
    EditText passwordEditText;
    Button buttonLogin;
    Button buttonRegister;
    Button buttonDebug;
    JSONObject returnedUsername;
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
        /*
        buttonDebug=(Button) findViewById(R.id.main_debug);

        buttonDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDebug(v);
            }
        });
        */
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

    private void onDebug(View v) {

        try {
            Invoice test = new Invoice("1");


            String invoicestring = test.createTxtString();
            BackgroundWorker sendInvoice = new BackgroundWorker();
            DataContainer dataContainer = new DataContainer();
            dataContainer.type="sendinvoice";
            dataContainer.dataPassedIn.add(invoicestring);
            dataContainer.phpVariableNames.add("invoicestring");
            dataContainer.dataPassedIn.add("adamdanielsen@gmail.com");
            dataContainer.phpVariableNames.add("email");
            sendInvoice.execute(dataContainer);



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        DataContainer dataContainer= new DataContainer();
        dataContainer.type=type;
        dataContainer.phpVariableNames.add("user_name");
        dataContainer.phpVariableNames.add("user_pass");
        dataContainer.dataPassedIn.add(username);
        dataContainer.dataPassedIn.add(password);
        BackgroundWorkerJSON backgroundWorker = new BackgroundWorkerJSON();
        try {
            returnedUsername = new JSONObject();
            returnedUsername = backgroundWorker.execute(dataContainer).get(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Toast.makeText(this, "Request Timed Out, Check Connection", Toast.LENGTH_SHORT).show();
        }
        String setusername= "";
        //if its null that means it didn't get a user in the php script and we should say that
        if (returnedUsername==null)
        {
            Toast.makeText(this, "Username/Password not found", Toast.LENGTH_SHORT).show();
            return;
        }
        //This is how you get a field back from the JSONobject
        try {
            setusername = returnedUsername.getString("Username");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(this, "Welcome "+setusername, Toast.LENGTH_SHORT).show();
        SessionData.getInstance().setUsername(setusername);
        Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
        menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menuIntent);

    }


}
