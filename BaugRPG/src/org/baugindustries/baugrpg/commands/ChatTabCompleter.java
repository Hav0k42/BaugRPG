package org.baugindustries.baugrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ChatTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			List<String> chatChannels = new ArrayList<String>();
			chatChannels.add("Global");
			chatChannels.add("Race");
			return chatChannels;
		}
		return null;
	}

}
