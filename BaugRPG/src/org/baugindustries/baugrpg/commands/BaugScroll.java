package org.baugindustries.baugrpg.commands;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BaugScroll implements CommandExecutor {
	
	private Main plugin;
	
	public BaugScroll(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("baugscroll").setExecutor(this);
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
			if (!data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {//Player is a normie, and therefore cannot open the Scrolls of Baug.
				player.sendMessage("You are not part of a race, join one using /setrace");
			} else {//Player is a part of a race and can read the baug scroll.
				player.openInventory(plugin.inventoryManager.getBaugScrollMenuInventory(player));
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return true;
	}
}
