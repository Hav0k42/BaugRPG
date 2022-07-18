package org.baugindustries.baugrpg.commands.econ;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BankBal implements CommandExecutor {
	
	private Main plugin;
	
	public BankBal(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("bankbal").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {
			File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
		 	FileConfiguration bankconfig = YamlConfiguration.loadConfiguration(bankfile);
		 	
			
			int balance = (int) bankconfig.get(player.getUniqueId().toString());
			player.sendMessage(ChatColor.YELLOW + "Your bank balance is " + balance + ".");
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			return true;
		}
	}
}