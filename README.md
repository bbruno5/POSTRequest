# POSTRequest Plugin for Spigot 1.8/1.9/1.10/1.11/1.12

    Version: 1.4
    Author: bbruno5
    E-mail: bbruno5.ever@gmail.com
    Author Site: https://bbruno5.us.to
    Company: B5 Team
    Company Site: https://b5team.com

# ABOUT
This plugin was made to send POST requests for a external PHP server, sending data from Minecraft Servers as you want. Works with HTTP and HTTPS protocols, accepting as many arguments as you want per command, equivalent to variables sent to external server.

# FEATURES
- Lag-free;
- Secure encrypted data;
- Easy to use;
- Customizable data;
- Infinite arguments;
- Highly customized API.
- Inside game command;
- Console command;
- Remote console command (using Rcon connection).

# DOWNLOAD
The latest compiled version can be found on the following link:
https://www.spigotmc.org/resources/postrequest.42863/

# INSTALL
Download and put the file POSTRequest.jar into your plugins folder and then, restart your server.

# CONFIGURE
Open plugins/POSTRequest/config.txt and edit it putting your URL and your password in the correct fields. Make sure that don't forget to put 'http://' or 'https://' before the url.

After change the file, save it and restart your server again.

Is necessary yet, configure your PHP file on external server. This plugin, encrypt your password with the hash SHA-512, so your PHP file need to have the same password configured as a variable and, in a second moment, must be encrypted and compared with the hash from Minecraft server side, to guarantee your exclusive access when setting important variables, like of monetary plugins.

A sample PHP file can be found with this plugin.

# COMMAND
The syntax to use the plugin is very simple. To use variables, you can call this plugin from another, or just use global variables, like @p, @a, etc. With plugins like Skript, there are innumerable possibilities.

```
/pr <argument1> <argument2> <argument3> ...
```

Example:

```
/pr @p buy Vip
```

With the command above, you send to external server, the name of player, the string 'buy' and the string 'Vip'.

# PERMISSION
To use this plugin and send commands, you need to have the following permission configured on your permission system:

```
postrequest.pr.send (default op)
```

Be very careful when setting this permission, if your intents are for send important data values.

# API
To use API, it is very easy, and add more features, like multiple URL and different passwords per URL.

Example of use:
```
public static void callPOSTRequest(String protocol, String url, String password, String[] args) {
 
    POSTRequestAPI.makeRequest(protocol, url, password, args);
}
```
protocol: must be 'http' or 'https'
url: the complete url that will receive the data request
password: the pure password for the given url, without encryption (this will be did inside API)
args: as many as you want to pass to that url set before, containing any necessary values


# DONATE
I don't made this plugin for gain anything, except facilities on my server, but if you donate me any quantity, I will can buy (someday) a new computer. The mine is dying :s But feel free to use without donating ^_^

https://b5team.com/donate
