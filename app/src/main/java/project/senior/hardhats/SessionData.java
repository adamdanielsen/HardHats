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
    private String address;

    private ArrayList<String> lastStringArrayResult;

    private SessionData() {
        username = "";
        lastStringArrayResult = new ArrayList<>();
        lastStringResult = "";
    }

    static SessionData getInstance() {
        if (mInstance == null) {
            mInstance = new SessionData();
        }
        return mInstance;
    }

    static void Reset() {
        getInstance().eraseUsername();
        getInstance().eraseLastStringResult();
        getInstance().eraseLastStringArrayResult();
        getInstance().eraseUserID();
    }

    String getUsername() {

        return this.username;
    }

    void setUsername(String value) {

        username = value;
    }

    void eraseUsername() {

        username = "";
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

    String getAddress() {
        return address;
    }


    void getUserData() {
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
            phoneNumber = rowData.getString("PhoneNumber");
            faxNumber = rowData.getString("FaxNumber");
            licenseNumber = rowData.getString("LicenseNumber");
            emailAddress = rowData.getString("EmailAddress");
            address = rowData.getString("Address");
            int i = 1;
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

    void setUserID(String userID) {
        UserID = userID;
    }

    private void eraseUserID() {
        UserID = "";
    }

    private void eraseLastStringArrayResult() {
        lastStringArrayResult = new ArrayList<>();
    }

    private void eraseLastStringResult() {
        lastStringResult = "";
    }


}
