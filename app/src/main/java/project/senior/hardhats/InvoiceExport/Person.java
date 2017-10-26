package project.senior.hardhats.InvoiceExport;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import project.senior.hardhats.BackgroundWorkerJSON;
import project.senior.hardhats.DataContainer;

/**
 * Created by theev on 10/12/2017.
 */

public class Person {

    String table;
    String firstName;
    String lastName;
    String phoneNumber;
    String faxNumber;
    String emailAddress;
    String companyName;
    String street;
    String city;
    String zipCode;
    String state;


    Person (JSONObject personJSONObject) throws JSONException

    {
        firstName=personJSONObject.getString("FirstName");
        lastName=personJSONObject.getString("LastName");
        phoneNumber=personJSONObject.getString("PhoneNumber");
        faxNumber=personJSONObject.getString("FaxNumber");
        emailAddress=personJSONObject.getString("EmailAddress");
        companyName=personJSONObject.getString("CompanyName");
        street=personJSONObject.getString("Street");
        city=personJSONObject.getString("City");
        zipCode=personJSONObject.getString("ZipCode");
        state=personJSONObject.getString("State");
    }

    Person(Context context, String contractorOrCustomer, String id) throws ExecutionException, InterruptedException, JSONException {
        DataContainer idContainer = new DataContainer();
        idContainer.type=contractorOrCustomer;
        idContainer.phpVariableNames.add("ID");
        idContainer.dataPassedIn.add(id);
        BackgroundWorkerJSON getAddressProcess = new BackgroundWorkerJSON();
        JSONObject returnedData;
        returnedData = getAddressProcess.execute(idContainer).get();
        firstName=returnedData.getString("FirstName");
        lastName=returnedData.getString("LastName");
        phoneNumber=returnedData.getString("PhoneNumber");
        faxNumber=returnedData.getString("FaxNumber");
        emailAddress=returnedData.getString("EmailAddress");
        companyName=returnedData.getString("CompanyName");
        street=returnedData.getString("Street");
        city=returnedData.getString("City");
        zipCode=returnedData.getString("ZipCode");
        state=returnedData.getString("State");
    }

    public String BuildAddress()
    {
        String address="";



        return address;
    }

}
