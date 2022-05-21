package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class CartographyTableListener implements Listener {
	private Main plugin;
	
	public CartographyTableListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEquipMorral(InventoryClickEvent event) {

		if (!event.getInventory().getType().equals(InventoryType.CARTOGRAPHY)) return;
		
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (event.getInventory().getItem(1) != null) {
					for (Recipes recipe : Recipes.values()) {
						if (recipe.matches(plugin, event.getInventory().getItem(1))) {
							event.getInventory().setItem(2, null);
							return;
						}
					}
				}
			}
		}, 1L);
		
	}

}
