package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import project.senior.hardhats.Documents.InvoiceLine;

public class InvoiceCreateActivity extends AppCompatActivity {


    ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
    Button addLineButton;
    Button doneOrFinishLineButton;
    Button cancelOrCancelLineButton;
    String CustomerID;
    String UserID;
    String GCEmail;
    //boolean firstRun=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_create);

        UserID = SessionData.getInstance().getUserID();

        CustomerID = getIntent().getStringExtra("CustomerID");



        //check a null to see if we use gcemail
        GCEmail = getIntent().getStringExtra("GCEmail");

        addLineButton = (Button) findViewById(R.id.invoicecreate_addLineButton);
        doneOrFinishLineButton = (Button) findViewById(R.id.invoicecreate_doneOrFinishLineButton);
        cancelOrCancelLineButton = (Button) findViewById(R.id.invoicecreate_cancelOrCancelLineButton);
        addLineButton.setText(R.string.invoicecreate_addline);
        doneOrFinishLineButton.setText(R.string.invoicecreate_done);
        cancelOrCancelLineButton.setText(R.string.invoicecreate_cancel);
        addLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAddLineButton();
            }
        });

        doneOrFinishLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleDoneOrFinishLineButton(v);
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

    private void HandleDoneOrFinishLineButton(View v) {

        Fragment myFragment =getSupportFragmentManager().findFragmentByTag("INVOICEPREVIEW");

        if (myFragment != null && myFragment.isVisible()) {
            //Add Invoice to Database
            BackgroundWorker dataInsert = new BackgroundWorker();
            StringBuilder invoiceLinesJSON = new StringBuilder();
            invoiceLinesJSON.append("[");
            for (InvoiceLine eachLine : invoiceLines )
            {
                try {
                    invoiceLinesJSON.append(eachLine.getJsonString()).append(",");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            invoiceLinesJSON.setLength(invoiceLinesJSON.length() - 1);
            invoiceLinesJSON.append("]");

            DataContainer dataContainer = new DataContainer();
            dataContainer.type="generateinvoice";
            dataContainer.phpVariableNames.add("UserID");
            dataContainer.phpVariableNames.add("CustomerID");
            dataContainer.phpVariableNames.add("InvoiceDate");
            dataContainer.phpVariableNames.add("GCEmail");
            dataContainer.phpVariableNames.add("InvoiceArray");
            dataContainer.dataPassedIn.add(UserID);
            dataContainer.dataPassedIn.add(CustomerID);
            dataContainer.dataPassedIn.add(DateFormat.getDateTimeInstance().format(new Date()));
            dataContainer.dataPassedIn.add(GCEmail);
            dataContainer.dataPassedIn.add(invoiceLinesJSON.toString());
            dataInsert.execute(dataContainer);
            Log.d("LINES",invoiceLinesJSON.toString());
            Intent menuIntent = new Intent(InvoiceCreateActivity.this, MenuActivity.class);
            menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(menuIntent);
        }

        else
        {
            AddLineFragment addLineFragment =(AddLineFragment) getSupportFragmentManager().findFragmentByTag("ADDLINE");
            InvoiceLine invoiceLine = addLineFragment.sendLineBack();

            if (invoiceLine.Verify()) {
                Snackbar.make(v,"Line Added!",Snackbar.LENGTH_SHORT).show();

                startInvoicePreview();
                SetInvoiceLines(invoiceLine);

            }
            else
            {
                //todo verify stuff
            }
        }

    }

    private void HandleCancelOrCancelLineButton() {

        Fragment myFragment =getSupportFragmentManager().findFragmentByTag("INVOICEPREVIEW");

        if (myFragment != null && myFragment.isVisible()) {
            //todo confirmation
            Intent menuIntent = new Intent(InvoiceCreateActivity.this, MenuActivity.class);
            menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(menuIntent);

        }
        else
        {
            //todo confirmation
            startInvoicePreview();
        }

    }



    protected void startInvoicePreview()
    {

        addLineButton.setVisibility(View.VISIBLE);
        addLineButton.setText(R.string.invoicecreate_addline);
        doneOrFinishLineButton.setText(R.string.invoicecreate_done);
        cancelOrCancelLineButton.setText(R.string.invoicecreate_cancel);
        getSupportFragmentManager().beginTransaction().replace(R.id.generateinvoice_FrameLayout, new InvoicePreviewFragment(),"INVOICEPREVIEW").commit();
    }

    protected void startAddLine()
    {
        addLineButton.setVisibility(View.INVISIBLE);
        doneOrFinishLineButton.setText(R.string.invoicecreate_finishline);
        cancelOrCancelLineButton.setText(R.string.invoicecreate_cancelline);
        getSupportFragmentManager().beginTransaction().replace(R.id.generateinvoice_FrameLayout, new AddLineFragment(),"ADDLINE").commit();
    }

    public ArrayList<InvoiceLine> GetInvoiceLines() {
        return invoiceLines;
    }
    public void SetInvoiceLines(InvoiceLine invoiceLine)
    {
        invoiceLines.add(invoiceLine);

    }
    public void RemoveInvoiceLine(int position)
    {
        invoiceLines.remove(position);

    }
}
