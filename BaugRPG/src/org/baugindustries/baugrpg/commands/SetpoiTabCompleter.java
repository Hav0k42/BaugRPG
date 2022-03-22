package org.baugindustries.baugrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class SetpoiTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			List<String> pois = new ArrayList<String>();
			pois.add("menSpawn");
			pois.add("elfSpawn");
			pois.add("dwarfSpawn");
			pois.add("orcSpawn");
			pois.add("orcExecutioner");
			pois.add("orcExecutionee");
			return pois;
		}
		return null;
	}

}
