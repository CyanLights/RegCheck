package de.zwinkie.regcheck;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;

public class AnvilDropper {
	
	private Player target;
	private boolean valid;
	
	public AnvilDropper(Player target, CommandSender sender){
		this.target = target;
		valid = false;
		if(!checkForAir(target.getLocation()))
			sender.sendMessage("Not enough room above player!");
		else
			valid = true;
	}

	private boolean checkForAir(Location loc) {		
		for(int i=0;i<12;i++){
			if(loc.getBlock().isEmpty())
				loc = loc.add(0,1,0);
			else
				return false;
		}
		return true;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public void placeAnvil(){
		Location loc = target.getLocation();
		loc = loc.add(0,12,0);
		Block block = loc.getBlock();
		if(block.isEmpty())
			block.setTypeIdAndData(Material.ANVIL.getId(), (byte) 2, true);
	}
}
