package org.baugindustries.baugrpg.commands.econ;

import java.io.File;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Balance implements CommandExecutor {
	
	private Main plugin;
	
	public Balance(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("balance").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {
			File file = new File(plugin.getDataFolder() + File.separator + "econ.yml");
		 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		 	
			if (args.length == 0) {
				int balance = (int) config.get(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "Your balance is " + balance + ".");
				return true;
			} else {
				Boolean checkFlag = false;
				
				List<Player> allOnlinePlayers = plugin.getOnlinePlayers();
				Player player2 = null;
				for (int i = 0; i < allOnlinePlayers.size(); i ++) {
					if (args[0].toLowerCase().equals(allOnlinePlayers.get(i).getName().toLowerCase())) {
						if (allOnlinePlayers.get(i).getName().equals(player.getName())) {
							int balance = (int) config.get(player.getUniqueId().toString());
							player.sendMessage(ChatColor.YELLOW + "Your balance is " + balance + ".");
							return true;
						}
						
						player2 = allOnlinePlayers.get(i);
						
						checkFlag = true;
						break;
					}
				}
				if (!checkFlag) {
					player.sendMessage(ChatColor.RED + "Player not found or is not online.");
				} else {
					int balance = (int) config.get(player2.getUniqueId().toString());
					player.sendMessage(ChatColor.YELLOW + player2.getDisplayName() + "'s balance is " + balance + ".");
					return true;
				}
			}
		} else {
			player.sendMessage("You do not have permission to execute this command");
			return true;
		}
		return true;
	}
}