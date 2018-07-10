package com.b5team.postrequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

class ConfigHandler {
	
	Settings loadSettings() throws IOException {

		Settings settings = new Settings();
		BufferedReader reader = openFile();
		
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			parseLine(currentLine, settings);
		}
		
		return settings;
	}
	
	void generateConfig() {
		
		File PRDir = Main.getInstance().getDataFolder();
		
		if (!PRDir.exists()) {
			if (!PRDir.mkdirs()) {
				Main.getMainLogger().log(Level.SEVERE, "Could not create plugin directory.");
			}
		}
		
		File configFile = new File(PRDir, "config.txt");
		
		PrintWriter writer = null;
		try {
			if (!configFile.createNewFile()) {
				Main.getMainLogger().log(Level.WARNING, "Could not create new config file.");
			}
			writer = new PrintWriter(new FileWriter(configFile));
		} catch (IOException ex) {
			Main.getMainLogger().info("Failed to create a new configuration file.");
			Main.getMainLogger().log(Level.SEVERE, null, ex);
		}
		
		writer.println("#Configuration and settings file!");
		writer.println("#=========================================================================================================================================#");
		writer.println("#Help: URL: use an url that points to a PHP file on your server. It is extremely necessary that you set the protocol in URL: (hhtp/https).");
		writer.println("#Help: PWD: change the password to one of your choice (set the same in the server php file).");
		writer.println("URL=http://localhost/example.php");
		writer.println("PWD=yourPasswordHere");
		writer.close();
	}

	void parseLine(String line, Settings settings) {
		
		if (line.trim().startsWith("#")) {
			return;
		} else {
			if (line.startsWith("URL=http://")) {
				String url = line.replaceFirst("URL=", "");
				try {
					settings.setUrl(new URL(url), "http");
				} catch (MalformedURLException ex) {
					Main.getMainLogger().log(Level.SEVERE, "Invalid URL.", ex);
				}
			} else if (line.startsWith("URL=https://")) {
				String url = line.replaceFirst("URL=", "");
				try {
					settings.setUrl(new URL(url), "https");
				} catch (MalformedURLException ex) {
					Main.getMainLogger().log(Level.SEVERE, "Invalid URL.", ex);
				}
			} else if (line.startsWith("PWD=")) {
				String pwd = line.replaceFirst("PWD=", "");
				settings.setPwd(pwd);
			}
		}
	}

	private BufferedReader openFile() throws FileNotFoundException {

		File folder = Main.getInstance().getDataFolder();
		File configFile = new File(folder, "config.txt");
		BufferedReader reader = new BufferedReader(new FileReader(configFile));
		
		return reader;
	}
}
