package org.baugindustries.baugrpg.commands;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class SetPoi implements CommandExecutor {
	
	private Main plugin;
	
	public SetPoi(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("setpoi").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		if (!player.hasPermission("minecraft.command.op")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}
		if (args.length != 1) {
			player.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage is: /setpoi <poi>");
			return true;
		}
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		if (args[0].equals("orcExecutionee")) {
			claimsConfig.set("orcExecutionee", player.getLocation());
		} else if (args[0].equals("orcExecutioner")) {
			claimsConfig.set("orcExecutioner", player.getLocation());
		} else if (args[0].equals("orcSpawn")) {
			claimsConfig.set("orcSpawn", player.getLocation());
		} else if (args[0].equals("dwarfSpawn")) {
			claimsConfig.set("dwarfSpawn", player.getLocation());
		} else if (args[0].equals("elfSpawn")) {
			claimsConfig.set("elfSpawn", player.getLocation());
		} else if (args[0].equals("menSpawn")) {
			claimsConfig.set("menSpawn", player.getLocation());
		} else {
			player.sendMessage(ChatColor.RED + "Incorrect usage. Please input an acceptable point of interest.");
			return true;
		}
		
		try {
			claimsConfig.save(claimsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		player.sendMessage(ChatColor.YELLOW + "Location set.");
		return true;
		
	}
}