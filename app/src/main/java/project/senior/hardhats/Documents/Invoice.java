package project.senior.hardhats.Documents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project.senior.hardhats.BackgroundWorkerJSONArray;
import project.senior.hardhats.DataContainer;

/**
 * Created by on 10/12/2017.
 */

public class Invoice {


    private Person customerAddress;
    private Person contractorAddress;
    ArrayList<InvoiceLine> invoiceLines;
    private double total;
    final DecimalFormat df = new DecimalFormat("$0.00");



    public Invoice (String InvoiceId) throws InterruptedException, ExecutionException, JSONException {
        BackgroundWorkerJSONArray getInvoiceData= new BackgroundWorkerJSONArray();
        DataContainer invoiceIdData = new DataContainer();
        invoiceIdData.type = "invoiceexport";
        invoiceIdData.phpVariableNames.add("invoice_id");
        invoiceIdData.dataPassedIn.add(InvoiceId);
        JSONArray returnedData = getInvoiceData.execute(invoiceIdData).get();

        customerAddress=new Person((JSONObject) returnedData.get(1), "Customer");

        contractorAddress=new Person((JSONObject) returnedData.get(2), "Contractor");

        JSONArray returnedInvoiceLines = returnedData.getJSONArray(3);

        invoiceLines = new ArrayList<>();
        total=0;
        for ( int i=0; i<returnedInvoiceLines.length();i++)
        {
            invoiceLines.add(new InvoiceLine(returnedInvoiceLines.getJSONObject(i)));
            total+=invoiceLines.get(i).getLineTotal();
        }


    }

    public Invoice() {

    }


    //  put linetotal in the invoiceline so we can make totals for each line easily.
    //todo: Tidy up and do checking.
    public String createTxtString()
    {
        StringBuilder invoiceString = new StringBuilder();

        invoiceString.append(contractorAddress.BuildContractorAddressForInvoice());
        invoiceString.append("\n\n");
        invoiceString.append(customerAddress.BuildCustomerAddressForInvoice());
        invoiceString.append("\n___________________________________________________________________________\n");
        double finalTotal = 0;
        for (InvoiceLine invoice : invoiceLines)

        {
                finalTotal+=invoice.lineTotal;
                invoiceString.append(invoice.getInvoiceExportString());
                invoiceString.append("\n");
        }
        invoiceString.append("___________________________________________________________________________\n");
        invoiceString.append("TOTAL                                              ");
        invoiceString.append(df.format(finalTotal));

        return invoiceString.toString();
    }

    public Person getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Person customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Person getContractorAddress() {
        return contractorAddress;
    }

    public void setContractorAddress(Person contractorAddress) {
        this.contractorAddress = contractorAddress;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
