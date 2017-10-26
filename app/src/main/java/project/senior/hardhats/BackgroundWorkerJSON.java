package project.senior.hardhats;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created on 9/14/2017.
 */



public class BackgroundWorkerJSON extends AsyncTask<DataContainer,Void,JSONObject> {


    public Context context;
    String type;
    ProgressDialog progressDialog;
    String login_url= "http://hardhatz.org/loginJSON.php";
    String createuser_url="http://hardhatz.org/createuser.php";
    String customeraddress_url="http://hardhatz.org/customeraddress.php";
    String contractoraddress_url="http://hardhatz.org/contractoraddress.php";
    String invoiceexport_url="http://hardhatz.org/invoiceexport.php";

    public BackgroundWorkerJSON(Context ctx)
    {
        context=ctx;
    }



    //Easy way to make the Post rather then making that annoying string. Pass in the variable names in the first array, and then the values in the other. Obviously they have to be parallel

    protected String PostBuilder (DataContainer dataContainer)

    {
        String postdata="";

        if (dataContainer.phpVariableNames.size()!=dataContainer.dataPassedIn.size())
        {
            return postdata;
        }

        int loopLength = dataContainer.phpVariableNames.size();

        for (int i=0;i<loopLength;i++)
        {
            try {
                postdata += URLEncoder.encode(dataContainer.phpVariableNames.get(i), "UTF-8")+"="+URLEncoder.encode(dataContainer.dataPassedIn.get(i),"UTF-8")+"&";
            }

            catch (IOException e) {
                return "";
            }

        }
        return postdata.substring(0, postdata.length() - 1);
    }


    protected JSONObject ExecuteRequest(String urlName, DataContainer dataContainer)
    {

        try {
            URL url = new URL(urlName);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = PostBuilder(dataContainer);
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
            //this is all you need to do to get the JsonObject back other then changing types, we might have to do JsonArray eventually
            return new JSONObject(result);
        }
        catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected JSONObject LoginProcedure(DataContainer dataContainer)
    {
        return ExecuteRequest(login_url, dataContainer);
    }

    protected JSONObject RegisterProcedure(DataContainer dataContainer)
    {
        return ExecuteRequest(createuser_url, dataContainer);
    }

    protected JSONObject InvoiceExportProcedure(DataContainer dataContainer) {
        return ExecuteRequest(contractoraddress_url, dataContainer);
    }




    @Override
    protected JSONObject doInBackground(DataContainer... params) {

        type = params[0].type;


        switch (type)
        {
            case "login":
                return LoginProcedure(params[0]);

            case "register":
                return RegisterProcedure(params[0]);

            case "customer":
                return InvoiceExportProcedure(params[0]);


        }

        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(JSONObject result) {



    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}

