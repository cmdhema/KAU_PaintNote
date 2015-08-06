package kau.paintnote;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import kau.paintnote.model.ResponseModel;

public class LoginRequest extends PostNetworkRequest<ResponseModel>{

	private ResponseModel data;
	private String serverUrl;
	private String userData;

	public LoginRequest(String serverUrl, ResponseModel data, String userData) {
		super(data);
		this.data = data;
		this.serverUrl = serverUrl;
		this.userData = userData;
	}

	@Override
	public boolean parsingPostReqeuest(InputStream is, ResponseModel result) {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonBuf =  new StringBuilder();
		String line = "";
		
		try {
			while((line = br.readLine()) != null)
				jsonBuf.append(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Log.i("PasswordResetRequest", jsonBuf.toString());
		
		try {
			JSONObject jData = new JSONObject(jsonBuf.toString());

			result.setCode(jData.getInt("code"));
			result.setMessage(jData.getString("error"));
			
			if(result.getCode() != 200)
				return false;
			

		} catch (JSONException je) {
			Log.e("PasswordResetRequest", "JSON Parsing Error", je);
		}
		
		return true;
	}

	@Override
	public String getRequestQueryString() {
		Log.i("LoginRequest", userData);
		return userData;
	}

	@Override
	public URL getServerURL() {
		try {
			return new URL(serverUrl + "/user/login");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
