package project.senior.hardhats.Documents;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by on 10/12/2017.
 */

public class Person {

    String table;
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String faxNumber;
    private String emailAddress;
    private String licenseNumber;
    private String companyName;
    private String street;
    private String city;
    private String zipCode;
    private String state;
    private String address;
    private String personType;


   public Person()
   {}

   Person(JSONObject personJSONObject, String type) throws JSONException
    {
        personType=type;
        if (personType.equals("Contractor"))
        {
            firstName = personJSONObject.getString("FirstName");
            lastName = personJSONObject.getString("LastName");
            phoneNumber = personJSONObject.getString("PhoneNumber");
            faxNumber = personJSONObject.getString("FaxNumber");
            emailAddress = personJSONObject.getString("EmailAddress");
            licenseNumber= personJSONObject.getString("LicenseNumber");
            companyName = personJSONObject.getString("CompanyName");
            address=personJSONObject.getString("Address");
            int i =1;
            }

        if (personType.equals("Customer"))
        {
            firstName = personJSONObject.getString("FirstName");
            lastName = personJSONObject.getString("LastName");
            phoneNumber = personJSONObject.getString("PhoneNumber");
            faxNumber = personJSONObject.getString("FaxNumber");
            emailAddress = personJSONObject.getString("EmailAddress");
            companyName = personJSONObject.getString("CompanyName");
            street = personJSONObject.getString("Street");
            city = personJSONObject.getString("City");
            zipCode = personJSONObject.getString("ZipCode");
            state = personJSONObject.getString("State");
        }
    }


    public String BuildContractorAddressForInvoice()
    {
        String formattedaddress = companyName +
                "\n" +
                phoneNumber +
                "\n" +
                firstName +
                " " +
                lastName +
                "\n" +
                address +
                "\n" +
                "License #: " +
                licenseNumber;


        return formattedaddress;
    }


    public String BuildCustomerAddressForInvoice()
    {

        return companyName +
                "\n" +
                phoneNumber +
                "\n" +
                firstName +
                " " +
                lastName +
                "\n" +
                street +
                "\n" +
                city +
                "," +
                state +
                " " +
                zipCode;
    }


    public String BuildCustomerAddressForPreview()
    {

        return firstName +
                " " +
                lastName +
                "\n" +
                street +
                "\n" +
                city +
                "," +
                state +
                " " +
                zipCode;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return id+"       "+firstName+" "+lastName;
    }
}
