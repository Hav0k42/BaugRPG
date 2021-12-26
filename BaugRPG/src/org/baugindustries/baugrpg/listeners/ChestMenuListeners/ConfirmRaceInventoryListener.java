package org.baugindustries.baugrpg.listeners.ChestMenuListeners;


import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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
				if (event.getView().getTitle().equals("Confirm Selection")) {
					
						if (event.getSlot() == 15 && event.getCurrentItem().equals(plugin.itemManager.getNoItem())) {//No

							int inventorySize = 54;
							String inventoryName = "Choose Your Race";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, plugin.itemManager.getBlankItem());
							}
							
							
							
							inventory.setItem(13, plugin.itemManager.getRaceSelectionItem());
							
							inventory.setItem(28, plugin.itemManager.getSelectManItem());
								
							inventory.setItem(30, plugin.itemManager.getSelectElfItem());
							
							inventory.setItem(32, plugin.itemManager.getSelectDwarfItem());
							
							inventory.setItem(34, plugin.itemManager.getSelectOrcItem());
							
							
							
							event.getWhoClicked().openInventory(inventory);
							
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
							
							
							int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
							switch (race) {
								case 1://Men
									plugin.board.getTeam("Men").addPlayer(player);
									break;
								case 2://Elves
									plugin.board.getTeam("Elves").addPlayer(player);
									break;
								case 3://Dwarves
									plugin.board.getTeam("Dwarves").addPlayer(player);
									break;
								case 4://Orcs
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

