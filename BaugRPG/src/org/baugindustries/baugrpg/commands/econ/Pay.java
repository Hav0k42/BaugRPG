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

import org.bukkit.ChatColor;

public class Pay implements CommandExecutor {
	
	private Main plugin;
	
	public Pay(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("pay").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {
			if (args.length != 2) {
				player.sendMessage(ChatColor.RED + "Incorrect Usage: Correct usage is /pay <Player> <Amount>");
			} else {
				Boolean checkFlag = false;
				
				List<Player> allOnlinePlayers = plugin.getOnlinePlayers();
				Player player2 = null;
				for (int i = 0; i < allOnlinePlayers.size(); i ++) {
					if (args[0].toLowerCase().equals(allOnlinePlayers.get(i).getName().toLowerCase())) {
						if (allOnlinePlayers.get(i).getName().equals(player.getName())) {
							player.sendMessage(ChatColor.RED + "You cannot give money to yourself");
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
					try {
				        Integer.parseInt(args[1]);
				    }
				    catch( Exception e ) {
				    	player.sendMessage(ChatColor.RED + "Incorrect Usage: Correct usage is /pay <Player> <Amount>");
				    	return true;
				    }
					
					
					
					File file = new File(plugin.getDataFolder() + File.separator + "econ.yml");
				 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					
					int balance = (int) config.get(player.getUniqueId().toString());
					int balance2 = (int) config.get(player2.getUniqueId().toString());
					
					int payBal = Math.abs(Integer.parseInt(args[1]));
					
					if (payBal > balance) {
						player.sendMessage(ChatColor.RED + "You do not have enough Dwarven Gold to pay.");
				    	return true;
					}
					
					
					int newBal1 = balance - payBal;
					int newBal2 = balance2 + payBal;
					
					config.set(player.getUniqueId().toString(), newBal1);
					config.set(player2.getUniqueId().toString(), newBal2);
					
					try {
						config.save(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					
					player.sendMessage(ChatColor.YELLOW + "Successfully paid " + player2.getDisplayName() + " " + payBal + " Dwarven Gold.");
					player2.sendMessage(ChatColor.YELLOW + player.getDisplayName() + " paid you " + payBal + " Dwarven Gold.");
							
				}
			}
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
		}
		return false;
	}
}