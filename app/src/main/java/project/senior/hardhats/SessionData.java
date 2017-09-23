package project.senior.hardhats;

import java.util.ArrayList;

/**
 * Created by theev on 9/22/2017.
 */
public class SessionData {
    private static SessionData mInstance = null;

    private String username;
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

    public String getUsername(){
        return this.username;
    }

    public ArrayList<String> getLastStringArrayResult()
    {
        return lastStringArrayResult;
    }

    public String getLastStringResult()
    {
        return lastStringResult;
    }

    public void setUsername(String value){
        username = value;
    }

    public void setLastStringResult(String value)
    {
        lastStringResult=value;
    }

    public void setLastStringArrayResult(ArrayList<String> value)
    {
        lastStringArrayResult=value;

    }
}
