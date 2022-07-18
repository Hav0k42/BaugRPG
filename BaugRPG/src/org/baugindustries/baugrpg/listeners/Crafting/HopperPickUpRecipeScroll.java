package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class HopperPickUpRecipeScroll implements Listener {
	private Main plugin;
	
	public HopperPickUpRecipeScroll(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryPickupItemEvent event) {
		if (event.getItem().getItemStack().isSimilar(plugin.itemManager.getBasicRecipeScrollItem()) ||
			event.getItem().getItemStack().isSimilar(plugin.itemManager.getIntermediateRecipeScrollItem()) ||
			event.getItem().getItemStack().isSimilar(plugin.itemManager.getAdvancedRecipeScrollItem()) ||
			event.getItem().getItemStack().isSimilar(plugin.itemManager.getExpertRecipeScrollItem())
			) {
			event.setCancelled(true);
		}
	}
}
