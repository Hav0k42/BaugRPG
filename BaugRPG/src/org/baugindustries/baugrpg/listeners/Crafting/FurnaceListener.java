package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class FurnaceListener implements Listener {
	private Main plugin;
	
	public FurnaceListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onStartSmelt(BlockCookEvent event) {
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getSource())) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void onBurn(FurnaceBurnEvent event) {
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getFuel())) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onHopper(InventoryMoveItemEvent event) {
		if (!event.getInitiator().getType().equals(InventoryType.HOPPER)) return;
		if (!(event.getDestination().getType().equals(InventoryType.FURNACE) || event.getDestination().getType().equals(InventoryType.BLAST_FURNACE))) return;
		
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getItem())) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
}
