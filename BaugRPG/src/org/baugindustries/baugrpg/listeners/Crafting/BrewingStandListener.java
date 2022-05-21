package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.inventory.ItemStack;

public class BrewingStandListener implements Listener {
	private Main plugin;
	
	public BrewingStandListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAddItem(BrewingStandFuelEvent event) {
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, event.getFuel())) {
				event.setCancelled(true);
				return;
			}
		}
		
	}
	
	@EventHandler
	public void onAddItem(BrewEvent event) {
		ItemStack ingredient = event.getContents().getIngredient();
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, ingredient)) {
				event.setCancelled(true);
				return;
			}
		}
		
	}
	

}
