package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.concurrent.ExecutionException;

public class AddCustomer extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText companyEditText;
    private EditText emailAddressEditText;
    private EditText phoneNumberEditText;
    private EditText faxNumberEditText;
    private EditText addressEditText;

    private TextInputLayout firstNameTextInputLayout;
    private TextInputLayout lastNameTextInputLayout;
    private TextInputLayout companyTextInputLayout;
    private TextInputLayout emailAddressTextInputLayout;
    private TextInputLayout phoneNumberTextInputLayout;
    private TextInputLayout faxNumberTextInputLayout;

    boolean googleError;

    Place address;
    String backupAddress;
    String addressToUse;

    private Button saveButton;
    private Button cancelButton;
    private Button addressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        googleError=false;
        firstNameEditText = (EditText) findViewById(R.id.customer_first_editText);
        lastNameEditText = (EditText) findViewById(R.id.customer_last_editText);
        companyEditText = (EditText) findViewById(R.id.customer_company_editText);
        emailAddressEditText = (EditText) findViewById(R.id.customer_email_address_editText);
        phoneNumberEditText = (EditText) findViewById(R.id.customer_phone_editText);
        faxNumberEditText = (EditText) findViewById(R.id.customer_fax_editText);
        addressEditText=(EditText) findViewById(R.id.customer_address_editText);
        firstNameTextInputLayout = (TextInputLayout) findViewById(R.id.customer_first_textInputLayout);
        lastNameTextInputLayout = (TextInputLayout) findViewById(R.id.customer_last_textInputLayout);
        companyTextInputLayout = (TextInputLayout) findViewById(R.id.customer_company_textInputLayout);
        emailAddressTextInputLayout = (TextInputLayout) findViewById(R.id.customer_email_textInputLayout);
        phoneNumberTextInputLayout = (TextInputLayout) findViewById(R.id.customer_phone_textInputLayout);
        faxNumberTextInputLayout = (TextInputLayout) findViewById(R.id.customer_fax_textInputLayout);

        saveButton = (Button) findViewById(R.id.save_customer_button);
        cancelButton = (Button) findViewById(R.id.cancel_customer_button);
        addressButton=(Button) findViewById(R.id.customer_address_button);
        addressEditText.setEnabled(false);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(AddCustomer.this, "Select an address before proceeding", Toast.LENGTH_SHORT).show();
                    return;
                }
                addCustomerFunction();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAddress();
            }
        });

    }

    private void getAddress() {
        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            Toast.makeText(this, "Please select your address", Toast.LENGTH_LONG).show();
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            googleError = true;
            Toast.makeText(this, "Error starting Google Places, launching backup", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, BackupAddressHandlerActivity.class);
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_LONG).show();
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!googleError) {
            if (resultCode == RESULT_OK) {
                address = PlaceAutocomplete.getPlace(this, data);
                backupAddress = null;
            }

            if (resultCode == RESULT_CANCELED) {
                address = null;
            }


            if (address == null) {

                Toast.makeText(this, "No Address Selected", Toast.LENGTH_SHORT).show();
                return;
            }

            addressToUse = address.getAddress().toString();
            addressEditText.setText(addressToUse);

        }
    }






    private void addCustomerFunction(){
        if (!Validate())
        {
            return;
        }
        String customer_userid = SessionData.getInstance().getUserID();
        String customer_first = firstNameEditText.getText().toString();
        String customer_last = lastNameEditText.getText().toString();
        String customer_company = companyEditText.getText().toString();
        String customer_email = emailAddressEditText.getText().toString();
        String customer_phoneNumber = phoneNumberEditText.getText().toString();
        String customer_faxNumber = faxNumberEditText.getText().toString();
        String customer_address=addressEditText.getText().toString();


        DataContainer dataContainer = new DataContainer();

        dataContainer.type = "addcustomer";

        dataContainer.phpVariableNames.add("userid");
        dataContainer.phpVariableNames.add("firstname");
        dataContainer.phpVariableNames.add("lastname");
        dataContainer.phpVariableNames.add("phonenumber");
        dataContainer.phpVariableNames.add("faxnumber");
        dataContainer.phpVariableNames.add("emailaddress");
        dataContainer.phpVariableNames.add("companyname");
        dataContainer.phpVariableNames.add("address");
        dataContainer.dataPassedIn.add(customer_userid);
        dataContainer.dataPassedIn.add(customer_first);
        dataContainer.dataPassedIn.add(customer_last);
        dataContainer.dataPassedIn.add(customer_phoneNumber);
        dataContainer.dataPassedIn.add(customer_faxNumber);
        dataContainer.dataPassedIn.add(customer_email);
        dataContainer.dataPassedIn.add(customer_company);
        dataContainer.dataPassedIn.add(customer_address);

        BackgroundWorker database = new BackgroundWorker();
        try {
            String s = database.execute(dataContainer).get();

            //AlertDialog.Builder box= new AlertDialog.Builder(this);
           // box.setTitle("wut");
            //box.setMessage(s);
            //box.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Toast.makeText(AddCustomer.this,"Your customer has been added", Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean Validate() {

        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            firstNameTextInputLayout.setError("Please fill out the First Name");
            requestFocus(firstNameEditText);
            //firstNameTextInputLayout.setErrorEnabled(false);
            lastNameTextInputLayout.setErrorEnabled(false);
            companyTextInputLayout.setErrorEnabled(false);
            emailAddressTextInputLayout.setErrorEnabled(false);
            phoneNumberTextInputLayout.setErrorEnabled(false);
            faxNumberTextInputLayout.setErrorEnabled(false);
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
            return false;
        }
        else {
            phoneNumberTextInputLayout.setErrorEnabled(false);
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