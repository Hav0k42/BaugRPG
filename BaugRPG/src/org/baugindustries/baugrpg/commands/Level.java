package org.baugindustries.baugrpg.commands;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class Level implements CommandExecutor {
	
	private Main plugin;
	
	public Level(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("level").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	int points = 0;
	 	if (skillsconfig.contains("totalSkillPoints")) {
	 		points = skillsconfig.getInt("totalSkillPoints");
	 	}
		player.sendMessage(ChatColor.GOLD + "You are currently level " + points + ".");
		
		return true;
	}
	
}
