package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class GenerateInvoiceActivity extends AppCompatActivity {

    Spinner customerSpinner;
    Button chooseCustomerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);
        //TODO populate spinner, maybe show detailed data, select customer, intent to InvoiceCreateActivity
        customerSpinner = (Spinner) findViewById(R.id.generateinvoice_chooseCustomerSpinner);
        chooseCustomerButton=(Button) findViewById(R.id.generateinvoice_chooseCustomerButton);

        chooseCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invoiceCreateIntent = new Intent(GenerateInvoiceActivity.this, InvoiceCreateActivity.class);
                invoiceCreateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(invoiceCreateIntent);

            }
        });
    }

}
