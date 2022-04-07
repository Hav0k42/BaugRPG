package org.baugindustries.baugrpg.listeners.ChestMenuListeners;


import org.baugindustries.baugrpg.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChooseRaceInventoryListener implements Listener{
	
	
	private Main plugin;
	public ChooseRaceInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
				if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Choose Your Race")) {
					
						
						String chosenRace = "";
						if (event.getSlot() == 28 && event.getCurrentItem().equals(plugin.itemManager.getSelectManItem())) {//Men
							chosenRace = "Men";
						} else if (event.getSlot() == 30 && event.getCurrentItem().equals(plugin.itemManager.getSelectElfItem())) {//Elves
							chosenRace = "Elves";
						} else if (event.getSlot() == 32 && event.getCurrentItem().equals(plugin.itemManager.getSelectDwarfItem())) {//Dwarves
							chosenRace = "Dwarves";
						} else if (event.getSlot() == 34 && event.getCurrentItem().equals(plugin.itemManager.getSelectOrcItem())) {//Orcs
							chosenRace = "Orcs";
						}
						
						if (!chosenRace.equals("")) {
							player.openInventory(plugin.inventoryManager.getConfirmRaceMenuInventory(chosenRace));
						}
						
						event.setCancelled(true);
					}
				}
				
			}
		}
	}

