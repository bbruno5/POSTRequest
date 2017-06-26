package com.b5team.postrequest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class HttpPOSTRequest {
	
	public static void sendRequest(String myurl, String hash, String args[]) {
		
		try {
			URL url = new URL(myurl);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
			con.setDoOutput(true);
			con.setDoInput(true);
			
			ArrayList<String> params = new ArrayList<String>(args.length + 1);
			
			DataOutputStream output = new DataOutputStream(con.getOutputStream());
			output.writeBytes("hash=" + hash);
			for(int i = 0; i < params.size(); i++) {
				output.writeBytes("&");
				output.writeBytes("arg" + i + "=" + args[i]);
				output.flush();
			}
			
			output.flush();
			output.close();
			
			DataInputStream input = new DataInputStream(con.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line;
			
			System.out.println("[POSTRequest] Data sent successfully!");
			System.out.println("[POSTRequest] Resp Code:"+con.getResponseCode());
			System.out.println("[POSTRequest] Resp Message:"+con.getResponseMessage());
			
			while ((line = reader.readLine()) != null) {
				System.out.println("[POSTRequest] Report: "+line);
			}
			
			input.close();
			
		} catch (UnsupportedEncodingException e) {
			System.out.println("[POSTRequest] Encoding error. Maybe string have invalid caracters.");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("[POSTRequest] Invalid URL. Verify your URL and try again.");
			e.printStackTrace();
		} catch (ProtocolException e) {
			System.out.println("[POSTRequest] Error on HttpPOST request.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[POSTRequest] Error on HTTP connection.");
			e.printStackTrace();
		}
	}
}
