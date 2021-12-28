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

public class PlayerSnoopingEnderChestListListener implements Listener{

	
	private Main plugin;
	public PlayerSnoopingEnderChestListListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Players Ender Chests")) {
						
						if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {//Open the previous menu
							player.openInventory(plugin.inventoryManager.getInventorySnoopingHubMenuInventory());
						} else if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
							ItemStack selectedPlayerHead = event.getCurrentItem();
							SkullMeta selectedPlayerHeadMeta = (SkullMeta)selectedPlayerHead.getItemMeta();
							OfflinePlayer selectedOfflinePlayer = plugin.getServer().getOfflinePlayer(selectedPlayerHeadMeta.getOwningPlayer().getUniqueId());//theres a possibility this does not work.
							
							player.performCommand("oe " + selectedOfflinePlayer.getName());
						}
						event.setCancelled(true);
					}
				}
			}
		}
	}

