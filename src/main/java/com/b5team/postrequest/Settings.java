package com.b5team.postrequest;

import java.net.URL;

public class Settings {
	
	private URL url;
	private String protocol;
	private String pwd;
	
	public String getUrl() {
		return url.toString();
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setUrl(URL url, String protocol) {
		this.url = url;
		this.protocol = protocol;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
