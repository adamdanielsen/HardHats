package project.senior.hardhats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCustomer extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText emailAddress;
    EditText phoneNumber;
    EditText houseNumber;
    EditText streetName;
    EditText zipCode;
    EditText state;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        firstName = (EditText) findViewById(R.id.customer_first_editText);
        lastName = (EditText) findViewById(R.id.customer_last_editText);
        emailAddress = (EditText) findViewById(R.id.customer_email_address_editText);
        phoneNumber = (EditText) findViewById(R.id.customer_phone_editText);
        houseNumber = (EditText) findViewById(R.id.customer_house_number_editText);
        streetName = (EditText) findViewById(R.id.customer_street_address_editText);
        zipCode = (EditText) findViewById(R.id.customer_zip_code_editText);
        state = (EditText) findViewById(R.id.customer_state_editText);

        save = (Button) findViewById(R.id.save_customer_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customer_first  = firstName.getText().toString();
                String customer_last = lastName.getText().toString();
                String customer_email = emailAddress.getText().toString();
                String customer_phoneNumber = phoneNumber.getText().toString();
                String customer_houseNumber = houseNumber.getText().toString();
                String customer_street = streetName.getText().toString();
                String customer_Zip = zipCode.getText().toString();
                String customer_State = state.getText().toString();


            }
        });






    }
}
