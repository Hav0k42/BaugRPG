package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FeatureManagement implements Listener{
	private Main plugin;
	public FeatureManagement(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() == null) return;
		if (!event.getView().getTitle().equals("Feature Management")) return;
		
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	
		ItemStack tpaItem = plugin.itemManager.getTpaFeatureItem();
		ItemStack recipeItem = plugin.itemManager.getRecipeFeatureItem();
		
		if (event.getCurrentItem().equals(tpaItem)) {
			
			config.set("allowTpa", !(Boolean)config.get("allowTpa"));
			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ItemStack newTpaItem = plugin.itemManager.getTpaFeatureItem();;
			event.getClickedInventory().setItem(event.getSlot(), newTpaItem);
		}
		
		
		if (event.getCurrentItem().equals(recipeItem)) {
			
			config.set("allowRecipe", !(Boolean)config.get("allowRecipe"));
			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ItemStack newRecipeItem = plugin.itemManager.getRecipeFeatureItem();;
			event.getClickedInventory().setItem(event.getSlot(), newRecipeItem);
		}
		
		
		event.setCancelled(true);
	}
}
