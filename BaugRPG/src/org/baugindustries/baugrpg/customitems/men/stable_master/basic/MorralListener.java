package org.baugindustries.baugrpg.customitems.men.stable_master.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MorralListener implements Listener {
	private Main plugin;
	
	public MorralListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEquipMorral(InventoryClickEvent event) {

		if (!(event.getInventory().getHolder() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getInventory().getHolder();
		
		if (horse.getInventory().getItem(1) != null && Recipes.MORRAL.matches(plugin, horse.getInventory().getItem(1))) return;//stop checking if theres already a morral in this slot.
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (horse.getInventory().getItem(1) != null && Recipes.MORRAL.matches(plugin, horse.getInventory().getItem(1))) {
					horse.setAI(false);
				}
			}
		}, 1L);
		
	}
	
	@EventHandler
	public void onDeEquipMorral(InventoryClickEvent event) {

		if (!(event.getInventory().getHolder() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getInventory().getHolder();
		
		if (horse.getInventory().getItem(1) == null || !Recipes.MORRAL.matches(plugin, horse.getInventory().getItem(1))) return;//stop checking if theres no morral in this slot.
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (horse.getInventory().getItem(1) == null || !Recipes.MORRAL.matches(plugin, horse.getInventory().getItem(1))) {
					horse.setAI(true);
				}
			}
		}, 1L);
		
	}
}
