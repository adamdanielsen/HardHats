package project.senior.hardhats.InvoiceExport;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by on 10/12/2017.
 */

public class InvoiceLine {


    int materialsID;
    int invoiceFK;
    String type;
    int quantity;
    String units;
    double price;
    double lineTotal;
    public InvoiceLine(JSONObject invoiceLineJsonObject) throws JSONException {
        materialsID=invoiceLineJsonObject.getInt("MaterialsID");
        invoiceFK=invoiceLineJsonObject.getInt("InvoiceFK");
        type=invoiceLineJsonObject.getString("Type");
        quantity=invoiceLineJsonObject.getInt("Quantity");
        units=invoiceLineJsonObject.getString("Units");
        price=invoiceLineJsonObject.getDouble("Price");
        lineTotal = quantity*price;
    }

    @Override
    //todo: Make work.
    public String toString() {
        return "INVOICE LINE #\nTOTAL STUFF";
    }
}
