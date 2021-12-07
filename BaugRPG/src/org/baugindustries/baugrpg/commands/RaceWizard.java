package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType; 


public class RaceWizard implements CommandExecutor {
	
	private Main plugin;
	
	public RaceWizard(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("racewizard").setExecutor(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.op")) {//admin only
			PersistentDataContainer data = player.getPersistentDataContainer();
			if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				player.sendMessage("You already have a race assigned.");
			} else {
				plugin.board.getTeam("Wizards").addPlayer(player);
				data.set(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER, 5);//Wizard: 5
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return true;
	}
}
