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

import net.md_5.bungee.api.ChatColor;

public class Deposit implements CommandExecutor {
	private Main plugin;
	
	public Deposit(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("deposit").setExecutor(this);
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
				player.sendMessage(ChatColor.RED + "Incorrect Usage, correct usage is /deposit <amount>");
			} else if (!plugin.isInteger(args[0]))  {
				player.sendMessage(ChatColor.RED + "Incorrect Usage, input a number for the amount.");
			} else {
				File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
			 	FileConfiguration econconfig = YamlConfiguration.loadConfiguration(econfile);
				File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
			 	FileConfiguration bankconfig = YamlConfiguration.loadConfiguration(bankfile);
			 	
			 	int toDeposit = Integer.parseInt(args[0]);
			 	String uuid = player.getUniqueId().toString();
			 	
			 	int balance = (int) econconfig.get(uuid);
				int bankbal = (int) bankconfig.get(uuid);
				
				if (balance > toDeposit) {
					econconfig.set(uuid, balance - toDeposit);
					bankconfig.set(uuid, bankbal + toDeposit);
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
					player.sendMessage(ChatColor.YELLOW + "Deposited " + toDeposit + " dwarven gold into the bank.");
				} else {
					player.sendMessage(ChatColor.RED + "You don't have enough gold to deposit.");
				}
				
				
			}
		} else {
			player.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
			
		}
		return true;
	}
}
