package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerCloseInventoryListener implements Listener{
	
	private Main plugin;
	public PlayerCloseInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void leave(InventoryCloseEvent event) {
		for (int i = 0; i < InventoryClickEvent.getHandlerList().getRegisteredListeners().length; i++) {
			InventoryClickEvent.getHandlerList().unregister(InventoryClickEvent.getHandlerList().getRegisteredListeners()[i]);
		}
    }
}
