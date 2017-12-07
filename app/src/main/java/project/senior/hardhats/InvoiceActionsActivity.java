package project.senior.hardhats;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    String paid;
    Button emailAccountButton;
    Button emailCustomerButton;
    Button emailGCButton;
    TextView accountEmailTextView;
    TextView customerEmailTextView;
    TextView gcEmailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_actions);
        currentInvoice =(Invoice) getIntent().getExtras().get("invoice");
        accountEmail=SessionData.getInstance().getEmailAddress();
        customerEmail=currentInvoice.getCustomerAddress().getEmailAddress();
        gcEmail=currentInvoice.getContractorAddress().getEmailAddress();

        emailAccountButton=(Button) findViewById(R.id.invoiceactions_emailToSelfButton);
        emailCustomerButton=(Button) findViewById(R.id.invoiceactions_emailToCustomerButton);
        emailGCButton=(Button) findViewById(R.id.invoiceactions_emailToGeneralContractorButton);
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

    void sendEmail()
    {
        BackgroundWorker sendEmailWorker = new BackgroundWorker();
        DataContainer dataContainer = new DataContainer();
        dataContainer.type="SendEmail";
        dataContainer.phpVariableNames.add("toAddress");
        dataContainer.phpVariableNames.add("invoicestring");
        dataContainer.dataPassedIn.add(currentInvoice.getCustomerAddress().getEmailAddress());
        dataContainer.dataPassedIn.add(currentInvoice.createTxtString());
        String s="";
        try {
            sendEmailWorker.execute(dataContainer).get();
            Toast.makeText(this, "Allow a few minutes for the invoice to arrive", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder box = new AlertDialog.Builder(this);
        box.setMessage("s");
        //box.show();

    }
}
