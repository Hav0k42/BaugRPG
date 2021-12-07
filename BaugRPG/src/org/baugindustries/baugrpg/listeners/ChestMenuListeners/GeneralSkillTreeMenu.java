package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class GeneralSkillTreeMenu implements Listener {
	private Main plugin;
	public GeneralSkillTreeMenu(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("General Skills")) {
					event.setCancelled(true);
					File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
				 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);

					Boolean reloadBool = false;
					
					ItemStack infoItem = plugin.createItem(
							Material.NETHER_STAR,
							1,
							ChatColor.GOLD + "General Skill Tree", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade different passive skills."));
					
					if (event.getClickedInventory().getItem(0).equals(infoItem)) {
						if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
							int clickedSlot = event.getSlot();
							switch (clickedSlot) {
								case 1:
									skillsconfig.set("speedOn", !skillsconfig.getBoolean("speedOn"));
									reloadBool = true;
									float maxSpeed = 0.35f;
									
									if (skillsconfig.getBoolean("speedOn")) {
										player.setWalkSpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed"))+ 0.2f);
										player.setFlySpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
									} else {
										player.setWalkSpeed(0.2f);
										player.setFlySpeed(0.2f);
									}
									break;
								case 2:
									skillsconfig.set("jumpOn", !skillsconfig.getBoolean("jumpOn"));
									reloadBool = true;
									break;
								case 3:
									skillsconfig.set("damageOn", !skillsconfig.getBoolean("damageOn"));
									reloadBool = true;
									break;
								case 4:
									skillsconfig.set("resistanceOn", !skillsconfig.getBoolean("resistanceOn"));
									reloadBool = true;
									break;
								case 5:
									skillsconfig.set("miningOn", !skillsconfig.getBoolean("miningOn"));
									reloadBool = true;
									break;
								case 6:
									skillsconfig.set("regenOn", !skillsconfig.getBoolean("regenOn"));
									reloadBool = true;
									if (skillsconfig.getBoolean("regenOn")) {
										player.setSaturatedRegenRate((int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
										player.setUnsaturatedRegenRate((int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
									} else {
										player.setSaturatedRegenRate(10);
										player.setUnsaturatedRegenRate(80);
									}
									break;
								case 7:
									skillsconfig.set("swimOn", !skillsconfig.getBoolean("swimOn"));
									reloadBool = true;
									break;
								
							}
							try {
								skillsconfig.save(skillsfile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					
					
					
					
					ItemStack backItem = plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "Back");
					
					if (event.getSlot() == 45 && event.getCurrentItem().equals(backItem)) {//back to skills hub
						
						
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
							
							inventory.setItem(5, plugin.createItem(
									Material.NETHERITE_AXE,
									1, 
									ChatColor.GOLD + "Race Skills", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Race specific skills that are more speciallized.")));
							
							event.getWhoClicked().openInventory(inventory);
						} else {
							player.performCommand("baugscroll");
						}
					}
					
					
					
					ItemStack speedItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.WHITE + "Upgrade speed: Lvl " + (skillsconfig.getInt("speed") + 1));
					ItemStack jumpItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Upgrade jump: Lvl " + (skillsconfig.getInt("jump") + 1));
					ItemStack damageItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_RED + "Upgrade attack damage: Lvl " + (skillsconfig.getInt("damage") + 1));
					ItemStack resistanceItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.DARK_AQUA + "Upgrade resistance: Lvl " + (skillsconfig.getInt("resistance") + 1));
					ItemStack miningItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.YELLOW + "Upgrade mining speed: Lvl " + (skillsconfig.getInt("mining") + 1));
					ItemStack regenItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.RED + "Upgrade regeneration: Lvl " + (skillsconfig.getInt("regen") + 1));
					ItemStack swimItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.AQUA + "Upgrade swim speed: Lvl " + (skillsconfig.getInt("swim") + 1));
					
					
					float maxSpeed = 0.35f;
					
					ItemStack currentItem = speedItem;
					String skill = "speed";
					
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							if (skillsconfig.getBoolean("speedOn")) {
								player.setWalkSpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed"))+ 0.2f);
								player.setFlySpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
							}
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					currentItem = jumpItem;
					skill = "jump";
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					currentItem = damageItem;
					skill = "damage";
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					currentItem = resistanceItem;
					skill = "resistance";
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					currentItem = miningItem;
					skill = "mining";
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					currentItem = regenItem;
					skill = "regen";
					
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							if (skillsconfig.getBoolean("regenOn")) {
								player.setSaturatedRegenRate((int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
								player.setUnsaturatedRegenRate((int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
							}
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					currentItem = swimItem;
					skill = "swim";
					if (event.getCurrentItem().equals(currentItem)) {
						if (skillsconfig.getInt("skillPoints") > 0) {
							skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
							skillsconfig.set(skill, skillsconfig.getInt(skill) + 1);
							reloadBool = true;
						}
						try {
							skillsconfig.save(skillsfile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (reloadBool) {
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
						
						currentItem = plugin.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.WHITE + "Upgrade speed: Lvl " + (skillsconfig.getInt("speed") + 1));
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
					
				}
			}
		}
	}
}
