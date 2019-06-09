package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import project.senior.hardhats.documents.Invoice;

public class InvoiceActionsActivity extends AppCompatActivity {

    Invoice currentInvoice;
    String accountEmail;
    String customerEmail;
    String gcEmail;
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
    TextView paymentTextView;
    boolean confirmFlag;
    View coordinatorLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_actions);
        confirmFlag = false;
        try {
            currentInvoice = new Invoice(getIntent().getExtras().getString("currentinvoice"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        accountEmail = SessionData.getInstance().getEmailAddress();
        customerEmail = currentInvoice.getCustomerAddress().getEmailAddress();
        gcEmail = currentInvoice.getGeneralContractorEmail();
        emailAccountButton = findViewById(R.id.invoiceactions_emailToSelfButton);
        emailCustomerButton = findViewById(R.id.invoiceactions_emailToCustomerButton);
        emailGCButton = findViewById(R.id.invoiceactions_emailToGeneralContractorButton);
        editInvoiceButton = findViewById(R.id.invoiceactions_editInvoiceButton);
        deleteInvoiceButton = findViewById(R.id.invoiceaction_deleteInvoiceButton);
        paidButton = findViewById(R.id.invoiceactions_paidButton);
        doneButton = findViewById(R.id.invoiceactions_doneButton);
        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        if (currentInvoice.isPaid()) {
            paidButton.setText("Mark as unpaid");
        } else {
            paidButton.setText("Mark as paid");
        }

        emailAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(emailstatus.EMAIL_CONTRACTOR);
                Snackbar
                        .make(coordinatorLayoutView, "Email Sent!", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        emailCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail(emailstatus.EMAIL_CUSTOMER);
                Snackbar
                        .make(coordinatorLayoutView, "Email Sent!", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        if (currentInvoice.getGeneralContractorEmail().isEmpty()) {
            emailGCButton.setEnabled(false);
        }
        emailGCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail(emailstatus.EMAIL_GENERALCONTRACTOR);
                Snackbar
                        .make(coordinatorLayoutView, "Email Sent!", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        editInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEdit();
            }
        });

        deleteInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteInvoice();
            }
        });
        paidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaidStatus();

                if (currentInvoice.isPaid()) {
                    Snackbar
                            .make(coordinatorLayoutView, "Marked as Paid!", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    Snackbar
                            .make(coordinatorLayoutView, "Marked as Unpaid!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDone();
            }
        });


        accountEmailTextView = findViewById(R.id.invoiceactions_selfEmailTextView);
        customerEmailTextView = findViewById(R.id.invoiceactions_customerEmailTextView);
        gcEmailTextView = findViewById(R.id.invoiceactions_gcEmailTextView);
        paymentTextView = findViewById(R.id.invoiceactions_paidTextView);
        accountEmailTextView.setText(accountEmail);
        customerEmailTextView.setText(customerEmail);
        gcEmailTextView.setText(gcEmail);

        if (currentInvoice.isPaid()) {
            paymentTextView.setText("PAID");
        }


        if (!currentInvoice.isPaid()) {
            paymentTextView.setText("UNPAID");
        }

    }

    private void onEdit() {
        Intent openEdit = new Intent(this, EditInvoiceActivity.class);
        openEdit.putExtra("currentinvoice", currentInvoice);
        startActivityForResult(openEdit, 1);
    }

    private void onDone() {
        setResult(0);
        finish();
    }

    private void onDeleteInvoice() {
        if (confirmFlag) {
            onConfirm();
        } else {
            Snackbar
                    .make(coordinatorLayoutView, "Click again to delete!", Snackbar.LENGTH_LONG)
                    .show();
            confirmFlag = true;
        }
    }

    private void onConfirm() {

        BackgroundWorker backgroundWorker = new BackgroundWorker();
        DataContainer dataContainer = new DataContainer();
        dataContainer.type = "deleteinvoice";
        dataContainer.dataPassedIn.add(currentInvoice.getId());
        dataContainer.phpVariableNames.add("id");
        try {
            backgroundWorker.execute(dataContainer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setResult(1);
        finish();
    }

    void changePaidStatus() {
        String changeString;
        if (currentInvoice.isPaid()) {
            changeString = "0";
        } else {
            changeString = "1";
        }

        DataContainer dataContainer = new DataContainer();
        dataContainer.type = "markpaid";
        dataContainer.dataPassedIn.add(changeString);
        dataContainer.dataPassedIn.add(currentInvoice.getId());
        dataContainer.phpVariableNames.add("markpaid");
        dataContainer.phpVariableNames.add("id");
        BackgroundWorker changepayment = new BackgroundWorker();
        try {
            changepayment.execute(dataContainer).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (currentInvoice.isPaid()) {
            currentInvoice.setPaid(false);
            paymentTextView.setText("UNPAID");
            paidButton.setText("Mark as paid");

        } else {
            currentInvoice.setPaid(true);
            paymentTextView.setText("PAID");
            paidButton.setText("Mark as unpaid");
        }
    }

    void sendEmail(emailstatus s) {
        String body = "";
        String emailAddress = "";
        switch (s) {
            case EMAIL_GENERALCONTRACTOR:
                body = "Your subcontractor "
                        + SessionData.getInstance().getFirstName()
                        + " "
                        + SessionData.getInstance().getLastName()
                        + " has sent you this invoice for the customer "
                        + currentInvoice.getCustomerAddress().getFirstName()
                        + " "
                        + currentInvoice.getCustomerAddress().getLastName() +
                        ".";
                emailAddress = gcEmail;

                break;
            case EMAIL_CUSTOMER:
                body = "Hello and greetings from HardHats invoices. Your contractor "
                        + SessionData.getInstance().getFirstName()
                        + " "
                        + SessionData.getInstance().getLastName()
                        + " from "
                        + SessionData.getInstance().getCompanyName()
                        + " has sent you this invoice for work on your project. It is attached to "
                        + "this email.";
                emailAddress = customerEmail;
                break;

            case EMAIL_CONTRACTOR:
                body = "Attached to this email is the requested copy of the invoice for the customer "
                        + currentInvoice.getCustomerAddress().getFirstName()
                        + " "
                        + currentInvoice.getCustomerAddress().getLastName();
                emailAddress = accountEmail;
                break;
        }


        BackgroundWorker sendEmailWorker = new BackgroundWorker();
        DataContainer dataContainer = new DataContainer();
        dataContainer.type = "SendEmail";
        dataContainer.phpVariableNames.add("toAddress");
        dataContainer.phpVariableNames.add("invoicestring");
        dataContainer.phpVariableNames.add("body");
        dataContainer.dataPassedIn.add(emailAddress);
        dataContainer.dataPassedIn.add(currentInvoice.createEmailString());
        dataContainer.dataPassedIn.add(body);

        sendEmailWorker.execute(dataContainer);

        //AlertDialog.Builder box = new AlertDialog.Builder(this);
        //box.setMessage("s");
        //box.show();

    }

    enum emailstatus {EMAIL_CONTRACTOR, EMAIL_CUSTOMER, EMAIL_GENERALCONTRACTOR}
}
