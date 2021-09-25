//import java.io.InputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//import javax.net.ssl.HttpsURLConnection;

public class Methods //Utilities used by Gateway Diagnostics client
{

	//pre: reason.equals("heartbeats")
	//post: content is sent to CIC through url route via HTTP Post Request
	public static void sendHeartbeatPost(String content, String reason) throws Exception
	{
		System.out.println("POST content: " + content);
		String query_url = "https://team11.softwareengineeringii.com/api/heartbeatsAPI/createNewHeartbeat";
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
		if(reason.equals("heartbeat"))
			System.out.print("");

	}//end of sendHeartbeatPost utility

	//pre: reason.equals("diagnostics")
	//post: content is sent to CIC through url route via HTTP Post Request
	public static void sendDiagnosticsPost(String content, String reason) throws Exception
	{
		System.out.println("POST content: " + content);
		String query_url = "https://team11.softwareengineeringii.com/api/heartbeatsAPI/createNewHeartbeat";
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
		if(reason.equals("diagnostics"))
			System.out.print("trying to send diagnostic test results");

	}//end of sendDiagnosticsPost utility

}//end of Methods class
