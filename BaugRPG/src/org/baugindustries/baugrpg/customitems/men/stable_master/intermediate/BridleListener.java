package org.baugindustries.baugrpg.customitems.men.stable_master.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BridleListener implements Listener {
	private Main plugin;
	
	public BridleListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEquipBridle(InventoryClickEvent event) {

		if (!(event.getInventory().getHolder() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getInventory().getHolder();
		
		if (horse.getInventory().getItem(1) != null && Recipes.BRIDLE.matches(plugin, horse.getInventory().getItem(1))) return;//stop checking if theres already a morral in this slot.
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (horse.getInventory().getItem(1) != null && Recipes.BRIDLE.matches(plugin, horse.getInventory().getItem(1))) {
					horse.setJumpStrength(horse.getJumpStrength() * 1.45);
				}
			}
		}, 1L);
		
	}
	
	@EventHandler
	public void onDeEquipBridle(InventoryClickEvent event) {

		if (!(event.getInventory().getHolder() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getInventory().getHolder();
		
		if (horse.getInventory().getItem(1) == null || !Recipes.BRIDLE.matches(plugin, horse.getInventory().getItem(1))) return;//stop checking if theres no morral in this slot.
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (horse.getInventory().getItem(1) == null || !Recipes.BRIDLE.matches(plugin, horse.getInventory().getItem(1))) {
					horse.setJumpStrength(horse.getJumpStrength() / 1.45);
				}
			}
		}, 1L);
		
	}
}
