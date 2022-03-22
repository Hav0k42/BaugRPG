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
		ItemStack endermanGriefItem = plugin.itemManager.getEndermanGriefItem();
		ItemStack creeperGriefItem = plugin.itemManager.getCreeperGriefItem();
		ItemStack tntGriefItem = plugin.itemManager.getTntGriefItem();
		ItemStack ghastGriefItem = plugin.itemManager.getGhastGriefItem();
		
		if (event.getCurrentItem().equals(tpaItem)) {
			
			config.set("allowTpa", !(Boolean)config.get("allowTpa"));
			ItemStack newTpaItem = plugin.itemManager.getTpaFeatureItem();;
			event.getClickedInventory().setItem(event.getSlot(), newTpaItem);
		}
		
		
		if (event.getCurrentItem().equals(recipeItem)) {
			
			config.set("allowRecipe", !(Boolean)config.get("allowRecipe"));
			ItemStack newRecipeItem = plugin.itemManager.getRecipeFeatureItem();;
			event.getClickedInventory().setItem(event.getSlot(), newRecipeItem);
		}
		
		if (event.getCurrentItem().equals(endermanGriefItem)) {
			
			config.set("allowEndermanGriefing", !(Boolean)config.get("allowEndermanGriefing"));
			ItemStack newEndermanGriefItem = plugin.itemManager.getEndermanGriefItem();
			event.getClickedInventory().setItem(event.getSlot(), newEndermanGriefItem);
		}
		
		if (event.getCurrentItem().equals(creeperGriefItem)) {
			
			config.set("allowCreeperGriefing", !(Boolean)config.get("allowCreeperGriefing"));
			ItemStack newCreeperGriefItem = plugin.itemManager.getCreeperGriefItem();
			event.getClickedInventory().setItem(event.getSlot(), newCreeperGriefItem);
		}
		
		if (event.getCurrentItem().equals(tntGriefItem)) {
			
			config.set("allowTntGriefing", !(Boolean)config.get("allowTntGriefing"));
			ItemStack newTntGriefItem = plugin.itemManager.getTntGriefItem();
			event.getClickedInventory().setItem(event.getSlot(), newTntGriefItem);
		}
		
		if (event.getCurrentItem().equals(ghastGriefItem)) {
			
			config.set("allowGhastGriefing", !(Boolean)config.get("allowGhastGriefing"));
			ItemStack newGhastGriefItem = plugin.itemManager.getGhastGriefItem();
			event.getClickedInventory().setItem(event.getSlot(), newGhastGriefItem);
		}
		

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		event.setCancelled(true);
	}
}
