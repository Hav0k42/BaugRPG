package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;
import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Chat implements CommandExecutor {
	
	private Main plugin;
	
	public Chat(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("chat").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {
			PersistentDataContainer data = player.getPersistentDataContainer();
			int race = 0;
			
			if (args.length == 0) {
				player.sendMessage("Incorrect Usage: Correct usage is /chat <Channel>");
			} else if (args[0].equals("Global") || args[0].equals("global") || args[0].equals("g")) {
				plugin.channelManager.joinChannel(player, "Global Chat");
			} else if (args[0].equals("Race") || args[0].equals("race") || args[0].equals("r")) {
				
				if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
					race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
					switch (race) {
					case 1:
						plugin.channelManager.joinChannel(player, "Men Chat");
						break;
					case 2:
						plugin.channelManager.joinChannel(player, "Elves Chat");
						break;
					case 3:
						plugin.channelManager.joinChannel(player, "Dwarves Chat");
						break;
					case 4:
						plugin.channelManager.joinChannel(player, "Orcs Chat");
						break;
					case 5:
						plugin.channelManager.joinChannel(player, "Wizard Chat");
						break;
					}
				} else {
					player.sendMessage("You are not part of a race, join one using /setrace");
				}
			} else {
				player.sendMessage("Incorrect chat channel, use arguments: global, race");
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
		}
		return true;
	}
}
