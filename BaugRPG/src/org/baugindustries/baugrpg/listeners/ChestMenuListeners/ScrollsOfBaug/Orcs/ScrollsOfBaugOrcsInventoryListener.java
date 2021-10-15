package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Orcs;

import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ScrollsOfBaugOrcsInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugOrcsInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Scrolls of Baug")) {
					
						event.setCancelled(true);
					
				}
			}
		}
		
	}
}
