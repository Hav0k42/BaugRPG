package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;

public class AppointKingMenu implements Listener {
	private Main plugin;
	public AppointKingMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		if (!event.getView().getTitle().contains(ChatColor.DARK_AQUA + "Appoint King")) return;
		Player player = (Player)event.getWhoClicked();

		
		int page = 0;
		String lastChar = event.getView().getTitle().charAt(event.getView().getTitle().length() - 1) + "";
		if (plugin.isInteger(lastChar)) {
			page = Integer.parseInt(lastChar);
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getNextPageItem()) ) {
			player.openInventory(plugin.inventoryManager.getAppointKingMenu(player.getUniqueId(), page + 1));
		}
		
		if (event.getCurrentItem().equals(plugin.itemManager.getPreviousPageItem()) ) {
			player.openInventory(plugin.inventoryManager.getAppointKingMenu(player.getUniqueId(), page - 1));
		}
		
		if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD) && event.getCurrentItem().getItemMeta().getLore().get(0).contains(ChatColor.YELLOW + "Appoint ")) {
			player.openInventory(plugin.inventoryManager.getConfirmAppointKingMenu(((SkullMeta)event.getCurrentItem().getItemMeta()).getOwningPlayer().getUniqueId()));
		}
		
		event.setCancelled(true);
	}
}
