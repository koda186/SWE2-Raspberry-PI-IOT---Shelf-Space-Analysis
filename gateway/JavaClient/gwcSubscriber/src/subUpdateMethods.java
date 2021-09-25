import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class subUpdateMethods //Utilities used by firmware update client
{
    //pre: reason.equals("data")
    //post: content is sent to CIC through url route via HTTP Post Request
    public static void sendDataPost(String content, String reason) throws Exception
    {
        System.out.println("POST content for CIC: " + content);
        //String query_url = "https://team11.softwareengineeringii.com/api/sensorSoftware/firmwareUpdate";
        String query_url = "https://team11.softwareengineeringii.com/api/firmwareUpdateAPI";
        //String query_url = "https://team11.softwareengineeringii.com/routes/api/firmwareUpdateAPI";
        //app.use('/api/firmwareUpdateAPI', require('./routes/api/firmwareUpdateAPI'))
        //Hard URL included for de-bugging purposes
        URL url = new URL(query_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        try (OutputStream os = con.getOutputStream())
        {
            os.write(content.getBytes());
            os.flush();
        }
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            System.out.print("200 STATUS");
        }
        con.disconnect();

        // this isnt needed, I just didnt want to delete parameter just yet
        // reason parameter isnt needed. It will send whatever test is done via this post
        // no matter what!! Then CIC will check what the 'TESTTYPE' is and queue data into
        //database based on that.
        if(reason.equals("data"))
            System.out.print("trying to send firmware data");

    }//end of sendDataPost method

}//end of subUpdateMethods class