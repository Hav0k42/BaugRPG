package org.baugindustries.baugrpg.listeners;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
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
					
						
						if (event.getSlot() == 11) {//Player clicked on the Shared Inventory Chest
							int inventorySize = 9;
							String inventoryName = "Elves Communism Hub";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}

							ItemStack backItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
							ItemMeta backItemMeta = backItem.getItemMeta();
							backItemMeta.setDisplayName("Go Back");
							backItem.setItemMeta(backItemMeta);
							inventory.setItem(0, backItem);
							
							ItemStack openInventoriesItem = new ItemStack(Material.CHEST);
							ItemMeta openInventoriesItemMeta = openInventoriesItem.getItemMeta();
							openInventoriesItemMeta.setDisplayName("Inventories");
							List<String> openInventoriesItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other Elves' Inventories");
							openInventoriesItemMeta.setLore(openInventoriesItemLore);
							openInventoriesItem.setItemMeta(openInventoriesItemMeta);
							inventory.setItem(3, openInventoriesItem);
							
							ItemStack openEnderChestsItem = new ItemStack(Material.ENDER_CHEST);
							ItemMeta openEnderChestsItemMeta = openEnderChestsItem.getItemMeta();
							openEnderChestsItemMeta.setDisplayName("Ender Chests");
							List<String> openEnderChestsItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other Elves' Ender Chests");
							openEnderChestsItemMeta.setLore(openEnderChestsItemLore);
							openEnderChestsItem.setItemMeta(openEnderChestsItemMeta);
							inventory.setItem(5, openEnderChestsItem);
							
							event.getWhoClicked().openInventory(inventory);
							
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new ElvesCommunismHubInventoryListener(plugin), plugin);
						}
						
						
						event.setCancelled(true);
					}
				}
			}
		}
		
	}

