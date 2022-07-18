package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import org.bukkit.ChatColor;

public class DarkMatterListener implements Listener {
	private Main plugin;
	
	public DarkMatterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerItemConsumeEvent event) {
		if (!Recipes.DARK_MATTER.matches(plugin, event.getItem())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be consumed");
	}

}
