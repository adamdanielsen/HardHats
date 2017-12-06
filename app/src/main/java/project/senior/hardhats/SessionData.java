package project.senior.hardhats;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by theev on 9/22/2017.
 */
public class SessionData {
    private static SessionData mInstance = null;

    private String username;
    private String UserID;
    private String lastStringResult;
    private String companyName;
    private String faxNumber;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    private String licenseNumber;
    private String emailAddress;
    private String zipCode;
    private String street;
    private String city;
    private String state;

    private ArrayList<String>  lastStringArrayResult;
    private SessionData(){
        username = "";
        lastStringArrayResult= new ArrayList<>();
        lastStringResult="";
    }

    static SessionData getInstance(){
        if(mInstance == null)
        {
            mInstance = new SessionData();
        }
        return mInstance;
    }


    void setUsername(String value){

        username = value;
    }

    String getUsername(){

        return this.username;
    }

    void eraseUsername(){

        username="";
    }


    void setUserID(String userID) {
        UserID = userID;
    }

    String getCompanyName() {
        return companyName;
    }

    String getFaxNumber() {
        return faxNumber;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    String getLastName() {
        return lastName;
    }

    String getFirstName() {
        return firstName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    String getEmailAddress() {
        return emailAddress;
    }

    String getZipCode() {
        return zipCode;
    }

    String getStreet() {
        return street;
    }

    String getCity() {
        return city;
    }

    String getState() {
        return state;
    }

    void getUserData(){
        BackgroundWorkerJSON backgroundWorker = new BackgroundWorkerJSON();
        ArrayList<String> fields = new ArrayList<>();
        fields.add("user_id");
        ArrayList<String> values = new ArrayList<>();
        values.add(UserID);
        DataContainer dataContainer = new DataContainer(fields, values);
        dataContainer.type = "getUser";
        try {
            JSONObject rowData = backgroundWorker.execute(dataContainer).get();
            firstName = rowData.getString("FirstName");
            lastName = rowData.getString("LastName");
            companyName = rowData.getString("CompanyName");
            city = rowData.getString("City");
            street = rowData.getString("Street");
            phoneNumber = rowData.getString("PhoneNumber");
            faxNumber = rowData.getString("FaxNumber");
            licenseNumber = rowData.getString("LicenseNumber");
            emailAddress = rowData.getString("EmailAddress");
            state = rowData.getString("State");
            zipCode = rowData.getString("ZipCode");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String getUserID() {
        return UserID;
    }

    private void eraseUserID()
    {
        UserID= "";
    }

    public void setLastStringArrayResult(ArrayList<String> value) {
        lastStringArrayResult=value;

    }

    public ArrayList<String> getLastStringArrayResult() {
        return lastStringArrayResult;
    }

    public ArrayList<String> getAndEraseLastStringArrayResult() {
        ArrayList<String> temp = new ArrayList<>();
        temp=lastStringArrayResult;
        lastStringArrayResult= new ArrayList<>();
        return temp;
    }


    private void eraseLastStringArrayResult() {
        lastStringArrayResult= new ArrayList<>();
    }

    public void setLastStringResult(String value) {
        lastStringResult=value;
    }

    public String getLastStringResult() {
        return lastStringResult;
    }

    public String getAndEraseLastStringResult() {
        String temp=lastStringResult;
        lastStringResult="";
        return temp;
    }

    private void eraseLastStringResult()
    {
        lastStringResult="";
    }


    static void Reset() {
        getInstance().eraseUsername();
        getInstance().eraseLastStringResult();
        getInstance().eraseLastStringArrayResult();
        getInstance().eraseUserID();
    }




}
