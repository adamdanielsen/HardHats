package project.senior.hardhats.Documents;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project.senior.hardhats.BackgroundWorkerJSONArray;
import project.senior.hardhats.DataContainer;

public class Invoice implements Parcelable {


    private String id;
    private Person customerAddress;
    private Person contractorAddress;
    private String date;
    private String generalContractorEmail;
    private ArrayList<InvoiceLine> invoiceLines;
    private double total;
    private DecimalFormat df = new DecimalFormat("$0.00");
    private boolean paid;


    public Invoice (String InvoiceId) throws InterruptedException, ExecutionException, JSONException {
        BackgroundWorkerJSONArray getInvoiceData= new BackgroundWorkerJSONArray();
        DataContainer invoiceIdData = new DataContainer();
        invoiceIdData.type = "invoiceexport";
        invoiceIdData.phpVariableNames.add("invoice_id");
        invoiceIdData.dataPassedIn.add(InvoiceId);
        JSONArray returnedData = getInvoiceData.execute(invoiceIdData).get();

        id=InvoiceId;
        date=returnedData.getJSONObject(0).getString("InvoiceDate");

        generalContractorEmail=returnedData.getJSONObject(0).getString("GCEmail");

        if ((returnedData.getJSONObject(0).getInt("Paid")==0))
        {

            paid=false;

        }

        else
        {
            paid=true;
        }

        customerAddress=new Person(returnedData.getJSONObject(1), "Customer");

        contractorAddress=new Person( returnedData.getJSONObject(2), "Contractor");

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
    public String createEmailString()
    {

        StringBuilder invoiceString = new StringBuilder();
        invoiceString.append("Contractor:\n");
        invoiceString.append(contractorAddress.BuildContractorAddressForInvoice());
        invoiceString.append("\n\nBill To:\n");
        invoiceString.append(customerAddress.BuildCustomerAddressForInvoice());
        invoiceString.append("\n___________________________________________________________________________\n");
        double finalTotal = 0;
        for (InvoiceLine invoice : invoiceLines)

        {
            finalTotal+=invoice.lineTotal;
            invoiceString.append(invoice.getInvoiceExportStringForEmail());
            invoiceString.append("\n");
        }
        invoiceString.append("___________________________________________________________________________\n");
        invoiceString.append("TOTAL                                              ");
        invoiceString.append(df.format(finalTotal));

        return invoiceString.toString();
    }


    public String createPreviewString()
    {

        StringBuilder invoiceString = new StringBuilder();

        if (paid)
        {
            invoiceString.append("_______________PAID_______________");

        }
        else
        {
            invoiceString.append("______________UNPAID______________");

        }

        invoiceString.append("Contractor:\n");
        invoiceString.append(contractorAddress.BuildContractorAddressForInvoice());
        invoiceString.append("\n\nBill To:\n");
        invoiceString.append(customerAddress.BuildCustomerAddressForInvoice());
        invoiceString.append("\n___________________________________________________________________________\n");
        double finalTotal = 0;
        for (InvoiceLine invoice : invoiceLines)

        {
            finalTotal+=invoice.lineTotal;
            invoiceString.append(invoice.getInvoiceExportStringForPreview());
            invoiceString.append("\n");
        }
        invoiceString.append("___________________________________________________________________________\n");
        invoiceString.append("\nTOTAL                                   ");
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getGeneralContractorEmail() {
        return generalContractorEmail;
    }

    public void setGeneralContractorEmail(String generalContractorEmail) {
        this.generalContractorEmail = generalContractorEmail;
    }


    protected Invoice(Parcel in) {
        id = in.readString();
        customerAddress = (Person) in.readValue(Person.class.getClassLoader());
        contractorAddress = (Person) in.readValue(Person.class.getClassLoader());
        date = in.readString();
        generalContractorEmail = in.readString();
        if (in.readByte() == 0x01) {
            invoiceLines = new ArrayList<InvoiceLine>();
            in.readList(invoiceLines, InvoiceLine.class.getClassLoader());
        } else {
            invoiceLines = null;
        }
        total = in.readDouble();
        df = (DecimalFormat) in.readValue(DecimalFormat.class.getClassLoader());
        paid = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeValue(customerAddress);
        dest.writeValue(contractorAddress);
        dest.writeString(date);
        dest.writeString(generalContractorEmail);
        if (invoiceLines == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(invoiceLines);
        }
        dest.writeDouble(total);
        dest.writeValue(df);
        dest.writeByte((byte) (paid ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Invoice> CREATOR = new Parcelable.Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel in) {
            return new Invoice(in);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };
}