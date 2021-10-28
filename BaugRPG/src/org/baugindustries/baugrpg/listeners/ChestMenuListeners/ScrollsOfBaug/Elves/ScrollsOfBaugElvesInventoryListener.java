package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub.ElvesCommunismHubInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugElvesInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugElvesInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Scrolls of Baug")) {
					
					ItemStack sharedInventoriesItem = plugin.createItem(Material.CHEST, 1, ChatColor.GOLD + "Shared Inventories", Arrays.asList(ChatColor.LIGHT_PURPLE + "Access fellow Elves' inventories and ender chests"));
						
						if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
						
							if (event.getSlot() == 12 && event.getCurrentItem().equals(sharedInventoriesItem)) {//Player clicked on the Shared Inventory Chest
								int inventorySize = 9;
								String inventoryName = "Elves Communism Hub";
								Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
								
								
								for (int i = 0; i < inventorySize; i++) {
									inventory.setItem(i, new ItemStack(Material.AIR));
								}
	
								
								inventory.setItem(0, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Go Back", null));
								
								inventory.setItem(3, plugin.createItem(
										Material.CHEST, 
										1, 
										"Inventories", 
										Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other Elves' Inventories")));
								
								inventory.setItem(5, plugin.createItem(
										Material.ENDER_CHEST, 
										1, 
										"Ender Chests", 
										Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other Elves' Ender Chests")));
								
								event.getWhoClicked().openInventory(inventory);
								
								InventoryClickEvent.getHandlerList().unregister(this);
								plugin.getServer().getPluginManager().registerEvents(new ElvesCommunismHubInventoryListener(plugin), plugin);
							}
							
							
						} else {
							player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease contact the admins and ask them to install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
						}
						
						
						event.setCancelled(true);
					}
				}
			}
		}
		
	}

