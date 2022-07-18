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

public class FeatureManagement implements Listener{
	private Main plugin;
	public FeatureManagement(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() == null) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		if (!event.getView().getTitle().equals("Feature Management")) return;
		

		event.setCancelled(true);
		
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	 	
	 	if (event.getCurrentItem() == null) return;
	 	
		
		if (event.getCurrentItem().equals(plugin.itemManager.getTpaFeatureItem())) {
			config.set("allowTpa", !config.getBoolean("allowTpa"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getRecipeFeatureItem())) {
			config.set("allowRecipe", !config.getBoolean("allowRecipe"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getEndermanGriefItem())) {
			config.set("allowEndermanGriefing", !config.getBoolean("allowEndermanGriefing"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getCreeperGriefItem())) {
			config.set("allowCreeperGriefing", !config.getBoolean("allowCreeperGriefing"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getTntGriefItem())) {
			config.set("allowTntGriefing", !config.getBoolean("allowTntGriefing"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getGhastGriefItem())) {
			config.set("allowGhastGriefing", !config.getBoolean("allowGhastGriefing"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getMediumCoreDeathItem())) {
			config.set("mediumCoreDeathOn", !config.getBoolean("mediumCoreDeathOn"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getHardcoreDeathItem())) {
			config.set("hardcoreDeathOn", !config.getBoolean("hardcoreDeathOn"));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getHardcoreDeathItem())) {
			config.set("autoBalanceRaces", !config.getBoolean("autoBalanceRaces"));
		}
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		event.getWhoClicked().openInventory(plugin.inventoryManager.getFeatureManagementInventory());

		
		
		
		
	}
}
