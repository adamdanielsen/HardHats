package project.senior.hardhats.InvoiceExport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import project.senior.hardhats.BackgroundWorkerJSON;
import project.senior.hardhats.DataContainer;

/**
 * Created by theev on 10/12/2017.
 */

public class Invoice {


    private Person customerAddress;
    private Person contractorAddress;
    ArrayList<InvoiceLine> invoiceLines;


    Invoice(Person CustomerAddress, Person ContractorAddress, InvoiceLine InvoiceLine)
    {
        customerAddress=CustomerAddress;

        contractorAddress=ContractorAddress;



    }

    Invoice (String InvoiceId) throws InterruptedException, ExecutionException, TimeoutException, JSONException {
        BackgroundWorkerJSON getInvoiceData= new BackgroundWorkerJSON();
        DataContainer invoiceIdData = new DataContainer();
        invoiceIdData.type = "GetInvoice";
        invoiceIdData.phpVariableNames.add("invoice_id");
        invoiceIdData.dataPassedIn.add(InvoiceId);
        JSONArray returnedData = new JSONArray(getInvoiceData.execute(invoiceIdData).get(1, TimeUnit.SECONDS));
        customerAddress=new Person((JSONObject) returnedData.get(1), "Customer");

        contractorAddress=new Person((JSONObject) returnedData.get(2), "Contractor");

        JSONArray returnedInvoiceLines = returnedData.getJSONArray(3);

        invoiceLines = new ArrayList<>();

        for ( int i=0; i<=returnedInvoiceLines.length();i++)
        {
            invoiceLines.add(new InvoiceLine(returnedInvoiceLines.getJSONObject(i)));
        }




    }




}
