package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;

public class GenerateInvoiceActivity extends AppCompatActivity {

    Spinner customerSpinner;
    Button chooseCustomerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
        //TODO populate spinner, maybe show detailed data, select customer, intent to InvoiceCreate
        //MAKE STUFF READONLY DONT FORGET
    }
}
