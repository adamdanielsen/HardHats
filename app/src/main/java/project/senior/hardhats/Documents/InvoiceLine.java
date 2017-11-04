package project.senior.hardhats.Documents;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by on 10/12/2017.
 */

public class InvoiceLine {

    final DecimalFormat df = new DecimalFormat("$0.00");
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
        int spaceRoom=51;
        int stringLength;
        String line1="";
        StringBuilder line2= new StringBuilder();
        line1=type+"\n";
        line2 = new StringBuilder(quantity + " " + units + " @ " + df.format(price));
        stringLength=line2.length();
        spaceRoom-=stringLength;
        for (int i=spaceRoom;i>0;i--)
        {
            line2.append(" ");

        }
        line2.append(df.format(lineTotal));
        return line1+line2;
    }
}
