package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.ChatColor;

public class SteelWoolListener implements Listener {
	private Main plugin;
	
	public SteelWoolListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.STEEL_WOOL.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be placed");
	}

}
