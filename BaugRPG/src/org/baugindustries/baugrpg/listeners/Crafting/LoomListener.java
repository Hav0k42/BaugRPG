package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import net.md_5.bungee.api.ChatColor;

public class LoomListener implements Listener {
	private Main plugin;
	
	public LoomListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEquipMorral(InventoryClickEvent event) {

		if (!event.getInventory().getType().equals(InventoryType.LOOM)) return;
		if (event.getSlot() != 3) return;
		if (event.getInventory().getItem(3) == null) return;
		
		if (event.getInventory().getItem(1) != null) {
			for (Recipes recipe : Recipes.values()) {
				if (recipe.matches(plugin, event.getInventory().getItem(1))) {
					event.setCancelled(true);
					event.getWhoClicked().sendMessage(ChatColor.RED + "That ingredient cannot be used for banners.");
					return;
				}
			}
		}
		
		
	}

}
