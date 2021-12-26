package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping;

import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class PlayerSnoopingInventoryListListener implements Listener{

	
	private Main plugin;
	public PlayerSnoopingInventoryListListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Players Inventories")) {
					
						
						
						
						
						
						
						
						ItemStack backItemTest = new ItemStack(Material.RED_STAINED_GLASS_PANE);
						ItemMeta backItemTestMeta = backItemTest.getItemMeta();
						backItemTestMeta.setDisplayName("Go Back");
						backItemTest.setItemMeta(backItemTestMeta);
						
						if (event.getCurrentItem().getItemMeta().equals(backItemTestMeta)) {//Open the previous menu
							int inventorySize = 9;
							String inventoryName = "Inventory Snooping Hub";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}
	
							
							inventory.setItem(0, plugin.itemManager.getBackItem());
							
							inventory.setItem(3, plugin.itemManager.getInventorySnoopingInventoryItem());
							
							inventory.setItem(5, plugin.itemManager.getInventorySnoopingEnderChestItem());
							
							event.getWhoClicked().openInventory(inventory);
							
						} else if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {//View Selected Player's Inventory
							ItemStack selectedPlayerHead = event.getCurrentItem();
							SkullMeta selectedPlayerHeadMeta = (SkullMeta)selectedPlayerHead.getItemMeta();
							OfflinePlayer selectedOfflinePlayer = plugin.getServer().getOfflinePlayer(selectedPlayerHeadMeta.getOwningPlayer().getUniqueId());//theres a possibility this does not work.
							
							player.performCommand("oi " + selectedOfflinePlayer.getName());
						}
						event.setCancelled(true);
					}
				}
			}
		}
	}

