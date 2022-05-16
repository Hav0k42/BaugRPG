package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.md_5.bungee.api.ChatColor;

public class CustomItemClassListener implements Listener {

	
	private Main plugin;
	public CustomItemClassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		if (!event.getView().getTitle().equals(ChatColor.GOLD + "Class Recipes")) return;
		Player player = (Player)event.getWhoClicked();
		
		event.setCancelled(true);
		
		if (event.getCurrentItem() == null) return;
		
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getBaugScrollMenuInventory(player));
			return;
		}
		
		String[] words = event.getCurrentItem().getItemMeta().getDisplayName().substring(2).split(" ");
		if (words.length < 2) return;
		String name = words[0].toUpperCase() + "_" + words[1].toUpperCase();
		
		if (Profession.valueOf(name) != null) {
			player.openInventory(plugin.inventoryManager.getAllRecipesMenu(Profession.valueOf(name)));
		}
		
		
	}
}
