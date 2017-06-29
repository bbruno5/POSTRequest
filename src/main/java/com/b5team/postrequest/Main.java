package com.b5team.postrequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Settings settings;
	private static Main plugin;
	private static Logger logger;
	
	@Override
	public void onEnable() {
		
		plugin = this;
		logger = this.getLogger();
		
		ConfigHandler configHandler = new ConfigHandler();
		try {
			settings = configHandler.loadSettings();
		} catch (FileNotFoundException ex) {
			configHandler.generateConfig();
			logger.info("POSTRequest generated a config file. Go edit it!");
		} catch (IOException ex) {
			logger.info("POSTRequest failed to read your configuration file.");
            logger.log(Level.SEVERE, null, ex);
            this.getPluginLoader().disablePlugin(this);
            return;
		}
	}
	
	@Override
	public void onDisable() {
		logger.info("POSTRequest is now disabled. You will no longer capable to send HTTP/HTTPS POST requests.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender,
			Command cmd,
			String label,
			String[] args) {
		if(!this.isEnabled()){
            logger.log(Level.SEVERE, "POSTRequest is disabled. Restart the server to run commands.");
        } else if(cmd.getName().equalsIgnoreCase("pr")) {
        	String[] arg = args;
        	String url = Main.getSettings().getUrl();
        	String protocol = Main.getSettings().getProtocol();
        	String pwd = Main.getSettings().getPwd();
        	
        	try {
				Utils util = new Utils();
				String hash = util.encode(pwd);
				
				if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
	        		
					if (protocol == "https") {
	        			
						System.out.println("[POSTRequest] Making HTTPS POST request...");
	        			HttpsPOSTRequest.sendRequest(url, hash, arg);
	        		} else if (protocol == "http") {
	        			
	        			System.out.println("[POSTRequest] Making HTTP POST request...");
	        			HttpPOSTRequest.sendRequest(url, hash, arg);
	        		}
					
	        	} else if (sender instanceof Player) {
	        		
	        		Player plsender = (Player) sender;
	        		if (plsender.hasPermission("postrequest.pr.send")) {
	        			
	        			if (protocol == "https") {
		        			System.out.println("[POSTRequest] Making HTTPS POST request...");
		        			HttpsPOSTRequest.sendRequest(url, hash, arg);
		        		} else if (protocol == "http") {
		        			
		        			System.out.println("[POSTRequest] Making HTTP POST request...");
		        			HttpPOSTRequest.sendRequest(url, hash, arg);
		        		}
	        			
	        		} else {
	        			plsender.sendMessage("You are not allowed to use this command.");
	        		}
	        	}
				
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				logger.log(Level.SEVERE, "[POSTRequest] Non-unicode caracteres found on your password. Change it and reload the server.");
				e.printStackTrace();
			} catch (KeyManagementException e) {
				System.out.println("[POSTRequest] Error on KeyManagement while initiating context.");
				e.printStackTrace();
			}
        }
		return false;
	}
	
	public static Main getInstance() {
		return plugin;
	}
	
	public static Logger getMainLogger() {
		return logger;
	}
	
	public static Settings getSettings() {
		return settings;
	}
}
