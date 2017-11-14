package project.senior.hardhats.Documents;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by on 10/12/2017.
 */

public class InvoiceLine{

    private static final DecimalFormat df = new DecimalFormat("$0.00");
    private int materialsID;
    private int invoiceFK;
    private String type;
    private int quantity;
    private String units;
    private double price;
    double lineTotal;

    InvoiceLine(JSONObject invoiceLineJsonObject) throws JSONException {
        materialsID=invoiceLineJsonObject.getInt("MaterialsID");
        invoiceFK=invoiceLineJsonObject.getInt("InvoiceFK");
        type=invoiceLineJsonObject.getString("Type");
        quantity=invoiceLineJsonObject.getInt("Quantity");
        units=invoiceLineJsonObject.getString("Units");
        price=invoiceLineJsonObject.getDouble("Price");
        lineTotal = quantity*price;
    }

    public InvoiceLine()
    {}

    String getInvoiceExportString() {
        int spaceRoom=51;
        int stringLength;
        String line1;
        StringBuilder line2;
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

    public int getMaterialsID() {
        return materialsID;
    }

    public int getInvoiceFK() {
        return invoiceFK;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnits() {
        return units;
    }

    public double getPrice() {
        return price;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    public void setMaterialsID(int materialsID) {
        this.materialsID = materialsID;
    }

    public void setInvoiceFK(int invoiceFK) {
        this.invoiceFK = invoiceFK;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLineTotal() {
        this.lineTotal = price*quantity;
    }

    public boolean Verify() {
        //do some verify stuff

        return true;
    }

    public String getJsonString() throws JSONException {

    return "{\"invoiceFK\":"+invoiceFK+",\"lineTotal\":"+lineTotal+",\"materialsID\":"+materialsID+",\"price\":"+price+",\"quantity\":"+quantity+",\"type\":\""+type+"\",\"units\":\""+units+"\"}";

    }
}


