package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import project.senior.hardhats.Documents.Person;

public class GenerateInvoiceActivity extends AppCompatActivity {

    // TODO Clean this garbage up, change for loop to standard, change php script to do ORDER BY

    private Spinner customerSpinner;
    private Button chooseCustomerButton;
    private CheckBox useGCEmailCheckBox;
    private EditText GCEmailEditText;
    private TextView firstNameTextBox;
    private TextView lastNameTextBox;
    private TextView phoneNumberTextBox;
    private TextView emailAddressTextBox;
    private TextView addressTextBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
        customerSpinner = (Spinner) findViewById(R.id.generateinvoice_chooseCustomerSpinner);
        chooseCustomerButton=(Button) findViewById(R.id.generateinvoice_chooseCustomerButton);
        useGCEmailCheckBox = (CheckBox) findViewById(R.id.generateinvoice_useGeneralContractorEmailCheckBox);
        GCEmailEditText = (EditText) findViewById(R.id.generateinvoice_gcEmailEditText);
        firstNameTextBox = (TextView) findViewById(R.id.generateinvoice_firstNameTextView);
        lastNameTextBox = (TextView) findViewById(R.id.generateinvoice_lastNameTextView);
        phoneNumberTextBox=(TextView) findViewById(R.id.generateinvoice_phoneNumberTextView);
        emailAddressTextBox=(TextView) findViewById(R.id.generateinvoice_emailTextView);
        addressTextBox=(TextView) findViewById(R.id.generateinvoice_addressTextView);





        List<Person> customersForSpinnerList = new ArrayList<>();
        DataContainer listData = new DataContainer();
        listData.type = "populatecustomerspinner";
        listData.phpVariableNames.add("UserID");
        listData.dataPassedIn.add(SessionData.getInstance().getUserID());
        JSONArray customerJSON;
        try {
            customerJSON = new BackgroundWorkerJSONArray().execute(listData).get();
            Person noPerson = new Person();
            noPerson.setId("No Customer Selected");
            noPerson.setLastName("");
            noPerson.setFirstName("");
            customersForSpinnerList.add(noPerson);

            for (int i = customerJSON.length()-1; i>=0; i--)
            {
                Person customerForSpinner = new Person(customerJSON.getJSONObject(i),"Customer");
                customersForSpinnerList.add(customerForSpinner);
            }

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Person> adapter = new ArrayAdapter<>(this, R.layout.simplelistitem, customersForSpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(adapter);
        chooseCustomerButton.setEnabled(false);
        GCEmailEditText.setVisibility(View.INVISIBLE);
        useGCEmailCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (useGCEmailCheckBox.isChecked())
                {

                    GCEmailEditText.setVisibility(View.VISIBLE);
                    GCEmailEditText.setText("");
                }

                else
                {

                    GCEmailEditText.setVisibility(View.INVISIBLE);
                    GCEmailEditText.setText("");
                }
            }
        });
        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Person selectedPerson = (Person) customerSpinner.getSelectedItem();
                if (selectedPerson.getId().equals("No Customer Selected"))
                {
                    chooseCustomerButton.setEnabled(false);

                    firstNameTextBox.setText("");
                    lastNameTextBox.setText("");
                    phoneNumberTextBox.setText("");
                    emailAddressTextBox.setText("");
                    addressTextBox.setText("");
                }
                else {
                    chooseCustomerButton.setEnabled(true);

                    firstNameTextBox.setText(selectedPerson.getFirstName());
                    lastNameTextBox.setText(selectedPerson.getLastName());
                    phoneNumberTextBox.setText(selectedPerson.getPhoneNumber());
                    emailAddressTextBox.setText(selectedPerson.getEmailAddress());
                    addressTextBox.setText(selectedPerson.BuildCustomerAddressForPreview());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            chooseCustomerButton.setEnabled(false);
            }
        });
        chooseCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person selectedPerson = (Person) customerSpinner.getSelectedItem();
                String customerID = selectedPerson.getId();
                String GCEmail;
                if (useGCEmailCheckBox.isChecked())
                {
                    GCEmail=GCEmailEditText.getText().toString();
                }
                else
                {
                    GCEmail="";

                }


                Intent invoiceCreateIntent = new Intent(GenerateInvoiceActivity.this, InvoiceCreateActivity.class);
                invoiceCreateIntent.putExtra("CustomerID",customerID);
                invoiceCreateIntent.putExtra("GCEmail" , GCEmail);
                invoiceCreateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(invoiceCreateIntent);

            }
        });
    }

}
