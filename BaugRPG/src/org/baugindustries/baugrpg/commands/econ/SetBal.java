package org.baugindustries.baugrpg.commands.econ;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SetBal implements CommandExecutor {
	
	private Main plugin;
	
	public SetBal(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("setbal").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.op")) {
			if (args.length != 2) {
				player.sendMessage(ChatColor.RED + "Incorrect Usage, correct usage is: /setbal <Player> <Amount>");
			} else {
				Boolean checkFlag = false;
				
				List<Player> allOnlinePlayers = plugin.getOnlinePlayers();
				Player player2 = null;
				for (int i = 0; i < allOnlinePlayers.size(); i ++) {
					if (args[0].toLowerCase().equals(allOnlinePlayers.get(i).getName().toLowerCase())) {
						
						player2 = allOnlinePlayers.get(i);
						
						checkFlag = true;
						break;
					}
				}
				if (!checkFlag) {
					player.sendMessage(ChatColor.RED + "Player not found or is not online.");
				} else {
					try {
				        Integer.parseInt(args[1]);
				    }
				    catch( Exception e ) {
				    	player.sendMessage(ChatColor.RED + "Incorrect Usage: Correct usage is /pay <Player> <Amount>");
				    	return true;
				    }
					
					File file = new File(plugin.getDataFolder() + File.separator + "econ.yml");
				 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				 	
				 	config.set(player2.getUniqueId().toString(), Integer.parseInt(args[1]));
				 	try {
						config.save(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
			}
		} else {
			player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
		}
		
		return true;
	}
}
