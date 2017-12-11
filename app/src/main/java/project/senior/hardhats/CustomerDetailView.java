package project.senior.hardhats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CustomerDetailView extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText streetAddress;
    EditText phoneNumber;
    EditText faxNumber;
    EditText emailAddress;
    EditText city;
    EditText state;
    EditText zipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail_view);
        firstName = (EditText) findViewById(R.id.customer_Preview_firstName_editText);
        lastName = (EditText) findViewById(R.id.customer_Preview_lastName_editText);
        streetAddress = (EditText) findViewById(R.id.customer_Preview_streetAddress_editText);
        phoneNumber = (EditText) findViewById(R.id.customer_Preview_phoneNumber_editText);
        faxNumber = (EditText) findViewById(R.id.customer_Preview_faxNumber_editText);
        emailAddress = (EditText) findViewById(R.id.customer_Preview_emailAddress_editText);
        city = (EditText) findViewById(R.id.customer_Preview_city_editText);
        state = (EditText) findViewById(R.id.customer_Preview_state_editText);
        zipCode = (EditText) findViewById(R.id.customer_Preview_zipCode_editText);

    }
}
