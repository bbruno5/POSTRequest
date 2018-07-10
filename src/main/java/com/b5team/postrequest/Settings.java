package com.b5team.postrequest;

import java.net.URL;

class Settings {
	
	private URL url;
	private String protocol;
	private String pwd;
	
	String getUrl() {
		return url.toString();
	}
	
	String getProtocol() {
		return protocol;
	}
	
	String getPwd() {
		return pwd;
	}
	
	void setUrl(URL url, String protocol) {
		this.url = url;
		this.protocol = protocol;
	}
	
	void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
