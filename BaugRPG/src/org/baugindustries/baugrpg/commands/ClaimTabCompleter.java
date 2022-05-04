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
			if ("delete".contains(args[0].toLowerCase())) chatChannels.add("delete");
			if ("trust".contains(args[0].toLowerCase())) chatChannels.add("trust");
			if ("untrust".contains(args[0].toLowerCase())) chatChannels.add("untrust");
			return chatChannels;
		}
		if (args.length == 2) {
			List<String> chatChannels = new ArrayList<String>();
			plugin.getOnlinePlayers().forEach(player -> {
				if (player.getName().toLowerCase().contains(args[0].toLowerCase())) chatChannels.add(player.getName());
			});
			return chatChannels;
		}
		return null;
	}

}
