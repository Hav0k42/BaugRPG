package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ResetRace implements CommandExecutor {
	
	private Main plugin;
	
	public ResetRace(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("resetrace").setExecutor(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("race.reset")) {
			PersistentDataContainer data = player.getPersistentDataContainer();
			if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				data.remove(new NamespacedKey(plugin, "Race"));
				
				player.sendMessage("Your race has been reset");
				
				plugin.board.getTeam(plugin.board.getPlayerTeam(player).getName()).removePlayer(player);//removes player from team they're currently on
			} else {
				player.sendMessage("You already didn't have a race assigned.");
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
			
		}
		
		return true;
	}
}
