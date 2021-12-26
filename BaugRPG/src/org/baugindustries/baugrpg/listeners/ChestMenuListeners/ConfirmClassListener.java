package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
		
		Player player = (Player)event.getWhoClicked();
		if (event.getSlot() == 15 && event.getCurrentItem().equals(plugin.itemManager.getNoItem())) {//No
			PersistentDataContainer data = player.getPersistentDataContainer();
			int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			int inventorySize = 27;
			String inventoryName = "Choose your Class";
			Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
			switch (race) {
				case 1:
					inventory.setItem(11, plugin.itemManager.getStableMasterItem());
					
					inventory.setItem(13, plugin.itemManager.getSteeledArmorerItem());
					
					inventory.setItem(15, plugin.itemManager.getVerdantShepherdItem());
					break;
					
				case 2:
					inventory.setItem(11, plugin.itemManager.getEnchantedBotanistItem());
					
					inventory.setItem(13, plugin.itemManager.getLunarArtificerItem());
					
					inventory.setItem(15, plugin.itemManager.getWoodlandCraftsmanItem());
					break;
					
				case 3:
					inventory.setItem(11, plugin.itemManager.getRadiantMetallurgistItem());
					
					inventory.setItem(13, plugin.itemManager.getArcaneJewelerItem());
					
					inventory.setItem(15, plugin.itemManager.getGildedMinerItem());
					break;
					
				case 4:
					inventory.setItem(11, plugin.itemManager.getDarkAlchemistItem());
					
					inventory.setItem(13, plugin.itemManager.getEnragedBerserkerItem());
					
					inventory.setItem(15, plugin.itemManager.getGreedyScrapperItem());
					break;
			}
			
			player.openInventory(inventory);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			player.closeInventory();
		}
		event.setCancelled(true);
		
	}
}