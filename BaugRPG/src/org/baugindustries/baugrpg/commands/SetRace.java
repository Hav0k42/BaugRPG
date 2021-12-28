package org.baugindustries.baugrpg.commands;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SetRace implements CommandExecutor {
	
	private Main plugin;
	
	public SetRace(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("setrace").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {//everyone has this command hopefully
			PersistentDataContainer data = player.getPersistentDataContainer();
			if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				player.sendMessage("You already have a race assigned.");
			} else {
				player.openInventory(plugin.inventoryManager.getSetRaceMenuInventory());
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return true;
	}
}
