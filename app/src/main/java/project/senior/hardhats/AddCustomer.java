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

import java.util.concurrent.ExecutionException;

public class AddCustomer extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText emailAddress;
    EditText phoneNumber;
    EditText houseNumber;
    EditText streetName;
    EditText zipCode;
    Spinner state;
    String customer_State;

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
        state = (Spinner) findViewById(R.id.customer_state_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(state_listener);

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
                if(customer_State.length() == 0) {
                    Toast.makeText(AddCustomer.this, "No State Selected", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddCustomer.this, "State Selected: " + customer_State, Toast.LENGTH_LONG).show();
                }
                //String customer_State = state.getText().toString();
                DataContainer data = new DataContainer();
                data.type="addcustomer";
                data.phpVariableNames.add("customer_first");
                data.dataPassedIn.add(customer_first);
                data.phpVariableNames.add("customer_last");
                data.dataPassedIn.add(customer_last);
                data.phpVariableNames.add("customer_email");
                data.dataPassedIn.add(customer_email);
                data.phpVariableNames.add("customer_phoneNumber");
                data.dataPassedIn.add(customer_phoneNumber);
                data.phpVariableNames.add("customer_street");
                data.dataPassedIn.add(customer_street);
                data.phpVariableNames.add("customer_Zip");
                data.dataPassedIn.add(customer_Zip);

                BackgroundWorker database = new BackgroundWorker();
                try {
                    String result = database.execute(data).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position > 0){
                customer_State = (String) state.getItemAtPosition(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
