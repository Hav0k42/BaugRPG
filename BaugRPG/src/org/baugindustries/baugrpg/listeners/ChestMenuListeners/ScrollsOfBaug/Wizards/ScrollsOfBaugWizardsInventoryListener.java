package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugWizardsInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugWizardsInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().equals(ChatColor.AQUA + "Scrolls of Baug")) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		if (race != 5) return;
					
					
					
					
					
		if (event.getCurrentItem().equals(plugin.itemManager.getFeatureManagementItem())) {//Player clicked on the Feature Management Item
			
			event.getWhoClicked().openInventory(plugin.inventoryManager.getFeatureManagementInventory());
			
		} else if (event.getCurrentItem().equals(plugin.itemManager.getInventorySnoopingItem())) {//Player clicked on the Inventory Snooping Item
			
			if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
				player.openInventory(plugin.inventoryManager.getInventorySnoopingHubMenuInventory());
			} else {
				player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
			}
		}
		
		
		event.setCancelled(true);
	}
	
	
}
