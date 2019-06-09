package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import project.senior.hardhats.documents.Person;

public class CustomerDetailView extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText CompanyName;
    EditText phoneNumber;
    EditText faxNumber;
    EditText emailAddress;
    TextView address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail_view);
        firstName = findViewById(R.id.customer_Preview_firstName_editText);
        lastName = findViewById(R.id.customer_Preview_lastName_editText);
        CompanyName = findViewById(R.id.customer_Preview_CompanyName_editText);
        phoneNumber = findViewById(R.id.customer_Preview_phoneNumber_editText);
        faxNumber = findViewById(R.id.customer_Preview_faxNumber_editText);
        emailAddress = findViewById(R.id.customer_Preview_emailAddress_editText);
        address = findViewById(R.id.customer_preview_address);

        String customerid = getIntent().getStringExtra("CUSTOMERID");

        BackgroundWorkerJSON backgroundWorker = new BackgroundWorkerJSON();


        DataContainer dataContainer = new DataContainer();
        dataContainer.type = "getCustomer";
        dataContainer.phpVariableNames.add("UserID");
        dataContainer.dataPassedIn.add(customerid);
        Person customer = null;
        try {
            customer = new Person(backgroundWorker.execute(dataContainer).get(), "Customer");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        phoneNumber.setText(customer.getPhoneNumber());
        faxNumber.setText(customer.getFaxNumber());
        emailAddress.setText(customer.getEmailAddress());
        CompanyName.setText(customer.getCompanyName());
        address.setText(customer.getAddress());

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        phoneNumber.setEnabled(false);
        faxNumber.setEnabled(false);
        emailAddress.setEnabled(false);
        CompanyName.setEnabled(false);


    }
}
