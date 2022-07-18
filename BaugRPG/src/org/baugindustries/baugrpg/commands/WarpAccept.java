package org.baugindustries.baugrpg.commands;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class WarpAccept implements CommandExecutor{
private Main plugin;
	
	public WarpAccept(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("warpaccept").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}

		Player player = (Player)sender;
		int race = plugin.getRace(player);
		String raceString = plugin.getRaceString(race);
		
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
	
	 	File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		if (!player.getUniqueId().toString().equals(leaderConfig.getString(raceString + "LeaderUUID"))) {
			player.sendMessage(ChatColor.RED + "You must be the leader of your race to use this command.");
			return true;
		}
		
		if (plugin.warpHashMap.containsKey(raceString + "Spawn") && plugin.warpHashMap.get(raceString + "Spawn") != null) {
			Player player2 = plugin.warpHashMap.get(raceString + "Spawn");
			player2.sendMessage(ChatColor.YELLOW + "Teleport request accepted. Do not move for 3 seconds.");
			Location coords = player2.getLocation();
			final String raceStringTemp = raceString;
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					if (player2.getLocation().getX() == coords.getX() && player2.getLocation().getY() == coords.getY() && player2.getLocation().getZ() == coords.getZ()) {
						player2.teleport(claimsConfig.getLocation(raceStringTemp + "Spawn"));
				    } else {
				    	player2.sendMessage(ChatColor.RED + "You cretin, I said don't move.");
				    }
				}
			}, 60L);
		} else {
			player.sendMessage(ChatColor.RED + "No one has requested to teleport to your capital.");
		}
		
		
		return true;
	}
	
}
