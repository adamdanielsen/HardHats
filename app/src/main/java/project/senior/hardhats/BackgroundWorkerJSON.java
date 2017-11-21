package project.senior.hardhats;

import android.os.AsyncTask;
import android.util.Log;

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
 * A class that is used to run database operations. Parameters are passed in and then
 * this class will pass them to the correct php script
 * The only parameter passed in to the constructor is the context to make
 * debugging easier.
 * The way this class works is that when the execute function is called, three relevant functions
 * are run. First PreExecute, then doInBackground, then PostExecute.
 * This version of the class returns JSONObjects, not Strings.
 *
 */



public class BackgroundWorkerJSON extends AsyncTask<DataContainer,Void,JSONObject> {


    String type;
    String login_url= "http://hardhatz.org/loginJSON.php";
    String createuser_url="http://hardhatz.org/createuser.php";
    String invoiceexport_url="http://hardhatz.org/invoiceexport.php";


    public BackgroundWorkerJSON()
    {

    }



    /**
     * Returns a String object representing the created POST.
     * The DataContainer is read and the parallel arrays are used to create the post.
     * If this function fails because the arrays are not parallel it returns an empty string.
     * At the end the return function snips off the last character because it is always "&"
     *
     * @param   dataContainer The variables and data being passed in
     * @return  The relevant POST created from the data.
     */


    protected String PostBuilder (DataContainer dataContainer)

    {
        StringBuilder postdata= new StringBuilder();

        if (dataContainer.phpVariableNames.size()!=dataContainer.dataPassedIn.size())
        {
            return postdata.toString();
        }

        int loopLength = dataContainer.phpVariableNames.size();

        for (int i=0;i<loopLength;i++)
        {
            try {
                postdata.append(URLEncoder.encode(dataContainer.phpVariableNames.get(i), "UTF-8")).append("=").append(URLEncoder.encode(dataContainer.dataPassedIn.get(i), "UTF-8")).append("&");
            }

            catch (IOException e) {
                return "";
            }

        }
        return postdata.substring(0, postdata.length() - 1);
    }

    /**
     * Returns the echo from the PHP script as a String. The correct php script is loaded
     * from the urlName passed in. Then the correct protocols are used to setup a connection.
     * PostBuilder creates the post data and then the URL is used with the Post attached.
     * Then the echo is read back in to a string.
     * @param   urlName   The URL of the script to be used.
     * @param   dataContainer The data being passed to the PostBuilder
     * @return  The result of the script.
     */

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
            StringBuilder result = new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                result.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            //this is all you need to do to get the JsonObject back other then changing types, we might have to do JsonArray eventually
            return new JSONObject(result.toString());
        }
        catch (IOException e) {

            Log.d("IOEXCEPTION", "ExecuteRequest: threw ioexception ");
            e.printStackTrace();
        } catch (JSONException e) {

            Log.d("JSONEXCEPTION", "ExecuteRequest: threw jsonexception ");
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * other coders.
     *
     * @param   dataContainer   Data to be passed to script.
     * @return  Returns the result of ExecuteRequest, which is the script echo.
     */
    protected JSONObject LoginProcedure(DataContainer dataContainer)
    {
        return ExecuteRequest(login_url, dataContainer);
    }
    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * other coders.
     *
     * @param   dataContainer   Data to be passed to script.
     * @return  Returns the result of ExecuteRequest, which is the script echo.
     */
    protected JSONObject RegisterProcedure(DataContainer dataContainer)
    {
        return ExecuteRequest(createuser_url, dataContainer);
    }
    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * other coders.
     *
     * @param   dataContainer   Data to be passed to script.
     * @return  Returns the result of ExecuteRequest, which is the script echo.
     */
    protected JSONObject InvoiceExportProcedure(DataContainer dataContainer) {
        return ExecuteRequest(invoiceexport_url, dataContainer);
    }



    /**
     * After a few steps, this function returns the result of the echo from the script called.
     * This function uses the type located in the DataContainer to figure out which script to use.
     * This function should NOT BE REMOVED! It is an override of doInBackground from the base class
     * ASyncTask. Without this function this class is just a regular class that runs on the UI
     * Thread
     *
     * @param   params   Data to be passed to script. Just use index 0.
     * @return  Returns the result of the relevant function, which is the script echo.
     */

    @Override
    protected JSONObject doInBackground(DataContainer... params) {

        type = params[0].type;


        switch (type)
        {
            case "login":
                return LoginProcedure(params[0]);

            case "register":
                return RegisterProcedure(params[0]);

            case "invoiceexport":
                return InvoiceExportProcedure(params[0]);


        }

        return null;
    }




    /**
     * This is called before doInBackground.
     */
    @Override
    protected void onPreExecute() {


    }

    /**
     * This is called after doInBackground.
     *
     */
    @Override
    protected void onPostExecute(JSONObject result) {
    }



}

