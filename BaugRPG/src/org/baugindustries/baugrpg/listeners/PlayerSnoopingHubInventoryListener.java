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

public class PlayerSnoopingHubInventoryListener implements Listener {

	private Main plugin;
	public PlayerSnoopingHubInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Inventory Snooping Hub")) {
					
					
						//Get a list of all elves, both online and offline. Sort alphabetically.
						//Then create an itemstack in a forloop for the number of elves there are, load that into an itemstack List
						//Then, use that list of itemstacks to create an inventory menu that displays each elf's head side by side for simple access.
					
						OfflinePlayer[] allOfflinePlayers = plugin.getServer().getOfflinePlayers();
						
						
						
						
						List<ItemStack> playerHeads = new ArrayList<ItemStack>();
						for (int i = 0; i < allOfflinePlayers.length; i++) {
							ItemStack tempPlayerHead = new ItemStack(Material.PLAYER_HEAD);
							SkullMeta tempPlayerHeadMeta = (SkullMeta)tempPlayerHead.getItemMeta();
							List<String> tempPlayerHeadLore = Arrays.asList(ChatColor.YELLOW + "View " + allOfflinePlayers[i].getName() + "'s inventory");
							tempPlayerHeadMeta.setLore(tempPlayerHeadLore);
							tempPlayerHeadMeta.setDisplayName(allOfflinePlayers[i].getName());
							tempPlayerHeadMeta.setOwningPlayer(allOfflinePlayers[i]);
							tempPlayerHead.setItemMeta(tempPlayerHeadMeta);
							if (!player.getUniqueId().equals(allOfflinePlayers[i].getUniqueId())) {
								playerHeads.add(tempPlayerHead);
							}
						}
						
						
						ItemStack backItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
						ItemMeta backItemMeta = backItem.getItemMeta();
						backItemMeta.setDisplayName("Go Back");
						backItem.setItemMeta(backItemMeta);
						
						
						int inventorySize = 18;
						if (playerHeads.size() > 9) {
							inventorySize = 27;
						}
						if (playerHeads.size() > 18) {
							inventorySize = 36;
						}
						if (playerHeads.size() > 27) {
							inventorySize = 45;
						}
						if (playerHeads.size() > 36) {
							inventorySize = 54;
						}
						
						
						
						if (event.getCurrentItem().equals(plugin.createItem(Material.CHEST, 1, "Inventories", Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Inventories")))) {//Player wants to access other players' inventories.
							String inventoryName = "Players Inventories";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}
							
							
							for (int i = 0; i < playerHeads.size(); i++) {
								inventory.setItem(i, playerHeads.get(i));
								if (i > 45) {
									break;
								}
							}
							
							
							
							
							if (playerHeads.size() > 45) {
								inventory.setItem(46, backItem);
								//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
							} else if (playerHeads.size() > 36) {
								inventory.setItem(46, backItem);
							} else if (playerHeads.size() > 27) {
								inventory.setItem(37, backItem);
							} else if (playerHeads.size() > 18) {
								inventory.setItem(28, backItem);
							} else if (playerHeads.size() > 9) {
								inventory.setItem(19, backItem);
							} else {
								inventory.setItem(10, backItem);
							}
							
							event.getWhoClicked().openInventory(inventory);
							
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new PlayerSnoopingInventoryListListener(plugin), plugin);
							
							
						} else if (event.getCurrentItem().equals(plugin.createItem(Material.ENDER_CHEST, 1, "Ender Chests", Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Ender Chests")))) {//Player wants to access other players' ender chests.
							
							String inventoryName = "Players Ender Chests";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}
							
							
							for (int i = 0; i < playerHeads.size(); i++) {
								inventory.setItem(i, playerHeads.get(i));
								if (i > 45) {
									break;
								}
							}
							
							
							
							
							if (playerHeads.size() > 45) {
								inventory.setItem(46, backItem);
								//add menu to go to the next page of players, also, figure out how to get multiple pages of elves if for some reason theres a server with more than 45 players.
							} else if (playerHeads.size() > 36) {
								inventory.setItem(46, backItem);
							} else if (playerHeads.size() > 27) {
								inventory.setItem(37, backItem);
							} else if (playerHeads.size() > 18) {
								inventory.setItem(28, backItem);
							} else if (playerHeads.size() > 9) {
								inventory.setItem(19, backItem);
							} else {
								inventory.setItem(10, backItem);
							}
							event.getWhoClicked().openInventory(inventory);
							
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new PlayerSnoopingEnderChestListListener(plugin), plugin);
							
							
						
						} else if (event.getCurrentItem().equals(plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Go Back", null))) {
							player.performCommand("baugscroll");
						}
						
						
						event.setCancelled(true);
					}
				}
			}
		}
	}

