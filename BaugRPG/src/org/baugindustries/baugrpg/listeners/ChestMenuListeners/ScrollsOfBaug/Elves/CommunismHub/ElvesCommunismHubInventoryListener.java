package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.CommunismHub;

import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ElvesCommunismHubInventoryListener implements Listener {

	private Main plugin;
	public ElvesCommunismHubInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Elves Communism Hub")) {
					
						
						if (event.getCurrentItem().equals(plugin.itemManager.getCommunistInventoryItem())) {//Player wants to access other elves' inventories.
							player.openInventory(plugin.inventoryManager.getElvesCommunistInventoryMenuInventory(player));
						} else if (event.getCurrentItem().equals(plugin.itemManager.getCommunistEnderChestItem())) {//Player wants to access other elves' ender chests.
							player.openInventory(plugin.inventoryManager.getElvesCommunistEnderChestMenuInventory(player));
						} else if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
							player.performCommand("baugscroll");
						}
						
						
						event.setCancelled(true);
					}
				}
			}
		}
	}

