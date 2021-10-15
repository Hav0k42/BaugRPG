package org.baugindustries.baugrpg.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tphere implements CommandExecutor {

	private Main plugin;
	
	public Tphere(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("tpa").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		Player player = (Player)sender;
		List<Player> allOnlinePlayers = new ArrayList<Player>();
		OfflinePlayer[] allOfflinePlayers = plugin.getServer().getOfflinePlayers();
		for (int i = 0; i < allOfflinePlayers.length; i++) {
			if (allOfflinePlayers[i].isOnline()) {
				allOnlinePlayers.add((Player)allOfflinePlayers[i]);
			}
		}
		
		
		
		if (player.hasPermission("minecraft.command.help")) {//Consider changing this to check if the admins have allowed tpa
			if (args.length == 0) {
				player.sendMessage("Incorrect Usage: Correct usage is /tphere <Player>");
			} else {
				Boolean checkFlag = false;
				for (int i = 0; i < allOnlinePlayers.size(); i ++) {
					if (args[0].toLowerCase().equals(allOnlinePlayers.get(i).getName().toLowerCase())) {
						//tphere request message to found player
						checkFlag = true;
						break;
					}
				}
				if (!checkFlag) {
					player.sendMessage("Incorrect Usage: Player not found.");
				}
			}
			
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
		}
		return false;
	}
	
}
