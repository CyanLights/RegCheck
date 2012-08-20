package de.zwinkie.regcheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * 
 * @author Cyan & Tuqe
 * @since August 2012
 */

// Begin main class
public class Main extends JavaPlugin{
	
	public void onEnable(){
		//do something
		getLogger().info("RegCheck started.");
	}
	
	public void onDisable(){
		//stop it!
		try {
		if(in != null)
			in.close();
		} catch (IOException e) {}
		
		getLogger().info("RegCheck has been disabled.");
	}
	
	
	/** I definitely didn't get this quite right, the if else nesting is
	* getting the better of me.
	* I was basically trying to do this: Declare onCommand, if command = /check then return true,
	* if not return false. If true, check permission, check args, then follow through with
	* the meat of the command.
	*/
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	Player player = null;
    	if(sender instanceof Player)
    		player = (Player) sender;

    	if(cmd.getName().equalsIgnoreCase("check") && player != null){ // If the player types /check, do this:
    		//Check if player has permission
    	    if(player.hasPermission("regcheck.lookup")) {
    	    	//If yes, follow through. Check arg amount:
        		if (args.length > 1) {
     	           sender.sendMessage("Too many arguments!");
     	           return false;
     	        }else if (args.length < 1) {
     	           sender.sendMessage("You must specify a username to lookup.");
     	           return false;
     	        }else{
     	        	if(isRegistered(args[0]))
     	        		sender.sendMessage("§a"+args[0]+" has registered.");
     	        	else
     	        		sender.sendMessage("§c"+args[0]+" has not registered.");
     	        	return true;
     	        }
    	    }
    	    //They don't have permission...
    	    sender.sendMessage("You are not allowed to use that command.");
    	    return false;
    	} //If this has happened the function will break and return true.
    	return false; 
    }
    
    private static BufferedReader in;
    
    public static boolean isRegistered(String in_player)
    {	
		try {		
			URL url = new URL("http://zwinkie.de/search.php?stext="+in_player+"&search=Search&method=OR&forum_id=0&stype=members&order=0");
			URLConnection connection = url.openConnection();
			connection.connect();
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			in.skip(15050);
			char[] copyBuffer = new char[1024];
	        StringBuffer sb = new StringBuffer();
	        sb.append(copyBuffer, 0, in.read(copyBuffer, 0, 1024));  
	        
	        return sb.toString().contains("profile-link");
	        
		} catch (IOException e) {
			return false;
		}	
    }


	
}
