package project.senior.hardhats;

import android.os.AsyncTask;

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
import java.nio.charset.StandardCharsets;
/*
 * Created on 9/14/2017.
 */


/**
 * A class that is used to run database operations. Parameters are passed in and then
 * this class will pass them to the correct php script
 * The only parameter passed in to the constructor is the context to make
 * debugging easier.
 * The way this class works is that when the execute function is called, three relevant functions
 * are run. First PreExecute, then doInBackground, then PostExecute.
 * This version of the class returns Strings, not JSONObject.
 */

class BackgroundWorker extends AsyncTask<DataContainer, Void, String> {

    public static final int LOGIN = 0;
    public static final int CREATEUSER = 1;
    public static final int SENDINVOICEEMAIL = 2;
    public static final int ADDCUSTOMER = 3;
    public static final int GENERATEINVOICE = 4;
    public static final int GETGCEMAIL = 5;
    public static final int SENDEMAIL = 6;
    public static final int CHECKUSERNAME = 7;
    private final String login_url = "http://hardhatz.org/login.php";
    private final String createuser_url = "http://hardhatz.org/createuser.php";
    private final String sendinvoiceemail_url = "http://hardhatz.org/sendinvoiceemail.php";
    private final String addcustomer_url = "http://hardhatz.org/addcustomer.php";
    private final String generateinvoice_url = "http://hardhatz.org/generateinvoice.php";
    private final String getgcemail_url = "http://hardhatz.org/getgcemail.php";
    //                                      Yes I know this is misspelled below
    private final String sendemail_url = "http://hardhatz.org/phpmailtext.php";
    private final String checkusername_url = "http://hardhatz.org/checkusername.php";
    private final String markpaid_url = "http://hardhatz.org/markpaid.php";
    private final String deleteinvoice_url = "http://hardhatz.org/deleteinvoice.php";
    private final String editinvoice_url = "http://hardhatz.org/editinvoice.php";
    private String type;

    BackgroundWorker() {
    }

    /**
     * Returns a String object representing the created POST.
     * The DataContainer is read and the parallel arrays are used to create the post.
     * If this function fails because the arrays are not parallel it returns an empty string.
     * At the end the return function snips off the last character because it is always "&"
     *
     * @param dataContainer The variables and data being passed in
     * @return The relevant POST created from the data.
     */
    private String PostBuilder(DataContainer dataContainer) {
        StringBuilder postdata = new StringBuilder();

        if (dataContainer.phpVariableNames.size() != dataContainer.dataPassedIn.size()) {
            return postdata.toString();
        }

        int loopLength = dataContainer.phpVariableNames.size();

        for (int i = 0; i < loopLength; i++) {
            try {
                postdata.append(URLEncoder.encode(dataContainer.phpVariableNames.get(i), "UTF-8")).append("=").append(URLEncoder.encode(dataContainer.dataPassedIn.get(i), "UTF-8")).append("&");
            } catch (IOException e) {
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
     *
     * @param urlName       The URL of the script to be used.
     * @param dataContainer The data being passed to the PostBuilder
     * @return The result of the script.
     */
    private String ExecuteRequest(String urlName, DataContainer dataContainer) {

        try {
            URL url = new URL(urlName);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            String post_data = PostBuilder(dataContainer);
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result.toString();
        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;
    }

    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * other coders.
     *
     * @param dataContainer Data to be passed to script.
     * @return Returns the result of ExecuteRequest, which is the script echo.
     */

    private String LoginProcedure(DataContainer dataContainer) {

        return ExecuteRequest(login_url, dataContainer);


    }


    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * others.
     *
     * @param dataContainer Data to be passed to script.
     * @return Returns the result of ExecuteRequest, which is the script echo.
     */
    private String RegisterProcedure(DataContainer dataContainer) {
        String result = ExecuteRequest(createuser_url, dataContainer);

        if (result == null) {
            return "CONNECTION";
        }
        if (result.equals("BAD")) {
            return "BAD";
        }

        return "GOOD";
    }

    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * other coders.
     *
     * @param dataContainer Data to be passed to script.
     * @return Returns the result of ExecuteRequest, which is the script echo.
     */

    private String SendInvoiceEmail(DataContainer dataContainer) {

        return ExecuteRequest(sendinvoiceemail_url, dataContainer);


    }


    /**
     * Returns ExecuteRequest, which is the resulting data, with the correct URL. This can be
     * simplified with a switch statement in ExecuteRequest, but it is left to not confuse
     * other coders.
     *
     * @param dataContainer Data to be passed to script.
     * @return Returns the result of ExecuteRequest, which is the script echo.
     */

    private String AddCustomerProcedure(DataContainer dataContainer) {

        return ExecuteRequest(addcustomer_url, dataContainer);

    }


    private String GenerateInvoiceProcedure(DataContainer dataContainer) {

        return ExecuteRequest(generateinvoice_url, dataContainer);

    }


    private String GetGCEmail(DataContainer dataContainer) {

        return ExecuteRequest(getgcemail_url, dataContainer);
    }

    private String SendEmailProcedure(DataContainer param) {

        return ExecuteRequest(sendemail_url, param);
    }

    private String CheckUsernameProcedure(DataContainer param) {
        return ExecuteRequest(checkusername_url, param);
    }

    private String MarkPaidProcedure(DataContainer param) {
        return ExecuteRequest(markpaid_url, param);
    }

    private String DeleteInvoiceProcedure(DataContainer param) {
        return ExecuteRequest(deleteinvoice_url, param);
    }


    private String EditInvoiceProcedure(DataContainer param) {
        return ExecuteRequest(editinvoice_url, param);
    }

    /**
     * After a few steps, this function returns the result of the echo from the script called.
     * This function uses the type located in the DataContainer to figure out which script to use.
     * This function should NOT BE REMOVED! It is an override of doInBackground from the base class
     * ASyncTask. Without this function this class is just a regular class that runs on the UI
     * Thread
     *
     * @param params Data to be passed to script. Just use index 0.
     * @return Returns the result of the relevant function, which iEs the script echo.
     */

    @Override
    protected String doInBackground(DataContainer... params) {

        type = params[0].type;

        switch (type) {
            case "login":
                return LoginProcedure(params[0]);
            case "register":
                return RegisterProcedure(params[0]);
            case "sendinvoice":
                return SendInvoiceEmail(params[0]);
            case "addcustomer":
                return AddCustomerProcedure(params[0]);
            case "generateinvoice":
                return GenerateInvoiceProcedure(params[0]);
            case "getgcemail":
                return GetGCEmail(params[0]);
            case "SendEmail":
                return SendEmailProcedure(params[0]);
            case "checkusername":
                return CheckUsernameProcedure(params[0]);
            case "markpaid":
                return MarkPaidProcedure(params[0]);
            case "deleteinvoice":
                return DeleteInvoiceProcedure(params[0]);
            case "editinvoice":
                return EditInvoiceProcedure(params[0]);


        }

        return "Unknown or misspelled type?";
    }


    /**
     * This is called before doInBackground.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /**
     * This is called after doInBackground.
     */
    @Override
    protected void onPostExecute(String result) {

    }
}