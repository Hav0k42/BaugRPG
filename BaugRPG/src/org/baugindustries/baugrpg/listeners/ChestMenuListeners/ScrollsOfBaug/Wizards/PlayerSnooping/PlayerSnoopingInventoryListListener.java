package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
					
						
						
					int page = 0;
					String lastChar = event.getView().getTitle().charAt(event.getView().getTitle().length() - 1) + "";
					if (plugin.isInteger(lastChar)) {
						page = Integer.parseInt(lastChar);
					}
						
						
						
						
					if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {//Open the previous menu
						player.openInventory(plugin.inventoryManager.getInventorySnoopingHubMenuInventory());
					} else if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {//View Selected Player's Inventory
						ItemStack selectedPlayerHead = event.getCurrentItem();
						SkullMeta selectedPlayerHeadMeta = (SkullMeta)selectedPlayerHead.getItemMeta();
						OfflinePlayer selectedOfflinePlayer = plugin.getServer().getOfflinePlayer(selectedPlayerHeadMeta.getOwningPlayer().getUniqueId());//theres a possibility this does not work.
						
						player.performCommand("oi " + selectedOfflinePlayer.getName());
					}
					if (event.getCurrentItem().equals(plugin.itemManager.getNextPageItem()) ) {
						player.openInventory(plugin.inventoryManager.getInventorySnoopingInventoryMenuInventory(player, page + 1));
					}
					
					if (event.getCurrentItem().equals(plugin.itemManager.getPreviousPageItem()) ) {
						player.openInventory(plugin.inventoryManager.getInventorySnoopingInventoryMenuInventory(player, page - 1));
					}
					event.setCancelled(true);
				}
			}
		}
	}
}

