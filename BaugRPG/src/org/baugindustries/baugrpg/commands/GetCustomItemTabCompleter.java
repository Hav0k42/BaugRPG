package org.baugindustries.baugrpg.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.CustomItems;
import org.baugindustries.baugrpg.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;


public class GetCustomItemTabCompleter implements TabCompleter {
	Main plugin;
	
	public GetCustomItemTabCompleter(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (args.length == 1) {
			List<String> chatChannels = new ArrayList<String>();
			for (Method method : CustomItems.class.getDeclaredMethods()) {
				if (method.getReturnType().equals(ItemStack.class)) {
					chatChannels.add(method.getName());
				}
			}
			return chatChannels;
		}
		return null;
	}

}
