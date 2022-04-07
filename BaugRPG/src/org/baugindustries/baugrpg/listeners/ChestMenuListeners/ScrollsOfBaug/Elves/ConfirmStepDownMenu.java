package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfirmStepDownMenu implements Listener {
	private Main plugin;
	public ConfirmStepDownMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().contains(ChatColor.DARK_GREEN + "Confirm Stepping Down")) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		
		
		
		if (event.getCurrentItem().equals(plugin.itemManager.getYesItem())) {
			
			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
			
			leaderConfig.set("elfElectionStartTime", System.currentTimeMillis());
			leaderConfig.set("elfElectionActive", true);
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.closeInventory();
			
		} else if (event.getCurrentItem().equals(plugin.itemManager.getNoItem())) {
			player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
		}
		
		event.setCancelled(true);
	}
}
