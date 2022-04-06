package org.baugindustries.baugrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ClaimTabCompleter implements TabCompleter {
	Main plugin;
	
	public ClaimTabCompleter(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			List<String> chatChannels = new ArrayList<String>();
			chatChannels.add("delete");
			chatChannels.add("trust");
			chatChannels.add("untrust");
			return chatChannels;
		}
		if (args.length == 2) {
			List<String> chatChannels = new ArrayList<String>();
			plugin.getOnlinePlayers().forEach(player -> {
				chatChannels.add(player.getName());
			});
			return chatChannels;
		}
		return null;
	}

}
