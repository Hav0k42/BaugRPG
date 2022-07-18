package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.ChatColor;

public class GemClusterListener implements Listener {
	private Main plugin;
	
	public GemClusterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.GEM_CLUSTER.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be placed");
	}

}
