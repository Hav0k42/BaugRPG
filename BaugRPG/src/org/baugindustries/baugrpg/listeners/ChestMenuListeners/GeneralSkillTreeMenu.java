package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

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
				if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
				if (event.getView().getTitle().equals("General Skills")) {
					event.setCancelled(true);
					File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
				 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);

					Boolean reloadBool = false;
					
					ItemStack infoItem = plugin.itemManager.getGeneralSkillTreeInfoItem();
					
					if (event.getClickedInventory().getItem(0) == null) return;
					
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
										player.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
										player.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
									} else {
										player.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 10);
										player.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 80);
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
					
					
					
					
					
					ItemStack backItem = plugin.itemManager.getBackItem();
					
					if (event.getSlot() == 45 && event.getCurrentItem().equals(backItem)) {//back to skills hub
						
						
						if (skillsconfig.getInt("totalSkillPoints") > 19) {
							player.openInventory(plugin.inventoryManager.getSkillTreeMenuInventory(player));
							return;
						} else {
							player.performCommand("baugscroll");
							return;
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
					
					
					float maxSpeed = 0.35f;
					
					String[] skillNames = {"speed", "jump", "damage", "resistance", "mining", "regen", "swim"};
					for (int i = 0; i < currentItem.length; i++) {
						if (event.getCurrentItem() != null && event.getCurrentItem().equals(currentItem[i])) {
							if (skillsconfig.getInt("skillPoints") > 0) {
								skillsconfig.set("skillPoints", skillsconfig.getInt("skillPoints") - 1);
								skillsconfig.set(skillNames[i], skillsconfig.getInt(skillNames[i]) + 1);
								if (skillsconfig.getBoolean("speedOn")) {
									player.setWalkSpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed"))+ 0.2f);
									player.setFlySpeed((((maxSpeed - 0.2f) / 10f) * skillsconfig.getInt("speed")) + 0.2f);
								}
								if (skillsconfig.getBoolean("regenOn")) {
									player.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -5f) / 9f) + (95f/9f)));
									player.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * (int)(((skillsconfig.getInt("regen") * -40f) / 9f) + (760f/9f)));
								} else {
									player.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 10);
									player.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 80);
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
					}
					
					
					if (reloadBool) {
						player.openInventory(plugin.inventoryManager.getGeneralSkillTreeMenuInventory(player));
					}
					
				}
			}
		}
	}
}
