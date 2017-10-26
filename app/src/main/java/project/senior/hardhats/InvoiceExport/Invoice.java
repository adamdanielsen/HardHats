package project.senior.hardhats.InvoiceExport;
import java.util.ArrayList;

/**
 * Created by theev on 10/12/2017.
 */

public class Invoice {


    private Person customerAddress;
    private Person contractorAddress;
    ArrayList<InvoiceLine> invoiceLines;



    Invoice(Person CustomerAddress, Person ContractorAddress, ArrayList<InvoiceLine> InvoiceLines)
    {
        customerAddress=CustomerAddress;
        contractorAddress=ContractorAddress;
        invoiceLines= new ArrayList<>(invoiceLines);
    }

    Invoice (int InvoiceId)
    {
        

    }



}
