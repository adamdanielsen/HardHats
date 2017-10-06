package project.senior.hardhats;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            ArrayList<String> stateList = new ArrayList<String>(Arrays.asList("AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"));
            ArrayList<String> countryList = new ArrayList<String>(Arrays.asList("Afghanistan","Åland Islands","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antarctica","Antigua And Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia And Herzegovina","Botswana","Bouvet Island","Brazil","British Indian Ocean Territory","Brunei Darussalam","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Cocos (Keeling) Islands","Colombia","Comoros","Congo","Congo, The Democratic Republic Of The","Cook Islands","Costa Rica","Cote D'ivoire","Croatia","Cuba","Cyprus","Czechia","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands (Malvinas)","Faroe Islands","Fiji","Finland","France","French Guiana","French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guernsey","Guinea","Guinea-bissau","Guyana","Haiti","Heard Island And Mcdonald Islands","Holy See (Vatican City State)","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran, Islamic Republic Of","Iraq","Ireland","Isle Of Man","Israel","Italy","Jamaica","Japan","Jersey","Jordan","Kazakhstan","Kenya","Kiribati","Korea, Democratic People's Republic Of","Korea, Republic Of","Kuwait","Kyrgyzstan","Lao People's Democratic Republic","Latvia","Lebanon","Lesotho","Liberia","Libyan Arab Jamahiriya","Liechtenstein","Lithuania","Luxembourg","Macao","Macedonia, The Former Yugoslav Republic Of","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia, Federated States Of","Moldova, Republic Of","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Northern Mariana Islands","Norway","Oman","Pakistan","Palau","Palestinian Territory, Occupied","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russian Federation","Rwanda","Saint Helena","Saint Kitts And Nevis","Saint Lucia","Saint Pierre And Miquelon","Saint Vincent And The Grenadines","Samoa","San Marino","Sao Tome And Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Georgia And The South Sandwich Islands","Spain","Sri Lanka","Sudan","Suriname","Svalbard And Jan Mayen","Swaziland","Sweden","Switzerland","Syrian Arab Republic","Taiwan, Province Of China","Tajikistan","Tanzania, United Republic Of","Thailand","Timor-leste","Togo","Tokelau","Tonga","Trinidad And Tobago","Tunisia","Turkey","Turkmenistan","Turks And Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","United States Minor Outlying Islands","Uruguay","Uzbekistan","Vanuatu","Venezuela","Viet Nam","Virgin Islands, British","Virgin Islands, U.S.","Wallis And Futuna","Western Sahara","Yemen","Zambia","Zimbabwe"));
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
/*
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
*/
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

        String type= "register";
        String username= usernameEditText.getText().toString();
        String password= passwordEditText.getText().toString();
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        try {
            backgroundWorker.execute(type,username,password).get();

            if (SessionData.getInstance().getLastStringResult().equals("BAD")) {
                Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (SessionData.getInstance().getLastStringResult().equals("GOOD"))
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
