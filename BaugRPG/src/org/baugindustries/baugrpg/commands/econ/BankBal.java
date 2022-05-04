package org.baugindustries.baugrpg.commands.econ;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

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
			File file = new File(plugin.getDataFolder() + File.separator + "bank.yml");
		 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		 	
			
			int balance = (int) config.get(player.getUniqueId().toString());
			player.sendMessage(ChatColor.YELLOW + "Your bank balance is " + balance + ".");
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			return true;
		}
	}
}