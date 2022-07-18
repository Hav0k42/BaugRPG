package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import org.bukkit.ChatColor;

public class BloodOfTheForsakenListener implements Listener {
	private Main plugin;
	
	public BloodOfTheForsakenListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerItemConsumeEvent event) {
		if (!Recipes.BLOOD_OF_THE_FORSAKEN.matches(plugin, event.getItem())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be consumed");
	}

}
