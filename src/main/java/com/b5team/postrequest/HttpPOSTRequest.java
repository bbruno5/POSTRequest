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
import java.util.logging.Level;

import org.bukkit.scheduler.BukkitRunnable;

class HttpPOSTRequest {
	
	static void sendRequest(String myurl, String hash, String args[]) {
		
		BukkitRunnable r = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				try {
					URL url = new URL(myurl);
					
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
					con.setDoOutput(true);
					con.setDoInput(true);
					
					DataOutputStream output = new DataOutputStream(con.getOutputStream());
					output.writeBytes("hash=" + hash);
					for(int i = 0; i < args.length; i++) {
						output.writeBytes("&");
						output.writeBytes("arg" + i + "=" + args[i]);
						output.flush();
					}
					
					output.flush();
					output.close();
					
					DataInputStream input = new DataInputStream(con.getInputStream());
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					String line;
					
					Main.getMainLogger().info("Data sent successfully!");
					Main.getMainLogger().info("Resp Code:"+con.getResponseCode());
					System.out.println("Resp Message:"+con.getResponseMessage());
					
					while ((line = reader.readLine()) != null) {
						Main.getMainLogger().info("Report: "+line);
					}
					
					input.close();
					
				} catch (UnsupportedEncodingException e) {
					
					Main.getMainLogger().log(Level.SEVERE, "Encoding error. Maybe string have invalid caracters.", e);
				} catch (MalformedURLException e) {
					
					Main.getMainLogger().log(Level.SEVERE, "Invalid URL. Verify your URL and try again.", e);
				} catch (ProtocolException e) {
					
					Main.getMainLogger().log(Level.SEVERE, "Error on HttpPOST request.", e);
				} catch (IOException e) {
					
					Main.getMainLogger().log(Level.SEVERE, "Error on HTTP connection.", e);
				}
			}
		};
		r.runTaskAsynchronously(Main.getInstance());
	}
}
