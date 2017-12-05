package project.senior.hardhats;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class AddCustomer extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText companyEditText;
    EditText emailAddressEditText;
    EditText phoneNumberEditText;
    EditText faxNumberEditText;
    EditText streetNameEditText;
    EditText zipCodeEditText;
    EditText cityEditText;

    TextInputLayout firstNameTextInputLayout;
    TextInputLayout lastNameTextInputLayout;
    TextInputLayout companyTextInputLayout;
    TextInputLayout emailAddressTextInputLayout;
    TextInputLayout phoneNumberTextInputLayout;
    TextInputLayout faxNumberTextInputLayout;
    TextInputLayout streetNameTextInputLayout;
    TextInputLayout zipCodeTextInputLayout;
    TextInputLayout cityTextInputLayout;





    Spinner state;

    String customer_state;

    Button save, cancel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        firstNameEditText = (EditText) findViewById(R.id.customer_first_editText);
        lastNameEditText = (EditText) findViewById(R.id.customer_last_editText);
        companyEditText = (EditText) findViewById(R.id.customer_company_editText);
        emailAddressEditText = (EditText) findViewById(R.id.customer_email_address_editText);
        phoneNumberEditText = (EditText) findViewById(R.id.customer_phone_editText);
        faxNumberEditText = (EditText) findViewById(R.id.customer_fax_editText);
        streetNameEditText = (EditText) findViewById(R.id.customer_street_address_editText);
        zipCodeEditText = (EditText) findViewById(R.id.customer_zip_code_editText);
        cityEditText = (EditText) findViewById(R.id.customer_city_editText);
        firstNameTextInputLayout = (TextInputLayout) findViewById(R.id.customer_first_textInputLayout);
        lastNameTextInputLayout = (TextInputLayout) findViewById(R.id.customer_last_textInputLayout);
        companyTextInputLayout = (TextInputLayout) findViewById(R.id.customer_company_textInputLayout);
        emailAddressTextInputLayout = (TextInputLayout) findViewById(R.id.customer_email_textInputLayout);
        phoneNumberTextInputLayout = (TextInputLayout) findViewById(R.id.customer_phone_textInputLayout);
        faxNumberTextInputLayout = (TextInputLayout) findViewById(R.id.customer_fax_textInputLayout);
        streetNameTextInputLayout = (TextInputLayout) findViewById(R.id.customer_street_address_textInputLayout);
        zipCodeTextInputLayout = (TextInputLayout) findViewById(R.id.customer_zip_code_textInputLayout);
        cityTextInputLayout = (TextInputLayout) findViewById(R.id.customer_city_textInputLayout);



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
                if (!Validate())
                {

                    return;


                }
                addCustomerFunction();

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
        String customer_first = firstNameEditText.getText().toString().replaceAll("\'","");
        String customer_last = lastNameEditText.getText().toString().replaceAll("\'","");
        String customer_company = companyEditText.getText().toString().replaceAll("\'","");
        String customer_email = emailAddressEditText.getText().toString().replaceAll("\'","");
        String customer_phoneNumber = phoneNumberEditText.getText().toString().replaceAll("\'","");
        String customer_faxNumber = faxNumberEditText.getText().toString().replaceAll("\'","");
        String customer_street = streetNameEditText.getText().toString().replaceAll("\'","");
        String customer_Zip = zipCodeEditText.getText().toString().replaceAll("\'","");
        String customer_city = cityEditText.getText().toString().replaceAll("\'","");
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
        Toast.makeText(AddCustomer.this,"Your customer has been added", Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean Validate() {


        /*
        EditText firstNameEditText;
        EditText lastNameEditText;
        EditText companyEditText;
        EditText emailAddressEditText;
        EditText phoneNumberEditText;
        EditText faxNumberEditText;
        EditText streetNameEditText;
        EditText zipCodeEditText;
        EditText cityEditText;

        TextInputLayout firstNameTextInputLayout;
        TextInputLayout lastNameTextInputLayout;
        TextInputLayout companyTextInputLayout;
        TextInputLayout emailAddressTextInputLayout;
        TextInputLayout phoneNumberTextInputLayout;
        TextInputLayout faxNumberTextInputLayout;
        TextInputLayout streetNameTextInputLayout;
        TextInputLayout zipCodeTextInputLayout;
        TextInputLayout cityTextInputLayout;
        */
        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            firstNameTextInputLayout.setError("Please fill out the First Name");
            requestFocus(firstNameEditText);
            //firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            streetNameTextInputLayout.setErrorEnabled(false);
            zipCodeTextInputLayout.setErrorEnabled(false);
            cityTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            firstNameTextInputLayout.setErrorEnabled(false);
        }


        if (lastNameEditText.getText().toString().trim().isEmpty()) {
            lastNameTextInputLayout.setError("Please fill out the Last Name");
            requestFocus(lastNameEditText);
            firstNameTextInputLayout.setErrorEnabled(false);
            //lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            streetNameTextInputLayout.setErrorEnabled(false);
            zipCodeTextInputLayout.setErrorEnabled(false);
            cityTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            lastNameTextInputLayout.setErrorEnabled(false);
        }
        if (emailAddressEditText.getText().toString().trim().isEmpty()) {
            emailAddressTextInputLayout.setError("Please fill out an Email Address");
            requestFocus(emailAddressEditText);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            //emailAddressTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            streetNameTextInputLayout.setErrorEnabled(false);
            zipCodeTextInputLayout.setErrorEnabled(false);
            cityTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }
        if (phoneNumberEditText.getText().toString().trim().isEmpty()) {
            phoneNumberTextInputLayout.setError("Please fill out the Phone Number");
            requestFocus(phoneNumberEditText);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            //phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            streetNameTextInputLayout.setErrorEnabled(false);
            zipCodeTextInputLayout.setErrorEnabled(false);
            cityTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            phoneNumberTextInputLayout.setErrorEnabled(false);
        }
        if (streetNameEditText.getText().toString().trim().isEmpty()) {
            streetNameTextInputLayout.setError("Please fill out the Street Name");
            requestFocus(streetNameEditText);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            //streetNameTextInputLayout.setErrorEnabled(false);
            zipCodeTextInputLayout.setErrorEnabled(false);
            cityTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            streetNameTextInputLayout.setErrorEnabled(false);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddressEditText.getText().toString().trim()).matches()) {
            emailAddressTextInputLayout.setError("Please fill out a valid email");
            requestFocus(emailAddressEditText);
            firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            //emailAddressTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
            streetNameTextInputLayout.setErrorEnabled(false);
            zipCodeTextInputLayout.setErrorEnabled(false);
            cityTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            emailAddressTextInputLayout.setErrorEnabled(false);
        }










        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}