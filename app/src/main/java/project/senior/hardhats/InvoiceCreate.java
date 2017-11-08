package project.senior.hardhats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import project.senior.hardhats.Documents.InvoiceLine;

public class InvoiceCreate extends AppCompatActivity {


    ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
    Button addLineButton;
    Button doneOrFinishLineButton;
    Button cancelOrCancelLineButton;
    //boolean firstRun=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_create);

        addLineButton = (Button) findViewById(R.id.invoicecreate_addLineButton);
        doneOrFinishLineButton = (Button) findViewById(R.id.invoicecreate_doneOrFinishLineButton);
        cancelOrCancelLineButton = (Button) findViewById(R.id.invoicecreate_cancelOrCancelLineButton);
        addLineButton.setText("Add Line");
        doneOrFinishLineButton.setText("Done");
        cancelOrCancelLineButton.setText("Cancel");


        addLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAddLineButton();
            }
        });

        doneOrFinishLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleDoneOrFinishLineButton();
            }
        });

        cancelOrCancelLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCancelOrCancelLineButton();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.generateinvoice_FrameLayout, new InvoicePreviewFragment(),"INVOICEPREVIEW").commit();

    }


    private void HandleAddLineButton() {
        startAddLine();
    }

    private void HandleDoneOrFinishLineButton() {
        Fragment myFragment =getSupportFragmentManager().findFragmentByTag("INVOICEPREVIEW");

        if (myFragment != null && myFragment.isVisible()) {

            finish();
        }
        else
        {

            //Add Line to Array
            startInvoicePreview();
        }

    }

    private void HandleCancelOrCancelLineButton() {

        Fragment myFragment =getSupportFragmentManager().findFragmentByTag("INVOICEPREVIEW");

        if (myFragment != null && myFragment.isVisible()) {
            //confirmation
            finish();
        }
        else
        {
            //confirmation
            startInvoicePreview();
        }

    }



    protected void startInvoicePreview()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.generateinvoice_FrameLayout, new InvoicePreviewFragment(),"INVOICEPREVIEW").commit();

    }

    protected void startAddLine()
    {


        getSupportFragmentManager().beginTransaction().replace(R.id.generateinvoice_FrameLayout, new AddLineFragment(),"ADDLINE").commit();

    }

    public ArrayList<InvoiceLine> getinvoiceLines() {
        return invoiceLines;
    }

}
