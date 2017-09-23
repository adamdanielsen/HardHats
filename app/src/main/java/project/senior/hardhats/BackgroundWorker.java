package project.senior.hardhats;
        import android.content.Context;
        import android.os.AsyncTask;
        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLEncoder;
/**
 * Created by theev on 9/14/2017.
 */

//If you don't know how this class works ask Adam, he might know
// - Adam

public class BackgroundWorker extends AsyncTask<String,Void,String> {


    Context context;
    String type;

    String login_url= "http://hardhatz.org/login.php"; //THIS MUST BE CHANGED YOUR IP IF YOU ARE RUNNING THE SERVER!
    //DB Username: HardHatz
    //DB Password: root123
    String createuser_url="http://hardhatz.org/createuser.php";
    BackgroundWorker(Context ctx)
    {
        context=ctx;
    }

    protected String LoginProcedure(String...params)
    {

        try {
            String user_name=params[1];
            String password=params[2];
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data= URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                    +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream= httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result="";
            String line="";
            while ((line=bufferedReader.readLine())!=null)
            {
                result +=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            if (result.equals("BAD")) {
                SessionData.getInstance().setUsername("BAD");
                return result;
            }


            SessionData.getInstance().setUsername(result);
            return result;


        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    return null;
    }



    @Override
    protected String doInBackground(String... params) {

        type = params[0];


        //I don't know why but this commented out code is EXTREMELY SLOW compared to the type.equals if statement.
        //switch (type)
        //{
        //    case "login":
        //        return LoginProcedure(params);
        //}

        if (type.equals("login")) {
           return LoginProcedure(params);
        }

        return "Unknown or misspelled type?";
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}

