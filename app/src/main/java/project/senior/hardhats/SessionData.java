package project.senior.hardhats;

/**
 * Created by theev on 9/22/2017.
 */
public class SessionData {
    private static SessionData mInstance = null;

    private String username;
    private String passData;
    private SessionData(){
        username = "";
        passData="";
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

    public void setUsername(String value){
        username = value;
    }
}
