package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugElvesInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugElvesInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().equals(plugin.inventoryManager.getRaceColor(2) + "Scrolls of Baug")) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		if (race != 2) return;
		
		ItemStack sharedInventoriesItem = plugin.itemManager.getCommunistHubItem();
			
			if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
			
				if (event.getCurrentItem().equals(sharedInventoriesItem)) {//Player clicked on the Shared Inventory Chest
					player.openInventory(plugin.inventoryManager.getElvesCommunistHubMenuInventory());
				}
				
				
			} else {
				player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease contact the admins and ask them to install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
			}
			
			
			ItemStack skillTreeItem = plugin.itemManager.getSkillTreeMenuItem();
			
			if (event.getCurrentItem().equals(skillTreeItem)) {
				
				File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
			 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				
				if (skillsconfig.getInt("totalSkillPoints") > 19) {
					player.openInventory(plugin.inventoryManager.getSkillTreeMenuInventory(player));
				} else {
					player.openInventory(plugin.inventoryManager.getGeneralSkillTreeMenuInventory(player));
				}
				
				
			} else if (event.getCurrentItem().equals(plugin.itemManager.getGovernmentMenuItem(2))) {
				player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
			}
			
			
			event.setCancelled(true);
		}
	}

