package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import project.senior.hardhats.Documents.InvoiceLine;

public class InvoiceCreate extends AppCompatActivity {


    ArrayList<InvoiceLine> invoiceLines;
    Button addLineOrDoneButton;
    Button cancelButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_create);
        addLineOrDoneButton = (Button) findViewById(R.id.invoicecreate_addLineOrDoneButton);
        cancelButton = (Button) findViewById(R.id.invoicecreate_cancelButton);

        addLineOrDoneButton.setText(R.string.generateinvoice_AddLine);
        cancelButton.setText(R.string.generateinvoice_cancel);

        addLineOrDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startAddLine();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startInvoicePreview();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.generateinvoice_FrameLayout, new InvoicePreviewFragment()).commit();

    }

    protected void startInvoicePreview()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.generateinvoice_FrameLayout, new InvoicePreviewFragment()).commit();

    }

    protected void startAddLine()
    {


        getSupportFragmentManager().beginTransaction().replace(R.id.generateinvoice_FrameLayout, new AddLineFragment()).commit();

    }

    public ArrayList<InvoiceLine> getinvoiceLines() {
        return invoiceLines;
    }

}
