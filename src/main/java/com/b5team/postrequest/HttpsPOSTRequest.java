package com.b5team.postrequest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.bukkit.scheduler.BukkitRunnable;

class HttpsPOSTRequest {
	
	static void sendRequest(String myurl, String hash, String args[]) {
		
		BukkitRunnable r = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				try (CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient()) {
					
					HttpPost httppost = new HttpPost(myurl);
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("hash", hash));
					
					for(int i = 0; i < args.length; i++) {
						
						params.add(new BasicNameValuePair("arg" + i, args[i]));
					}
					
					httppost.setEntity(new UrlEncodedFormEntity(params));
					CloseableHttpResponse response = httpclient.execute(httppost);
					
					if (response.getStatusLine().getStatusCode() == 200) {
						
						DataInputStream input = new DataInputStream(response.getEntity().getContent());
						BufferedReader reader = new BufferedReader(new InputStreamReader(input));
						String line;
						
						Main.getMainLogger().log(Level.INFO, "Data sent successfully!");
						
						while ((line = reader.readLine()) != null) {
							
							System.out.println("[POSTRequest] Report: " + line);
						}
						
						response.close();
					}
					
				} catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | IOException e) {
					
					Main.getMainLogger().log(Level.SEVERE, "HTTPS post error.", e);
				}
			}
		};
		r.runTaskAsynchronously(Main.getInstance());
	}
	
	static CloseableHttpClient createAcceptSelfSignedCertificateClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		
		SSLContext sslContext = SSLContextBuilder
				.create()
				.loadTrustMaterial(new TrustSelfSignedStrategy())
				.build();
		
		HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
		
		return HttpClients
				.custom()
				.setSSLSocketFactory(connectionFactory)
				.build();
	}
}
