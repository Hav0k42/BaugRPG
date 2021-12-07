package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class FeatureManagement implements Listener{
	private Main plugin;
	public FeatureManagement(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Feature Management")) {
					File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
				 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				 	
					ItemStack tpaItem = plugin.createItem(
							Material.ENDER_EYE, 
							1, 
							"TPA", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to teleport.", config.get("allowTpa").toString()));
					
					if (event.getCurrentItem().equals(tpaItem)) {
						
						config.set("allowTpa", !(Boolean)config.get("allowTpa"));
						try {
							config.save(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						ItemStack newTpaItem = plugin.createItem(
								Material.ENDER_EYE, 
								1, 
								"TPA", 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to teleport.", config.get("allowTpa").toString()));
						event.getClickedInventory().setItem(event.getSlot(), newTpaItem);
					}
					event.setCancelled(true);
				}
			}
		}
	}
}
