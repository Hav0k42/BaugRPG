package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SkillTreeMenu implements Listener{
	private Main plugin;
	public SkillTreeMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Skill Trees")) {
					ItemStack generalSkillsItem = plugin.itemManager.getGeneralSkillTreeMenuItem();
					
					
					
					ItemStack backItem = plugin.itemManager.getBackItem();
					
					if (event.getSlot() == 0 && event.getCurrentItem().equals(backItem)) {//back to baug scroll
						player.performCommand("baugscroll");
					} else if (event.getSlot() == 5 && event.getClickedInventory().getItem(3).equals(generalSkillsItem)) {//class skills
						if (skillsconfig.getString("class") == null) {
							player.openInventory(plugin.inventoryManager.getChooseRaceSkillTreeMenuInventory(player));
						} else {
							player.openInventory(plugin.inventoryManager.getRaceSkillTreeMenuInventory(player));
						}
					} else if (event.getSlot() == 3 && event.getCurrentItem().equals(generalSkillsItem)) {//general skills
						player.openInventory(plugin.inventoryManager.getGeneralSkillTreeMenuInventory(player));
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	
	void skillPaneSetter (ItemStack currentItem, int levelInt, int rowShifter) {
		
	}
}
