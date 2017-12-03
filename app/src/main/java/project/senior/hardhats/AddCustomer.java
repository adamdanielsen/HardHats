package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class AddCustomer extends AppCompatActivity {


    EditText firstName;
    EditText lastName;
    EditText company;
    EditText emailAddress;
    EditText phoneNumber;
    EditText faxNumber;
    EditText streetName;
    EditText zipCode;
    EditText city;
    Spinner state;

    String customer_state;

    Button save, cancel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        firstName = (EditText) findViewById(R.id.customer_first_editText);
        lastName = (EditText) findViewById(R.id.customer_last_editText);
        company = (EditText) findViewById(R.id.customer_company_editText);
        emailAddress = (EditText) findViewById(R.id.customer_email_address_editText);
        phoneNumber = (EditText) findViewById(R.id.customer_phone_editText);
        faxNumber = (EditText) findViewById(R.id.customer_fax_editText);
        streetName = (EditText) findViewById(R.id.customer_street_address_editText);
        zipCode = (EditText) findViewById(R.id.customer_zip_code_editText);
        city = (EditText) findViewById(R.id.customer_city_editText);
        state = (Spinner) findViewById(R.id.customer_state_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(state_listener);

        save = (Button) findViewById(R.id.save_customer_button);
        cancel = (Button) findViewById(R.id.cancel_customer_button);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText().toString().length() == 0)
                {
                    Toast.makeText(AddCustomer.this, "On this planet we refer to people by their first name. We need your customers first name.", Toast.LENGTH_LONG ).show();
                }else if(lastName.getText().toString().length() == 0){
                    Toast.makeText(AddCustomer.this, "Unless your customer is a pet gold fish, we're going to need their last name.", Toast.LENGTH_LONG ).show();
                }
                else if(!emailAddress.getText().toString().matches("\\S+@\\S+\\.\\S+")) {
                    Toast.makeText(AddCustomer.this, "ye- no, that is definitely not what an email looks like. Try again.!", Toast.LENGTH_LONG).show();
                }
                else {
                    addCustomerFunction();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                customer_state = (String) state.getItemAtPosition(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void addCustomerFunction(){
        String customer_userid = SessionData.getInstance().getUserID();
        String customer_first = firstName.getText().toString().replaceAll("\'","");
        String customer_last = lastName.getText().toString().replaceAll("\'","");
        String customer_company = company.getText().toString().replaceAll("\'","");
        String customer_email = emailAddress.getText().toString().replaceAll("\'","");
        String customer_phoneNumber = phoneNumber.getText().toString().replaceAll("\'","");
        String customer_faxNumber = faxNumber.getText().toString().replaceAll("\'","");
        String customer_street = streetName.getText().toString().replaceAll("\'","");
        String customer_Zip = zipCode.getText().toString().replaceAll("\'","");
        String customer_city = city.getText().toString().replaceAll("\'","");
        String customer_state = state.getSelectedItem().toString().replaceAll("\'","");


        if (customer_state.length() == 0 || customer_state.length() == NULL) {
            customer_state = "AL";
        }
        if(customer_email.endsWith(" ")){
           customer_email = customer_email.trim();
        }

        DataContainer dataContainer = new DataContainer();

        dataContainer.type = "addcustomer";

        dataContainer.phpVariableNames.add("userid");
        dataContainer.phpVariableNames.add("firstname");
        dataContainer.phpVariableNames.add("lastname");
        dataContainer.phpVariableNames.add("phonenumber");
        dataContainer.phpVariableNames.add("faxnumber");
        dataContainer.phpVariableNames.add("emailaddress");
        dataContainer.phpVariableNames.add("companyname");
        dataContainer.phpVariableNames.add("street");
        dataContainer.phpVariableNames.add("city");
        dataContainer.phpVariableNames.add("zipcode");
        dataContainer.phpVariableNames.add("state");


        dataContainer.dataPassedIn.add(customer_userid);
        dataContainer.dataPassedIn.add(customer_first);
        dataContainer.dataPassedIn.add(customer_last);
        dataContainer.dataPassedIn.add(customer_phoneNumber);
        dataContainer.dataPassedIn.add(customer_faxNumber);
        dataContainer.dataPassedIn.add(customer_email);
        dataContainer.dataPassedIn.add(customer_company);
        dataContainer.dataPassedIn.add(customer_street);
        dataContainer.dataPassedIn.add(customer_city);
        dataContainer.dataPassedIn.add(customer_Zip);
        dataContainer.dataPassedIn.add(customer_state);


        BackgroundWorker database = new BackgroundWorker();
        database.execute(dataContainer);
        Toast.makeText(AddCustomer.this,"Congrats! Your customer has been added", Toast.LENGTH_LONG).show();
        finish();
    }
}