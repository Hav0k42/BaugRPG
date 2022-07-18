package org.baugindustries.baugrpg.commands;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class Tpaccept implements CommandExecutor{
private Main plugin;
	
	public Tpaccept(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("tpaccept").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	
	 	if (!config.contains("allowTpa")) {
	 		config.set("allowTpa", true);
	 		try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	 	}

		Player player = (Player)sender;
		if (config.getBoolean("allowTpa")) {
			if (plugin.tpaHashMap.get(player) == null && plugin.tpahereHashMap.get(player) == null) {
				player.sendMessage(ChatColor.YELLOW + "You currently do not have any teleport requests.");
			} else {
				if (plugin.tpaHashMap.get(player) != null) {
					plugin.tpaHashMap.get(player).sendMessage(ChatColor.YELLOW + "Teleport request accepted. Do not move for 3 seconds.");
					Player player2 = plugin.tpaHashMap.get(player);
					Location coords = player2.getLocation();
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						 
						  public void run() {
						      if (player2.getLocation().getX() == coords.getX() && player2.getLocation().getY() == coords.getY() && player2.getLocation().getZ() == coords.getZ()) {
									player2.teleport(player);
									plugin.tpaHashMap.remove(player);
						      } else {
						    	  player2.sendMessage(ChatColor.RED + "You cretin, I said don't move.");
						    	  plugin.tpaHashMap.remove(player);
						      }
						  }
						}, 60L);
					
					return true;
				}
				
				if (plugin.tpahereHashMap.get(player) != null) {
					player.sendMessage(ChatColor.YELLOW + "Teleport request accepted. Do not move for 3 seconds.");
					Player player2 = plugin.tpahereHashMap.get(player);
					Location coords = player.getLocation();
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						 
						  public void run() {
						      if (player.getLocation().getX() == coords.getX() && player.getLocation().getY() == coords.getY() && player.getLocation().getZ() == coords.getZ()) {
									player.teleport(player2);
									plugin.tpahereHashMap.remove(player);
						      } else {
						    	  player.sendMessage(ChatColor.RED + "You cretin, I said don't move.");
						    	  plugin.tpahereHashMap.remove(player);
						      }
						  }
						}, 60L);
					
					return true;
				}
			}
		} else {
			player.sendMessage(ChatColor.RED + "This feature is currently disabled");
		}
		
		
		
		return true;
	}
	
}
