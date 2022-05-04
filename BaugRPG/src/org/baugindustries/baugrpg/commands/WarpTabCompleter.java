package org.baugindustries.baugrpg.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class WarpTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			List<String> pois = new ArrayList<String>();
			if ("menspawn".contains(args[0].toLowerCase())) pois.add("menSpawn");
			if ("elfspawn".contains(args[0].toLowerCase())) pois.add("elfSpawn");
			if ("dwarfspawn".contains(args[0].toLowerCase())) pois.add("dwarfSpawn");
			if ("orcspawn".contains(args[0].toLowerCase())) pois.add("orcSpawn");
			return pois;
		}
		return null;
	}

}
