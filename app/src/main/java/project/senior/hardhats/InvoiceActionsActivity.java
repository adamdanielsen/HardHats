package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import project.senior.hardhats.Documents.Invoice;

public class InvoiceActionsActivity extends AppCompatActivity {

    Invoice currentInvoice;
    String accountEmail;
    String customerEmail;
    String gcEmail;
    boolean paid;
    Button emailAccountButton;
    Button emailCustomerButton;
    Button emailGCButton;
    Button editInvoiceButton;
    Button deleteInvoiceButton;
    Button paidButton;
    Button doneButton;
    TextView accountEmailTextView;
    TextView customerEmailTextView;
    TextView gcEmailTextView;
    enum emailstatus {EMAIL_CONTRACTOR,EMAIL_CUSTOMER,EMAIL_GENERALCONTRACTOR};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_actions);
        currentInvoice =(Invoice) getIntent().getExtras().get("currentinvoice");
        accountEmail=SessionData.getInstance().getEmailAddress();
        customerEmail=currentInvoice.getCustomerAddress().getEmailAddress();
        gcEmail=currentInvoice.getContractorAddress().getEmailAddress();
        paid=currentInvoice.isPaid();
        emailAccountButton=(Button) findViewById(R.id.invoiceactions_emailToSelfButton);
        emailCustomerButton=(Button) findViewById(R.id.invoiceactions_emailToCustomerButton);
        emailGCButton=(Button) findViewById(R.id.invoiceactions_emailToGeneralContractorButton);
        editInvoiceButton=(Button) findViewById(R.id.invoiceactions_editInvoiceButton);
        deleteInvoiceButton=(Button) findViewById(R.id.invoiceaction_deleteInvoiceButton);
        paidButton=(Button) findViewById(R.id.invoiceactions_paidButton);
        doneButton=(Button) findViewById(R.id.invoiceactions_doneButton);

        emailAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        emailCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        emailGCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        paidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        accountEmailTextView=(TextView) findViewById(R.id.invoiceactions_selfEmailTextView);
        customerEmailTextView=(TextView) findViewById(R.id.invoiceactions_customerEmailTextView);
        gcEmailTextView=(TextView) findViewById(R.id.invoiceactions_gcEmailTextView);
        accountEmailTextView.setText(accountEmail);
        customerEmailTextView.setText(customerEmail);
        gcEmailTextView.setText(gcEmail);
    }


    void changePaidStatus()
    {



    }

    void sendEmail(emailstatus s)
    {


        String body="";

        switch(s)
        {
            case EMAIL_GENERALCONTRACTOR:
                body="Your subcontractor "
                        +SessionData.getInstance().getFirstName()
                        + " "
                        +SessionData.getInstance().getLastName()
                        +" has sent you this invoice for the customer "
                        + currentInvoice.getCustomerAddress().getFirstName()
                        +" "
                        +currentInvoice.getCustomerAddress().getLastName()+
                        ".";
                break;
            case EMAIL_CUSTOMER:
                body="Hello and greetings from HardHats invoices. Your contractor "
                        +SessionData.getInstance().getFirstName()
                        + " "
                        +SessionData.getInstance().getLastName()
                        +" from"
                        +SessionData.getInstance().getCompanyName()
                        +" has sent you this invoice for work on your project. It is attached to "
                        + "this email.";
                break;
            case EMAIL_CONTRACTOR:
                body="Attached to this email is the requested copy of the invoice for the customer "
                        + currentInvoice.getCustomerAddress().getFirstName()
                        +" "
                        +currentInvoice.getCustomerAddress().getLastName();
                break;
        }



        BackgroundWorker sendEmailWorker = new BackgroundWorker();
        DataContainer dataContainer = new DataContainer();
        dataContainer.type="SendEmail";
        dataContainer.phpVariableNames.add("toAddress");
        dataContainer.phpVariableNames.add("invoicestring");
        dataContainer.phpVariableNames.add("body");
        dataContainer.dataPassedIn.add(currentInvoice.getCustomerAddress().getEmailAddress());
        dataContainer.dataPassedIn.add(currentInvoice.createEmailString());
        dataContainer.dataPassedIn.add(body);

        try {
            sendEmailWorker.execute(dataContainer).get();
            Toast.makeText(this, "Allow a few minutes for the invoice to arrive", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //AlertDialog.Builder box = new AlertDialog.Builder(this);
        //box.setMessage("s");
        //box.show();

    }
}
