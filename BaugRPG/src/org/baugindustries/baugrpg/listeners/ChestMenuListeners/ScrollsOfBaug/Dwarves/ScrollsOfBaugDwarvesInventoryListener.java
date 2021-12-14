package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves;

import java.io.File;
import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugDwarvesInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugDwarvesInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			if (event.getClickedInventory() != null && event.getCurrentItem() != null) {
				if (event.getView().getTitle().equals("Scrolls of Baug")) {
					Player player = (Player) event.getWhoClicked();
					
					ItemStack depositItem = plugin.createItem(
							Material.GOLD_INGOT,
							1,
							ChatColor.GOLD + "Bank Deposit",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Convert gold ingots to Dwarven Gold."));
					
					ItemStack skillTreeItem = plugin.createItem(
							Material.OAK_SAPLING,
							1,
							ChatColor.GOLD + "Skill Tree",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race."));
					
					if (event.getSlot() == 11 && event.getCurrentItem().equals(skillTreeItem)) {
						
						File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
					 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
						
						if (skillsconfig.getInt("totalSkillPoints") > 19) {
							int inventorySize = 9;
							String inventoryName = "Skill Trees";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							inventory.setItem(0, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "Back"));
							
							inventory.setItem(3, plugin.createItem(
									Material.OAK_SAPLING,
									1, 
									ChatColor.GOLD + "General Skills", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Passive skills every player can use.")));
							
							
							if (skillsconfig.getString("class") == null) {
								inventory.setItem(5, plugin.createItem(
										Material.NETHERITE_AXE,
										1, 
										ChatColor.GOLD + "Race Skills", 
										Arrays.asList(ChatColor.LIGHT_PURPLE + "Choose your class.")));
							} else {
								String profession = skillsconfig.getString("class");
								Material professionMaterial = Material.NETHERITE_AXE;
								if (profession.equals("Radiant Metallurgist"))
									professionMaterial = Material.BLAST_FURNACE;
								if (profession.equals("Arcane Jeweller"))
									professionMaterial = Material.DIAMOND;
								if (profession.equals("Gilded Miner"))
									professionMaterial = Material.GOLDEN_PICKAXE;
								inventory.setItem(5, plugin.createItem(
										professionMaterial,
										1, 
										ChatColor.GOLD + profession + " Skills", 
										Arrays.asList(ChatColor.LIGHT_PURPLE + "Race specific skills that are more speciallized.")));
							}
							
							event.getWhoClicked().openInventory(inventory);
						} else {
							int inventorySize = 54;
							String inventoryName = "General Skills";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							inventory.setItem(0, plugin.createItem(
									Material.NETHER_STAR,
									1,
									ChatColor.GOLD + "General Skill Tree", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade different passive skills.")));
							
							String enabledString = "";
							
							
							if (skillsconfig.getBoolean("speedOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(1, plugin.createItem(
									Material.FEATHER,
									1,
									ChatColor.GOLD + "Speed", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade movement speed.", "Click to toggle: " + enabledString)));
							
							if (skillsconfig.getBoolean("jumpOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(2, plugin.createItem(
									Material.RABBIT_FOOT,
									1,
									ChatColor.GOLD + "Jump Height", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade jump height.", "Click to toggle: " + enabledString)));
							
							if (skillsconfig.getBoolean("damageOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(3, plugin.createItem(
									Material.IRON_AXE,
									1,
									ChatColor.GOLD + "Attack Damage", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade attack damage.", "Click to toggle: " + enabledString)));
							
							if (skillsconfig.getBoolean("resistanceOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(4, plugin.createItem(
									Material.SHIELD,
									1,
									ChatColor.GOLD + "Resistance", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade damage resistance", "Click to toggle: " + enabledString)));
							
							if (skillsconfig.getBoolean("miningOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(5, plugin.createItem(
									Material.IRON_PICKAXE,
									1,
									ChatColor.GOLD + "Mining Speed", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade mining speed.", "Click to toggle: " + enabledString)));
							
							if (skillsconfig.getBoolean("regenOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(6, plugin.createItem(
									Material.APPLE,
									1,
									ChatColor.GOLD + "Regeneration", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade regeration speed.", "Click to toggle: " + enabledString)));
							
							if (skillsconfig.getBoolean("swimOn")) {
								enabledString = ChatColor.GREEN + "ENABLED";
							} else {
								enabledString = ChatColor.RED + "DISABLED";
							}
							
							inventory.setItem(7, plugin.createItem(
									Material.HEART_OF_THE_SEA,
									1,
									ChatColor.GOLD + "Swim Speed", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade swim speed.", "Click to toggle: " + enabledString)));
							
							inventory.setItem(9, plugin.createItem(
									Material.BOOK,
									1,
									ChatColor.GOLD + "Available Points", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "" + skillsconfig.get("skillPoints"))));
							
							inventory.setItem(45, plugin.createItem(
									Material.RED_STAINED_GLASS_PANE,
									1,
									ChatColor.RED + "Back"));
							
							ItemStack blankItem = plugin.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, " ");
							ItemStack lockedItem = plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.DARK_GRAY + "LOCKED");
							ItemStack ownedItem = plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "UPGRADED");
							
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
							
							ItemStack currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.WHITE + "Upgrade speed: Lvl " + (skillsconfig.getInt("speed") + 1));
							int levelInt = skillsconfig.getInt("speed");
							int rowShifter = 0;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Upgrade jump: Lvl " + (skillsconfig.getInt("jump") + 1));
							levelInt = skillsconfig.getInt("jump");
							rowShifter++;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_RED + "Upgrade attack damage: Lvl " + (skillsconfig.getInt("damage") + 1));
							levelInt = skillsconfig.getInt("damage");
							rowShifter++;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_AQUA + "Upgrade resistance: Lvl " + (skillsconfig.getInt("resistance") + 1));
							levelInt = skillsconfig.getInt("resistance");
							rowShifter++;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.YELLOW + "Upgrade mining speed: Lvl " + (skillsconfig.getInt("mining") + 1));
							levelInt = skillsconfig.getInt("mining");
							rowShifter++;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.RED + "Upgrade regeneration: Lvl " + (skillsconfig.getInt("regen") + 1));
							levelInt = skillsconfig.getInt("regen");
							rowShifter++;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.AQUA + "Upgrade swim speed: Lvl " + (skillsconfig.getInt("swim") + 1));
							levelInt = skillsconfig.getInt("swim");
							rowShifter++;
							if (levelInt == 0) {
								inventory.setItem(46 + rowShifter, currentItem);
							} else if (levelInt > 0 && levelInt < 5) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, currentItem);
							} else if (levelInt > 4 && levelInt < 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, currentItem);
							} else if (levelInt == 9) {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, currentItem);
							} else {
								inventory.setItem(46 + rowShifter, ownedItem);
								inventory.setItem(37 + rowShifter, ownedItem);
								inventory.setItem(28 + rowShifter, ownedItem);
								inventory.setItem(19 + rowShifter, ownedItem);
							}
							
							
							
							event.getWhoClicked().openInventory(inventory);
						}
						
						
					} else if (event.getSlot() == 12 && event.getCurrentItem().equals(depositItem)) {
						int inventorySize = 9;
						String inventoryName = "Dwarven Gold Deposit";
						Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
						
						for (int i = 0; i < inventorySize; i++) {
							inventory.setItem(i, plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " "));
						}
						
						inventory.setItem(3, new ItemStack(Material.AIR));
						
						inventory.setItem(5, plugin.createItem(
								Material.LIME_STAINED_GLASS_PANE, 
								1, 
								ChatColor.GREEN + "CONFIRM", 
								Arrays.asList(ChatColor.LIGHT_PURPLE + "Place gold ingots to the left, ", "and click here to confirm conversion.")));
					
						event.getWhoClicked().openInventory(inventory);
						
					
					}
						event.setCancelled(true);
					
				}
			}
		}
		
	}
}
