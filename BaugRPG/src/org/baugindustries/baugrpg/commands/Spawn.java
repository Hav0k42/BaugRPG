package org.baugindustries.baugrpg.commands;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class Spawn implements CommandExecutor {

	private Main plugin;
	
	public Spawn(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("spawn").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		Player player = (Player)sender;
		
		int race = plugin.getRace(player);
		if (race == 0) {
			player.sendMessage(ChatColor.RED + "Only members of a race may use this command. Join one using /setrace.");
			return true;
		}
		
		player.performCommand("warp " + plugin.getRaceString(race) + "Spawn");
		return true;
	}
	
}
