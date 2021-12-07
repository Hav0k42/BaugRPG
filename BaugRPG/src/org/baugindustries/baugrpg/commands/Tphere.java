package org.baugindustries.baugrpg.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Tphere implements CommandExecutor {

	private Main plugin;
	
	public Tphere(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("tphere").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		Player player = (Player)sender;
		List<Player> allOnlinePlayers = new ArrayList<Player>();
		OfflinePlayer[] allOfflinePlayers = plugin.getServer().getOfflinePlayers();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			if (allOfflinePlayers[i].isOnline()) {
				allOnlinePlayers.add((Player)allOfflinePlayers[i]);
			}
		}
		
		
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	
	 	if (!config.contains("allowTpa")) {
	 		config.set("allowTpa", true);
	 		try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	}
		
		if (config.getBoolean("allowTpa")) {
			if (player.hasPermission("minecraft.command.help")) {//Consider changing this to check if the admins have allowed tpa
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "Incorrect Usage: Correct usage is /tphere <Player>");
				} else {
					Boolean checkFlag = false;
					for (int i = 0; i < allOnlinePlayers.size(); i ++) {
						if (args[0].toLowerCase().equals(allOnlinePlayers.get(i).getName().toLowerCase())) {
							if (allOnlinePlayers.get(i).getName().equals(player.getName())) {
								player.sendMessage(ChatColor.YELLOW + "You cannot request to teleport to yourself.");
								return true;
							}
							
							plugin.tpahereHashMap.put(allOnlinePlayers.get(i), player);
							allOnlinePlayers.get(i).sendMessage(ChatColor.YELLOW + player.getName() + " wishes you teleport to them. Type " + ChatColor.DARK_PURPLE + "/tpaccept" + ChatColor.YELLOW + " to accept.");
							player.sendMessage(ChatColor.YELLOW + "Request sent successfully");
							
							checkFlag = true;
							break;
						}
					}
					if (!checkFlag) {
						player.sendMessage(ChatColor.RED + "Incorrect Usage: Player not found.");
					}
				}
				
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
			}
		} else {
			player.sendMessage(ChatColor.RED + "This feature is currently disabled");
		}
		return true;
	}
	
}
