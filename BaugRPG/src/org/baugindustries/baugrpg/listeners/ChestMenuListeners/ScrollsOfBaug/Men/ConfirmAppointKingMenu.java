package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;

public class ConfirmAppointKingMenu implements Listener {
	private Main plugin;
	public ConfirmAppointKingMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().contains(ChatColor.DARK_AQUA + "Confirm Appointment")) return;
		Player player = (Player)event.getWhoClicked();

		
		
		if (event.getCurrentItem().equals(plugin.itemManager.getYesItem())) {
			OfflinePlayer newKing = ((SkullMeta)event.getClickedInventory().getItem(4).getItemMeta()).getOwningPlayer();

			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
			
			leaderConfig.set("menLeaderUUID", newKing.getUniqueId().toString());
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.closeInventory();
			player.sendMessage(ChatColor.YELLOW + "You have appointed " + ChatColor.DARK_AQUA + newKing.getName() + ChatColor.YELLOW + " as your new King.");
		
			if (newKing.isOnline()) {
				((Player)newKing).sendMessage(ChatColor.DARK_AQUA + "You have been appointed King. Lead your people using /bs.");
			}
		} else if (event.getCurrentItem().equals(plugin.itemManager.getNoItem())) {
			player.openInventory(plugin.inventoryManager.getAppointKingMenu(player.getUniqueId(), 0));
		}
		
		event.setCancelled(true);
	}
}
