package project.senior.hardhats;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created on 9/14/2017.
 */

//If you don't know how this class works ask Adam, he might know
// - Adam

public class BackgroundWorker extends AsyncTask<String,Void,String> {


    Context context;
    String type;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    String login_url= "http://hardhatz.org/login.php";
    //DB Username: HardHatz
    //DB Password: root123
    String createuser_url="http://hardhatz.org/createuser.php";

    BackgroundWorker(Context ctx)
    {
        context=ctx;
    }

    //Easy way to make the Post rather then making that annoying string. Pass in the variable names in the first array, and then the values in the other. Obviously they have to be parallel

    protected String PostBuilder (String[] phpVariableNames, String[] dataPassedIn)

    {
        String postdata="";

        if (Array.getLength(phpVariableNames)!=Array.getLength(dataPassedIn))
        {
            return postdata;
        }

        int loopLength = Array.getLength(phpVariableNames);

        for (int i=0;i<loopLength;i++)
        {
            try {
                postdata += URLEncoder.encode(phpVariableNames[i], "UTF-8")+"="+URLEncoder.encode(dataPassedIn[i],"UTF-8")+"&";
            }

            catch (IOException e) {
                return "";
            }

        }
        return postdata.substring(0, postdata.length() - 1);
    }

    protected String ExecuteRequest(String urlName, String[] phpVariableNames, String[] dataPassedIn)
    {

        try {
            URL url = new URL(urlName);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = PostBuilder(phpVariableNames, dataPassedIn);
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                result +=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        }
        catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }

    protected String LoginProcedure(String...params)
    {


            String[] phpVariableNames={"user_name","password"};
            String[] dataPassedIn= {params[1],params[2]};
            return ExecuteRequest(login_url,phpVariableNames,dataPassedIn);


    }

    protected String RegisterProcedure(String...params)
    {
            String[] phpVariableNames = {"user_name","password"};
            String[] dataPassedIn={params[1],params[2]};
            String result = ExecuteRequest(createuser_url,phpVariableNames,dataPassedIn);
            if (result.equals("BAD")) {
                return result;
            }
            return "GOOD";
    }

    @Override
    protected String doInBackground(String... params) {

        type = params[0];


        switch (type)
        {
            case "login":
                return LoginProcedure(params);

            case "register":
                return RegisterProcedure(params);
        }

        return "Unknown or misspelled type?";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog=new AlertDialog.Builder(context).create();
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setTitle("Raw String");
        alertDialog.setMessage(result);
        //debug stuff
        //alertDialog.show();


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}

