package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves;

import java.io.File;
import java.util.Arrays;

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
		if (!event.getView().getTitle().equals("Scrolls of Baug")) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		if (race != 2) return;
		
		ItemStack sharedInventoriesItem = plugin.itemManager.getCommunistHubItem();
			
			if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
			
				if (event.getSlot() == 12 && event.getCurrentItem().equals(sharedInventoriesItem)) {//Player clicked on the Shared Inventory Chest
					int inventorySize = 9;
					String inventoryName = "Elves Communism Hub";
					Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
					
					
					for (int i = 0; i < inventorySize; i++) {
						inventory.setItem(i, new ItemStack(Material.AIR));
					}

					
					inventory.setItem(0, plugin.itemManager.getBackItem());
					
					inventory.setItem(3, plugin.itemManager.getCommunistInventoryItem());
					
					inventory.setItem(5, plugin.itemManager.getCommunistEnderChestItem());
					
					event.getWhoClicked().openInventory(inventory);
					
				}
				
				
			} else {
				player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease contact the admins and ask them to install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
			}
			
			
			ItemStack skillTreeItem = plugin.itemManager.getSkillTreeMenuItem();
			
			if (event.getSlot() == 11 && event.getCurrentItem().equals(skillTreeItem)) {
				
				File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
			 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				
				if (skillsconfig.getInt("totalSkillPoints") > 19) {
					int inventorySize = 9;
					String inventoryName = "Skill Trees";
					Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
					
					inventory.setItem(0, plugin.itemManager.getBackItem());
					
					inventory.setItem(3, plugin.itemManager.getGeneralSkillTreeMenuItem());
					
					inventory.setItem(5, plugin.itemManager.getRaceSkillTreeMenuItem(player));
					
					event.getWhoClicked().openInventory(inventory);
				} else {
					int inventorySize = 54;
					String inventoryName = "General Skills";
					Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
					
					
					inventory.setItem(0, plugin.itemManager.getGeneralSkillTreeInfoItem());
					
					String enabledString = "";
					
					String[] skillNames = {"speed", "jump", "damage", "resistance", "mining", "regen", "swim"};
					String[] skillDisplayNames = {"Speed", "Jump Height", "Attack Damage", "Resistance", "Mining Speed", "Regeneration", "Swim Speed"};
					String[] upgradeText = {"movement speed", "jump height", "attack damage", "damage resistance", "mining speed", "regeneration speed", "swim speed"};
					Material[] skillMats = {Material.FEATHER, Material.RABBIT_FOOT, Material.IRON_AXE, Material.SHIELD, Material.IRON_PICKAXE, Material.APPLE, Material.HEART_OF_THE_SEA};
					
					for (int i = 0; i < skillNames.length; i++) {
						if (skillsconfig.getBoolean(skillNames[i] + "On")) {
							enabledString = ChatColor.GREEN + "ENABLED";
						} else {
							enabledString = ChatColor.RED + "DISABLED";
						}
						
						
						inventory.setItem(i + 1, plugin.createItem(
								skillMats[i],
								1,
								ChatColor.GOLD + skillDisplayNames[i], 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade " + upgradeText[i] + ".", "Click to toggle: " + enabledString)));
					}
					
					inventory.setItem(9, plugin.createItem(
							Material.BOOK,
							1,
							ChatColor.GOLD + "Available Points", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "" + skillsconfig.get("skillPoints"))));
					
					inventory.setItem(45, plugin.itemManager.getBackItem());
					
					ItemStack blankItem = plugin.itemManager.getBlankItem();
					ItemStack lockedItem = plugin.itemManager.getLockedItem();
					ItemStack ownedItem = plugin.itemManager.getUpgradedItem();
					
					for (int i = 0; i < 10; i++) {
						if (i % 9 == 8) {
							inventory.setItem(i, blankItem);
						}
					}
					
					for (int i = 10; i < 18; i++) {
						inventory.setItem(i, blankItem);
					}
					
					for (int i = 18; i < 45; i++) {
						if (i % 9 == 8 || i % 9 == 0) {
							inventory.setItem(i, blankItem);
						} else {
							inventory.setItem(i, lockedItem);
						}
					}
					
					for (int i = 45; i < 54; i++) {
						if (i % 9 == 8) {
							inventory.setItem(i, blankItem);
						} else if (i % 9 != 0) {
							inventory.setItem(i, lockedItem);
						}
					}
					
					ItemStack[] currentItem = new ItemStack[7];
					currentItem[0] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.WHITE + "Upgrade speed: Lvl " + (skillsconfig.getInt("speed") + 1));
					currentItem[1] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Upgrade jump: Lvl " + (skillsconfig.getInt("jump") + 1));
					currentItem[2] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_RED + "Upgrade attack damage: Lvl " + (skillsconfig.getInt("damage") + 1));
					currentItem[3] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_AQUA + "Upgrade resistance: Lvl " + (skillsconfig.getInt("resistance") + 1));
					currentItem[4] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.YELLOW + "Upgrade mining speed: Lvl " + (skillsconfig.getInt("mining") + 1));
					currentItem[5] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.RED + "Upgrade regeneration: Lvl " + (skillsconfig.getInt("regen") + 1));
					currentItem[6] = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.AQUA + "Upgrade swim speed: Lvl " + (skillsconfig.getInt("swim") + 1));
					
					
					for (int i = 0; i < currentItem.length; i++) {
						int levelInt = skillsconfig.getInt(skillNames[i]);
						int rowShifter = i;
						if (levelInt == 0) {
							inventory.setItem(46 + rowShifter, currentItem[i]);
						} else if (levelInt > 0 && levelInt < 5) {
							inventory.setItem(46 + rowShifter, ownedItem);
							inventory.setItem(37 + rowShifter, currentItem[i]);
						} else if (levelInt > 4 && levelInt < 9) {
							inventory.setItem(46 + rowShifter, ownedItem);
							inventory.setItem(37 + rowShifter, ownedItem);
							inventory.setItem(28 + rowShifter, currentItem[i]);
						} else if (levelInt == 9) {
							inventory.setItem(46 + rowShifter, ownedItem);
							inventory.setItem(37 + rowShifter, ownedItem);
							inventory.setItem(28 + rowShifter, ownedItem);
							inventory.setItem(19 + rowShifter, currentItem[i]);
						} else {
							inventory.setItem(46 + rowShifter, ownedItem);
							inventory.setItem(37 + rowShifter, ownedItem);
							inventory.setItem(28 + rowShifter, ownedItem);
							inventory.setItem(19 + rowShifter, ownedItem);
						}
					}
					
					
					
					event.getWhoClicked().openInventory(inventory);
				}
				
				
			}
			
			
			event.setCancelled(true);
		}
	}

