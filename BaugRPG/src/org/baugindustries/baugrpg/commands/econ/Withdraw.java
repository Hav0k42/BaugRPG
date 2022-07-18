package org.baugindustries.baugrpg.commands.econ;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class Withdraw implements CommandExecutor {
	private Main plugin;
	
	public Withdraw(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("withdraw").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {
			if (args.length != 1) {
				player.sendMessage(ChatColor.RED + "Incorrect Usage, correct usage is /withdraw <amount>");
			} else if (!plugin.isInteger(args[0]))  {
				player.sendMessage(ChatColor.RED + "Incorrect Usage, input a number for the amount.");
			} else {
				File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
			 	FileConfiguration econconfig = YamlConfiguration.loadConfiguration(econfile);
				File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
			 	FileConfiguration bankconfig = YamlConfiguration.loadConfiguration(bankfile);
			 	
			 	int toWithdraw = Integer.parseInt(args[0]);
			 	String uuid = player.getUniqueId().toString();
			 	
			 	int balance = (int) econconfig.get(uuid);
				int bankbal = (int) bankconfig.get(uuid);
				
				if (bankbal >= toWithdraw) {
					econconfig.set(uuid, balance + toWithdraw);
					bankconfig.set(uuid, bankbal - toWithdraw);
					try {
						econconfig.save(econfile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						bankconfig.save(bankfile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					player.sendMessage(ChatColor.YELLOW + "Withdrew " + toWithdraw + " dwarven gold from the bank.");
				} else {
					player.sendMessage(ChatColor.RED + "You don't have enough gold to withdraw.");
				}
				
				
			}
		} else {
			player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
			
		}
		return true;
	}
}
