package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping;

import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
					
						
						if (event.getCurrentItem().equals(plugin.itemManager.getInventorySnoopingInventoryItem())) {//Player wants to access other players' inventories.
							player.openInventory(plugin.inventoryManager.getInventorySnoopingInventoryMenuInventory(player));
						} else if (event.getCurrentItem().equals(plugin.itemManager.getInventorySnoopingEnderChestItem())) {//Player wants to access other players' ender chests.
							player.openInventory(plugin.inventoryManager.getInventorySnoopingEnderChestMenuInventory(player));
						} else if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
							player.performCommand("baugscroll");
						}
						
						
						event.setCancelled(true);
					}
				}
			}
		}
	}

