package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ConfirmClassListener implements Listener {

	
	private Main plugin;
	public ConfirmClassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() == null) return;
		if (!event.getView().getTitle().equals("Confirm Class Selection")) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		if (event.getSlot() == 15 && event.getCurrentItem().equals(plugin.itemManager.getNoItem())) {//No
			player.openInventory(plugin.inventoryManager.getChooseRaceSkillTreeMenuInventory(player));
		} else if (event.getSlot() == 11 && event.getCurrentItem().equals(plugin.itemManager.getYesItem())) {//Yes
			ItemMeta raceInfoMeta = event.getInventory().getItem(4).getItemMeta();
			String selectedClass = raceInfoMeta.getLore().get(0);
			selectedClass = selectedClass.substring(36, selectedClass.length() - 1);
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		 	
		 	player.sendMessage(ChatColor.YELLOW + "You are now an apprentice " + selectedClass + ".\nYou have unlocked new skills and custom crafting items.");
		 	
		 	skillsconfig.set("class", selectedClass);
		 	try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.closeInventory();
		}
		event.setCancelled(true);
		
	}
}