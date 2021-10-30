package org.baugindustries.baugrpg.listeners;

import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.FeatureManagement;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class PlayerCloseInventoryListener implements Listener{
	
	private Main plugin;
	public PlayerCloseInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void leave(InventoryCloseEvent event) {
//		//TODO: Check if any players are looking at an inventory, then only do this if they're not
//		boolean temp = false;
//		List<Player> allOnlinePlayers = plugin.getOnlinePlayers();
//		for (int i = 0; i < allOnlinePlayers.size(); i++) {
//			if (allOnlinePlayers.get(i).getInventory().getType() == InventoryType.CHEST) {
//				temp = true;
//			}
//		}
//		if (!temp) {
//			for (int i = 0; i < InventoryClickEvent.getHandlerList().getRegisteredListeners().length; i++) {
//				InventoryClickEvent.getHandlerList().unregister(InventoryClickEvent.getHandlerList().getRegisteredListeners()[i]);
//			}
//		}
		Player player = (Player)event.getPlayer();
		if (event.getView().getTitle().equals("Dwarven Gold Deposit") && event.getView().getTopInventory().getSize() == 9) {
			if (event.getView().getTopInventory().getItem(3) != null && !event.getView().getTopInventory().getItem(3).getType().equals(Material.GOLD_INGOT)) {
				player.getWorld().dropItem(player.getLocation(), event.getView().getTopInventory().getItem(3));
			}
		} else if (event.getView().getTitle().equals("Feature Management") && event.getView().getTopInventory().getSize() == 54) {
			InventoryClickEvent.getHandlerList().unregister(plugin.featureManagement);
		}
    }
}
