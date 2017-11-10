package project.senior.hardhats;

import java.util.ArrayList;

/**
 * Created by theev on 9/22/2017.
 */
public class SessionData {
    private static SessionData mInstance = null;

    private String username;
    private String UserID;
    private String lastStringResult;
    private ArrayList<String>  lastStringArrayResult;
    private SessionData(){
        username = "";
        lastStringArrayResult=new ArrayList<String>();
        lastStringResult="";
    }

    public static SessionData getInstance(){
        if(mInstance == null)
        {
            mInstance = new SessionData();
        }
        return mInstance;
    }


    public void setUsername(String value){

        username = value;
    }

    public String getUsername(){

        return this.username;
    }

    public void eraseUsername(){

        username="";
    }


    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserID() {
        return UserID;
    }

    public void eraseUserID()
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
        ArrayList<String> temp = new ArrayList<String>();
        temp=lastStringArrayResult;
        lastStringArrayResult=new ArrayList<String>();
        return temp;
    }


    private void eraseLastStringArrayResult() {
        lastStringArrayResult=new ArrayList<String>();
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


    public static void Reset() {
        getInstance().eraseUsername();
        getInstance().eraseLastStringResult();
        getInstance().eraseLastStringArrayResult();
        getInstance().eraseUserID();
    }

}
