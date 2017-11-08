package project.senior.hardhats.Documents;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by on 10/12/2017.
 */

public class Person {

    String table;
    String firstName;
    String lastName;
    String phoneNumber;
    String faxNumber;
    String emailAddress;
    String licenseNumber;
    String companyName;
    String street;
    String city;
    String zipCode;
    String state;



   public Person (JSONObject personJSONObject,String type) throws JSONException

    {

        if (type.equals("Contractor"))
        {
            firstName = personJSONObject.getString("FirstName");
            lastName = personJSONObject.getString("LastName");
            phoneNumber = personJSONObject.getString("PhoneNumber");
            faxNumber = personJSONObject.getString("FaxNumber");
            emailAddress = personJSONObject.getString("EmailAddress");
            licenseNumber= personJSONObject.getString("LicenseNumber");
            companyName = personJSONObject.getString("CompanyName");
            street = personJSONObject.getString("Street");
            city = personJSONObject.getString("City");
            zipCode = personJSONObject.getString("ZipCode");
            state = personJSONObject.getString("State");
        }

        if (type.equals("Customer"))
        {
            firstName = personJSONObject.getString("FirstName");
            lastName = personJSONObject.getString("LastName");
            phoneNumber = personJSONObject.getString("PhoneNumber");
            faxNumber = personJSONObject.getString("FaxNumber");
            emailAddress = personJSONObject.getString("EmailAddress");
            //licenseNumber= personJSONObject.getString("LicenseNumber");
            companyName = personJSONObject.getString("CompanyName");
            street = personJSONObject.getString("Street");
            city = personJSONObject.getString("City");
            zipCode = personJSONObject.getString("ZipCode");
            state = personJSONObject.getString("State");
        }
    }


    public String BuildContractorAddress()
    {
        StringBuilder address = new StringBuilder();


        address.append(companyName);
        address.append("\n");
        address.append(phoneNumber);
        address.append("\n");
        address.append(firstName);
        address.append(" ");
        address.append(lastName);
        address.append("\n");
        address.append(street);
        address.append("\n");
        address.append(city);
        address.append(",");
        address.append(state);
        address.append(" ");
        address.append(zipCode);
        address.append("\n");
        address.append("License #: ");
        address.append(licenseNumber);
        return address.toString();
    }


    public String BuildCustomerAddress()
    {
        StringBuilder address = new StringBuilder();

        address.append(companyName);
        address.append("\n");
        address.append(phoneNumber);
        address.append("\n");
        address.append(firstName);
        address.append(" ");
        address.append(lastName);
        address.append("\n");
        address.append(street);
        address.append("\n");
        address.append(city);
        address.append(",");
        address.append(state);
        address.append(" ");
        address.append(zipCode);
        return address.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getState() {
        return state;
    }
}
