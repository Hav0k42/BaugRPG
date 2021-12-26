package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub;

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
import org.bukkit.inventory.meta.SkullMeta;

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
					
						
						
						if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {//Open the previous menu
							int inventorySize = 9;
							String inventoryName = "Elves Communism Hub";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}

							
							inventory.setItem(0, plugin.itemManager.getBackItem());
							
							inventory.setItem(3, plugin.itemManager.getCommunistInventoryItem());
							
							inventory.setItem(5, plugin.itemManager.getCommunistEnderChestItem());
							
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

