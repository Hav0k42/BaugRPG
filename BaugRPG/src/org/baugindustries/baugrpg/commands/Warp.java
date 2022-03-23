package org.baugindustries.baugrpg.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Warp implements CommandExecutor {

	private Main plugin;
	
	public Warp(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("warp").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		Player player = (Player)sender;
		
		if (plugin.orcVictim != null && plugin.orcVictim.equals(player.getUniqueId())) {
			player.sendMessage(ChatColor.RED + "Take your medicine.");
			return true;
		}
		
		List<Player> allOnlinePlayers = new ArrayList<Player>();
		OfflinePlayer[] allOfflinePlayers = plugin.getServer().getOfflinePlayers();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			if (allOfflinePlayers[i].isOnline()) {
				allOnlinePlayers.add((Player)allOfflinePlayers[i]);
			}
		}
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
	
	 	File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
	 	
	 	if (player.hasPermission("minecraft.command.op")) {
	 		if (args[0].equals("menSpawn")) {
	 			player.teleport(claimsConfig.getLocation("menSpawn"));
			} else if (args[0].equals("elfSpawn")) {
				player.teleport(claimsConfig.getLocation("elfSpawn"));
			} else if (args[0].equals("dwarfSpawn")) {
				player.teleport(claimsConfig.getLocation("dwarfSpawn"));
			} else if (args[0].equals("orcSpawn")) {
				player.teleport(claimsConfig.getLocation("orcSpawn"));
			} else {
				player.sendMessage(ChatColor.RED + "Incorrect Usage: Unknown warp location.");
			}
	 	} else if (player.hasPermission("minecraft.command.help")) {//Consider changing this to check if the admins have allowed tpa
			if (args.length == 0) {
				player.sendMessage(ChatColor.RED + "Incorrect Usage: Correct usage is /warp <Location>");
			} else {
				String raceString = "";
				if (args[0].equals("menSpawn")) {
					raceString = "men";
				} else if (args[0].equals("elfSpawn")) {
					raceString = "elf";
				} else if (args[0].equals("dwarfSpawn")) {
					raceString = "dwarf";
				} else if (args[0].equals("orcSpawn")) {
					raceString = "orc";
				}
				
				if (raceString.equals("")) {
					player.sendMessage(ChatColor.RED + "Incorrect Usage: Unknown warp location.");
				}
				
				int race = plugin.getRace(player);
				
				if (raceString.equals(plugin.getRaceString(race))) {
//					player.teleport(claimsConfig.getLocation(raceString + "Spawn"));
					//Player needs to be still for 3 seconds first.
					player.sendMessage(ChatColor.YELLOW + "Teleporting. Don't move for three seconds.");
					Location coords = player.getLocation();
					final String raceStringTemp = raceString;
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							if (player.getLocation().getX() == coords.getX() && player.getLocation().getY() == coords.getY() && player.getLocation().getZ() == coords.getZ()) {
								player.teleport(claimsConfig.getLocation(raceStringTemp + "Spawn"));
						    } else {
						    	player.sendMessage(ChatColor.RED + "You cretin, I said don't move.");
						    }
						}
					}, 60L);
				} else {
					if (leaderConfig.contains(raceString + "LeaderUUID") && Bukkit.getOfflinePlayer(UUID.fromString(leaderConfig.getString(raceString + "LeaderUUID"))).isOnline()) {
						Player leader = Bukkit.getPlayer(UUID.fromString(leaderConfig.getString(raceString + "LeaderUUID")));
						plugin.warpHashMap.put(raceString + "Spawn", player);
						leader.sendMessage(ChatColor.YELLOW + player.getName() + " wishes to teleport to your capital. Type " + ChatColor.DARK_PURPLE + "/warpaccept" + ChatColor.YELLOW + " to allow them to teleport.");
						player.sendMessage(ChatColor.YELLOW + "Request sent successfully");
					} else {
						player.sendMessage(ChatColor.RED + "The leader for this race is not currently online to accept your request.");
					}
				}
				
			}
			
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
		}
		return true;
	}
	
}
