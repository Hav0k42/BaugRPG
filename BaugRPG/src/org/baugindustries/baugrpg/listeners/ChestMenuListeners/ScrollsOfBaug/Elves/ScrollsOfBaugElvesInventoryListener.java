package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.ChatColor;

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
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		if (race != 2) return;
		
		ItemStack sharedInventoriesItem = plugin.itemManager.getCommunistHubItem();
			
			
				if (event.getCurrentItem().equals(sharedInventoriesItem)) {//Player clicked on the Shared Inventory Chest
					if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
						
					ConfigurationSection guiltyElvesCooldown;
					File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
					FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
					if (leaderConfig.contains("guiltyElvesCooldown")) {
						guiltyElvesCooldown = leaderConfig.getConfigurationSection("guiltyElvesCooldown");
					} else {
						guiltyElvesCooldown = leaderConfig.createSection("guiltyElvesCooldown");
					}
					
					if (!guiltyElvesCooldown.contains(player.getUniqueId().toString())) {
						player.openInventory(plugin.inventoryManager.getElvesCommunistHubMenuInventory());
					} else {
						long cooldown = guiltyElvesCooldown.getLong(player.getUniqueId().toString());
						if (cooldown > System.currentTimeMillis()) {
							//locked out
							int minutesToMillis = 60000;
							Long timeRemaining = cooldown - System.currentTimeMillis();
							String timeString = "";
							int timeValue;
							if (timeRemaining > minutesToMillis * 60) {
								//display in hours
								timeValue = (int)(timeRemaining / (minutesToMillis * 60));
								if (timeValue == 1) {
									timeString = (timeValue + " hour remaining");
								} else {
									timeString = (timeValue + " hours remaining");
								}
							} else if (timeRemaining > minutesToMillis) {
								//display in minutes
								timeValue = (int)(timeRemaining / minutesToMillis);
								if (timeValue == 1) {
									timeString = (timeValue + " minute remaining");
								} else {
									timeString = (timeValue + " minutes remaining");
								}
							} else {
								//display in seconds
								timeValue = (int)(timeRemaining / 1000);
								if (timeValue == 1) {
									timeString = (timeValue + " second remaining");
								} else {
									timeString = (timeValue + " seconds remaining");
								}
							}
							player.closeInventory();
							player.sendMessage(ChatColor.RED + "You committed a crime, and as such have been restricted from accessing player's inventories. " + timeString + " until you can access them again.");
						} else {
							//not locked out
							guiltyElvesCooldown.set(player.getUniqueId().toString(), null);
							player.openInventory(plugin.inventoryManager.getElvesCommunistHubMenuInventory());
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease contact the admins and ask them to install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
				}
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
			} else if (event.getCurrentItem().equals(plugin.itemManager.getViewLearnedRecipesItem(2))) {
				player.openInventory(plugin.inventoryManager.getLearnedRecipesMenu(player.getUniqueId()));
			}
			
			
			event.setCancelled(true);
		}
	}

