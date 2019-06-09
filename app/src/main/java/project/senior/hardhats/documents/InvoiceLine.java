package project.senior.hardhats.documents;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * Created by on 10/12/2017.
 */

public class InvoiceLine implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<InvoiceLine> CREATOR = new Parcelable.Creator<InvoiceLine>() {
        @Override
        public InvoiceLine createFromParcel(Parcel in) {
            return new InvoiceLine(in);
        }

        @Override
        public InvoiceLine[] newArray(int size) {
            return new InvoiceLine[size];
        }
    };
    private static final DecimalFormat df = new DecimalFormat("$0.00");
    double lineTotal;
    private int materialsID;
    private int invoiceFK;
    private String type;
    private int quantity;
    private String units;
    private double price;

    InvoiceLine(JSONObject invoiceLineJsonObject) throws JSONException {
        materialsID = invoiceLineJsonObject.getInt("MaterialsID");
        invoiceFK = invoiceLineJsonObject.getInt("InvoiceFK");
        type = invoiceLineJsonObject.getString("Type");
        quantity = invoiceLineJsonObject.getInt("Quantity");
        units = invoiceLineJsonObject.getString("Units");
        price = invoiceLineJsonObject.getDouble("Price");
        lineTotal = quantity * price;
    }

    public InvoiceLine() {
    }

    protected InvoiceLine(Parcel in) {
        materialsID = in.readInt();
        invoiceFK = in.readInt();
        type = in.readString();
        quantity = in.readInt();
        units = in.readString();
        price = in.readDouble();
        lineTotal = in.readDouble();
    }

    String getInvoiceExportStringForEmail() {
        int spaceRoom = 51;
        int stringLength;
        String line1;
        StringBuilder line2;
        line1 = type + "\n";
        line2 = new StringBuilder(quantity + " " + units + " @ " + df.format(price));
        stringLength = line2.length();
        spaceRoom -= stringLength;
        for (int i = spaceRoom; i > 0; i--) {
            line2.append(" ");
        }
        line2.append(df.format(lineTotal));
        return line1 + line2;
    }

    String getInvoiceExportStringForPreview() {
        int spaceRoom = 36;
        int stringLength;
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2;


        //for ( String s:(addLinebreaks(type, spaceRoom))) {
        //    line1.append(s).append("\n");
        //}
        //line1.append("\n");
        line1.append(addLinebreaks(type, spaceRoom)).append("\n");
        line2 = new StringBuilder(quantity + " " + units + " @ " + df.format(price));
        stringLength = line2.length();
        spaceRoom -= stringLength;
        for (int i = spaceRoom; i > 0; i--) {
            line2.append(" ");
        }
        line2.append(df.format(lineTotal));
        return (line1.append(line2.toString())).toString();
    }

    public int getMaterialsID() {
        return materialsID;
    }

    public void setMaterialsID(int materialsID) {
        this.materialsID = materialsID;
    }

    public int getInvoiceFK() {
        return invoiceFK;
    }

    public void setInvoiceFK(int invoiceFK) {
        this.invoiceFK = invoiceFK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal() {
        this.lineTotal = price * quantity;
    }

    public String getJsonString() throws JSONException {

        return "{\"invoiceFK\":" + invoiceFK + ",\"lineTotal\":" + lineTotal + ",\"materialsID\":" + materialsID + ",\"price\":" + price + ",\"quantity\":" + quantity + ",\"type\":\"" + type + "\",\"units\":\"" + units + "\"}";

    }

    // https://stackoverflow.com/questions/7528045/large-string-split-into-lines-with-maximum-length-in-java
    //not really sure why this doesn't perform the expected way.
    public String addLinebreaks(String input, int maxCharInLine) {

        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while (word.length() > maxCharInLine) {
                output.append(word, 0, maxCharInLine - lineLen).append("\n");
                word = word.substring(maxCharInLine - lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word).append(" ");

            lineLen += word.length() + 1;
        }

        return output.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(materialsID);
        dest.writeInt(invoiceFK);
        dest.writeString(type);
        dest.writeInt(quantity);
        dest.writeString(units);
        dest.writeDouble(price);
        dest.writeDouble(lineTotal);
    }
}