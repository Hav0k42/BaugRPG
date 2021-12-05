package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves;

import java.util.Arrays;

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

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugDwarvesInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugDwarvesInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Scrolls of Baug")) {
					
					ItemStack depositItem = plugin.createItem(
							Material.GOLD_INGOT,
							1,
							ChatColor.GOLD + "Bank Deposit",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Convert gold ingots to Dwarven Gold."));
					
					ItemStack skillTreeItem = plugin.createItem(
							Material.OAK_SAPLING,
							1,
							ChatColor.GOLD + "Skill Tree",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race."));
					
					if (event.getSlot() == 11 && event.getCurrentItem().equals(skillTreeItem)) {
						int inventorySize = 9;
						String inventoryName = "Skill Trees";
						Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
						
						inventory.setItem(0, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "Back"));
						
						inventory.setItem(3, plugin.createItem(
								Material.OAK_SAPLING,
								1, 
								ChatColor.GOLD + "General Skills", 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Passive skills every player can use.")));
						
						inventory.setItem(5, plugin.createItem(
								Material.NETHERITE_AXE,
								1, 
								ChatColor.GOLD + "Race Skills", 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Race specific skills that are more speciallized.")));
						
						event.getWhoClicked().openInventory(inventory);
						
						InventoryClickEvent.getHandlerList().unregister(this);
						plugin.getServer().getPluginManager().registerEvents(plugin.skillTreeMenu, plugin);
						
						
					} else if (event.getSlot() == 12 && event.getCurrentItem().equals(depositItem)) {
						int inventorySize = 9;
						String inventoryName = "Dwarven Gold Deposit";
						Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
						
						for (int i = 0; i < inventorySize; i++) {
							inventory.setItem(i, plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " "));
						}
						
						inventory.setItem(3, new ItemStack(Material.AIR));
						
						inventory.setItem(5, plugin.createItem(
								Material.LIME_STAINED_GLASS_PANE, 
								1, 
								ChatColor.GREEN + "CONFIRM", 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Place gold ingots to the left, ", "and click here to confirm conversion.")));
					
						event.getWhoClicked().openInventory(inventory);
						
						InventoryClickEvent.getHandlerList().unregister(this);
						plugin.getServer().getPluginManager().registerEvents(plugin.goldConversionMenu, plugin);
					
					}
						event.setCancelled(true);
					
				}
			}
		}
		
	}
}
