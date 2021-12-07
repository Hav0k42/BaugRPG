package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerCloseInventoryListener implements Listener{
	

	public PlayerCloseInventoryListener(Main plugin) {
		
	}

	@EventHandler
	public void leave(InventoryCloseEvent event) {
		Player player = (Player)event.getPlayer();
		if (event.getView().getTitle().equals("Dwarven Gold Deposit") && event.getView().getTopInventory().getSize() == 9) {
			if (event.getView().getTopInventory().getItem(3) != null && !event.getView().getTopInventory().getItem(3).getType().equals(Material.GOLD_INGOT)) {
				player.getWorld().dropItem(player.getLocation(), event.getView().getTopInventory().getItem(3));
			}
		}
    }
}
