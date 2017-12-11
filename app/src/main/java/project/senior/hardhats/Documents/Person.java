package project.senior.hardhats.Documents;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by on 10/12/2017.
 */

public class Person implements Parcelable {

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

    public Person(JSONObject personJSONObject, String type) throws JSONException
    {
        personType=type;
        if (personType.equals("Contractor"))
        {
            id= personJSONObject.getString("UserID");
            firstName = personJSONObject.getString("FirstName");
            lastName = personJSONObject.getString("LastName");
            phoneNumber = personJSONObject.getString("PhoneNumber");
            faxNumber = personJSONObject.getString("FaxNumber");
            emailAddress = personJSONObject.getString("EmailAddress");
            licenseNumber= personJSONObject.getString("LicenseNumber");
            companyName = personJSONObject.getString("CompanyName");
            address=personJSONObject.getString("Address");
            address = address.replace(", ","\n");



        }

        if (personType.equals("Customer"))
        {
            id= personJSONObject.getString("CustomerID");
            firstName = personJSONObject.getString("FirstName");
            lastName = personJSONObject.getString("LastName");
            phoneNumber = personJSONObject.getString("PhoneNumber");
            faxNumber = personJSONObject.getString("FaxNumber");
            emailAddress = personJSONObject.getString("EmailAddress");
            companyName = personJSONObject.getString("CompanyName");
            address = personJSONObject.getString("Address");
            address = address.replace(", ","\n");
        }
    }
    //not sure if we need this so not gonna bother
    private Person(String customerID)
    {





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
                address;
    }


    public String BuildCustomerAddressForPreview()
    {

        return firstName +
                " " +
                lastName +
                "\n" +
                address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }

    protected Person(Parcel in) {
        table = in.readString();
        id = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        faxNumber = in.readString();
        emailAddress = in.readString();
        licenseNumber = in.readString();
        companyName = in.readString();
        street = in.readString();
        city = in.readString();
        zipCode = in.readString();
        state = in.readString();
        address = in.readString();
        personType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(table);
        dest.writeString(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(faxNumber);
        dest.writeString(emailAddress);
        dest.writeString(licenseNumber);
        dest.writeString(companyName);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(zipCode);
        dest.writeString(state);
        dest.writeString(address);
        dest.writeString(personType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}