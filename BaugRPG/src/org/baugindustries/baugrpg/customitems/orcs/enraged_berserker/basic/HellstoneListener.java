package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.ChatColor;

public class HellstoneListener implements Listener {
	private Main plugin;
	
	public HellstoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.HELLSTONE.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be placed");
	}

}
