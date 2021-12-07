package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

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
					
						if (event.getSlot() == 15 && event.getCurrentItem().equals(plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No", null))) {//No

							int inventorySize = 54;
							String inventoryName = "Choose Your Race";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " ", null));
							}
							
							
							
							inventory.setItem(13, plugin.createItem(
									Material.NETHER_STAR, 
									1, 
									ChatColor.YELLOW + "Choose Your Race", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "This race will determine the way you build", "the way you play, and the way you", "interact with players on this server.", "", "It cannot be changed later.")));
							
							inventory.setItem(28, plugin.createItem(
									Material.NETHERITE_SWORD, 
									1, 
									ChatColor.DARK_AQUA + "Men", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the sword and horse combat,", "Men prefer to live in flat open areas", "with lots of room to roam about.")));
								
							inventory.setItem(30, plugin.createItem(
									Material.BOW, 
									1, 
									ChatColor.DARK_GREEN + "Elves", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the bow and ranged combat", "Elves reside in wooded areas", "due to their love of nature.")));
							
							inventory.setItem(32, plugin.createItem(
									Material.NETHERITE_AXE, 
									1, 
									ChatColor.DARK_PURPLE + "Dwarves", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the riches beneath the surface.")));
							
							inventory.setItem(34, plugin.createItem(
									Material.NETHERITE_HELMET, 
									1, 
									ChatColor.DARK_RED + "Orcs", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of iron forged weaponry and brutal combat", "Orcs smoulder in the fires of hell", "for they cannot go to the surface.")));

							
							
							
							event.getWhoClicked().openInventory(inventory);
							
						} else if (event.getSlot() == 11 && event.getCurrentItem().equals(plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes", null))) {//Yes
							
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

