package org.baugindustries.baugrpg.commands;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class Tpdeny implements CommandExecutor{
private Main plugin;
	
	public Tpdeny(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("tpdeny").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}

		Player player = (Player)sender;
		if (plugin.tpaHashMap.get(player) == null && plugin.tpahereHashMap.get(player) == null) {
			player.sendMessage(ChatColor.YELLOW + "You currently do not have any teleport requests.");
		} else {
			if (plugin.tpaHashMap.get(player) != null) {
				plugin.tpaHashMap.remove(player);
				player.sendMessage(ChatColor.YELLOW + "Request denied");
				return true;
			}
			
			if (plugin.tpahereHashMap.get(player) != null) {
				plugin.tpahereHashMap.remove(player);
				player.sendMessage(ChatColor.YELLOW + "Request denied");
				return true;
			}
		}
		return true;
	}
	
}
