package org.baugindustries.baugrpg.listeners.ChestMenuListeners;


import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class ConfirmRaceInventoryListener implements Listener{

	
	private Main plugin;
	public ConfirmRaceInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Confirm Selection")) {
					
						if (event.getSlot() == 15 && event.getCurrentItem().equals(plugin.itemManager.getNoItem())) {//No
							player.openInventory(plugin.inventoryManager.getSetRaceMenuInventory());
						} else if (event.getSlot() == 11 && event.getCurrentItem().equals(plugin.itemManager.getYesItem())) {//Yes
							
							PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
							ItemMeta raceInfoMeta = event.getInventory().getItem(4).getItemMeta();
							String selectedRace = raceInfoMeta.getLore().get(0);
							int selectedRaceInt = 0;
							if (selectedRace.contains("Men")) {
								selectedRaceInt = 1;
							} else if (selectedRace.contains("Elves")) {
								selectedRaceInt = 2;
							} else if (selectedRace.contains("Dwarves")) {
								selectedRaceInt = 3;
							} else if (selectedRace.contains("Orcs")) {
								selectedRaceInt = 4;
							}
							data.set(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER, selectedRaceInt);//Men: 1, Elves: 2, Dwarves: 3, Orcs: 4
							
							player.closeInventory();
							
							File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
							FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
							
							int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
							switch (race) {
								case 1://Men
									player.teleport(claimsConfig.getLocation("menSpawn"));
									plugin.board.getTeam("Men").addPlayer(player);
									break;
								case 2://Elves
									player.teleport(claimsConfig.getLocation("elfSpawn"));
									plugin.board.getTeam("Elves").addPlayer(player);
									break;
								case 3://Dwarves
									player.teleport(claimsConfig.getLocation("dwarfSpawn"));
									plugin.board.getTeam("Dwarves").addPlayer(player);
									break;
								case 4://Orcs
									player.teleport(claimsConfig.getLocation("orcSpawn"));
									plugin.board.getTeam("Orcs").addPlayer(player);
									break;
								case 5://Wizards
									plugin.board.getTeam("Wizards").addPlayer(player);
									break;
							}
							
							
						}
						event.setCancelled(true);
					}
				}
			}
		}
		
	}

