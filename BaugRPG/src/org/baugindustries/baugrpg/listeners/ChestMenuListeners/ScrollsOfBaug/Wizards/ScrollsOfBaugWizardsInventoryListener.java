package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugWizardsInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugWizardsInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Scrolls of Baug")) {
					
					
					
					
					
					
					if (event.getSlot() == 11 && event.getCurrentItem().equals(plugin.createItem(
					Material.WRITABLE_BOOK,
					1,
					ChatColor.AQUA + "Feature Management", 
					Arrays.asList(ChatColor.LIGHT_PURPLE + "Turn certain features on and off,", "according to how you wish to run your server.")))) {//Player clicked on the Feature Management Item
						
						File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
					 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
						
					 	if (!config.contains("allowTpa")) {
					 		config.set("allowTpa", true);
					 		try {
								config.save(file);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					 	}
						
						int inventorySize = 54;
						String inventoryName = "Feature Management";
						Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
						
						inventory.setItem(11, plugin.createItem(
								Material.ENDER_EYE, 
								1, 
								"TPA", 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Allow Players to teleport.", config.get("allowTpa").toString())));
						
						event.getWhoClicked().openInventory(inventory);
						
					} else if (event.getSlot() == 12 && event.getCurrentItem().equals(plugin.createItem(
					Material.CHEST,
					1,
					ChatColor.AQUA + "Inventory Snooping", 
					Arrays.asList(ChatColor.LIGHT_PURPLE +  "Access every players' inventories and ender chests")))) {//Player clicked on the Inventory Snooping Item
						
						if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
							int inventorySize = 9;
							String inventoryName = "Inventory Snooping Hub";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}
	
							
							inventory.setItem(0, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Go Back", null));
							
							inventory.setItem(3, plugin.createItem(
									Material.CHEST, 
									1, 
									"Inventories", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Inventories")));
							
							inventory.setItem(5, plugin.createItem(
									Material.ENDER_CHEST, 
									1, 
									"Ender Chests", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Ender Chests")));
							
							event.getWhoClicked().openInventory(inventory);
							
						} else {
							player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
						}
					}
					
					
					event.setCancelled(true);
				}
			}
		}
		
	}
}
