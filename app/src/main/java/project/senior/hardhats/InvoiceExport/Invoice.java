package project.senior.hardhats.InvoiceExport;
import java.util.ArrayList;

/**
 * Created by theev on 10/12/2017.
 */

public class Invoice {


    private Address customerAddress;
    private Address contractorAddress;
    ArrayList<InvoiceLine> invoiceLines;
    int width;
    int length;
    int[] addressSpace;


    Invoice(Address CustomerAddress, Address ContractorAddress, ArrayList<InvoiceLine> InvoiceLines)
    {
        customerAddress=CustomerAddress;
        contractorAddress=ContractorAddress;
        invoiceLines= new ArrayList<>(invoiceLines);
    }



}
