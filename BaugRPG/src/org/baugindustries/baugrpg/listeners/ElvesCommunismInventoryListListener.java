package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ElvesCommunismInventoryListListener implements Listener{

	
	private Main plugin;
	public ElvesCommunismInventoryListListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Elves Inventories")) {
					
						
						
						List<OfflinePlayer> allOfflineElves = plugin.getAllOfflineElves();
						
						
						
						
						
						ItemStack backItemTest = new ItemStack(Material.RED_STAINED_GLASS_PANE);
						ItemMeta backItemTestMeta = backItemTest.getItemMeta();
						backItemTestMeta.setDisplayName("Go Back");
						backItemTest.setItemMeta(backItemTestMeta);
						
						if (event.getCurrentItem().getItemMeta().equals(backItemTestMeta)) {//Open the previous menu
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
						} else if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {//View Selected Player's Inventory
							ItemStack selectedPlayerHead = event.getCurrentItem();
							SkullMeta selectedPlayerHeadMeta = (SkullMeta)selectedPlayerHead.getItemMeta();
							OfflinePlayer selectedOfflinePlayer = plugin.getServer().getOfflinePlayer(selectedPlayerHeadMeta.getOwningPlayer().getUniqueId());//theres a possibility this does not work.
							
							InventoryClickEvent.getHandlerList().unregister(this);
							player.performCommand("oi " + selectedOfflinePlayer.getName());
						}
						event.setCancelled(true);
					}
				}
			}
		}
	}

