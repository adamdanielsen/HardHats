package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import project.senior.hardhats.Documents.Person;

public class GenerateInvoiceActivity extends AppCompatActivity {



    Spinner customerSpinner;
    Button chooseCustomerButton;
    CheckBox useGCEmailCheckBox;
    EditText GCEmailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
        //TODO populate spinner, maybe show detailed data, select customer, intent to InvoiceCreateActivity
        customerSpinner = (Spinner) findViewById(R.id.generateinvoice_chooseCustomerSpinner);
        chooseCustomerButton=(Button) findViewById(R.id.generateinvoice_chooseCustomerButton);


        List<Person> customersForSpinnerList = new ArrayList<>();

        DataContainer listData = new DataContainer();
        listData.type = "populatecustomerspinner";
        listData.phpVariableNames.add("UserID");
        listData.dataPassedIn.add(SessionData.getInstance().getUserID());
        JSONArray customerJSON;
        try {
            customerJSON = new BackgroundWorkerJSONArray().execute(listData).get();

            for (int i = 0; i<customerJSON.length(); i++)
            {
                Person customerForSpinner = new Person();
                customerForSpinner.setCity(customerJSON.getJSONObject(i).getString("City"));
                customerForSpinner.setCompanyName(customerJSON.getJSONObject(i).getString("CompanyName"));
                customerForSpinner.setEmailAddress(customerJSON.getJSONObject(i).getString("EmailAddress"));
                customerForSpinner.setFaxNumber(customerJSON.getJSONObject(i).getString("FaxNumber"));
                customerForSpinner.setFirstName(customerJSON.getJSONObject(i).getString("FirstName"));
                customerForSpinner.setLastName(customerJSON.getJSONObject(i).getString("LastName"));
                customerForSpinner.setPhoneNumber(customerJSON.getJSONObject(i).getString("PhoneNumber"));
                customerForSpinner.setState(customerJSON.getJSONObject(i).getString("State"));
                customerForSpinner.setStreet(customerJSON.getJSONObject(i).getString("Street"));
                customerForSpinner.setZipCode(customerJSON.getJSONObject(i).getString("ZipCode"));
                customersForSpinnerList.add(customerForSpinner);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(this , R.layout.simplelistitem, customersForSpinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(adapter);
        chooseCustomerButton.setEnabled(false);
        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chooseCustomerButton.setEnabled(true);
                //set them edittexts
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
                    DataContainer dataContainer= new DataContainer();
                    dataContainer.type="getgcemail";
                    dataContainer.phpVariableNames.add("ID");
                    dataContainer.dataPassedIn.add(SessionData.getInstance().getUserID());
                    try {
                        GCEmail= new BackgroundWorker().execute(dataContainer).get();
                    } catch (InterruptedException e) {
                        return;
                    } catch (ExecutionException e) {
                        return;
                    }

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
