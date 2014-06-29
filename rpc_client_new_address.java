import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



class rpc_client_new_address{


	private static final String COMMAND_GET_BALANCE = "getbalance";
	private static final String COMMAND_GET_INFO = "getinfo";
	private static final String COMMAND_GET_NEW_ADDRESS = "getnewaddress";


	String bufferx = new String("");

rpc_client_new_address(){

getNewAddress();


}//**********








private JSONObject invokeRPC(String id, String method, List<String> params) {




		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("method", method);
		if (null != params) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			json.put("params", params);
		}



		JSONObject responseJsonObj = null;
		try {



			httpclient.getCredentialsProvider().setCredentials(new AuthScope("localhost", lm.rpc_wallet_port),
					new UsernamePasswordCredentials("user1", "pass2"));


			StringEntity myEntity = new StringEntity(json.toJSONString());
			System.out.println(json.toString());
			HttpPost httppost = new HttpPost("http://localhost:" + Integer.toString(lm.rpc_wallet_port));
			httppost.setEntity(myEntity);

			System.out.println("executing request" + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {

				System.out.println("Response content length: " + entity.getContentLength());
				bufferx = EntityUtils.toString(entity);
				System.out.println(bufferx);
				lm.rpc_connection_active = 1;

			}

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(bufferx);
 
			JSONObject jsonObject = (JSONObject) obj;
  
			String result = (String) jsonObject.get("result").toString();
			System.out.println("x" + result + "x");

			lm.new_wallet_address = result;

		} catch (ClientProtocolException e) {


			lm.rpc_connection_active = 0;
			e.printStackTrace();
		} catch (IOException e) {


			lm.rpc_connection_active = 0;
			e.printStackTrace();
		} catch (ParseException e) {


			lm.rpc_connection_active = 0;
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {


			lm.rpc_connection_active = 0;
			e.printStackTrace();
		} finally {


			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}






		return responseJsonObj;


}








	public void getNewAddress() {
		String[] params = {};
		JSONObject json = invokeRPC(UUID.randomUUID().toString(), COMMAND_GET_NEW_ADDRESS, Arrays.asList(params));
		//return (String)json.get("result");
	}



}//last
