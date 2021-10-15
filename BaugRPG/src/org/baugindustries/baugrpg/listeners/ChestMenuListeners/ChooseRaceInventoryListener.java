package org.baugindustries.baugrpg.listeners.ChestMenuListeners;


import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

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
				if (event.getView().getTitle().equals("Choose Your Race")) {
					
						
						String chosenRace = "";
						if (event.getSlot() == 28 && event.getCurrentItem().equals(plugin.createItem(Material.NETHERITE_SWORD, 1, ChatColor.DARK_AQUA + "Men", Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the sword and horse combat,", "Men prefer to live in flat open areas", "with lots of room to roam about.")))) {//Men
							chosenRace = "Men";
						} else if (event.getSlot() == 30 && event.getCurrentItem().equals(plugin.createItem(Material.BOW, 1, ChatColor.DARK_GREEN + "Elves", Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the bow and ranged combat", "Elves reside in wooded areas", "due to their love of nature.")))) {//Elves
							chosenRace = "Elves";
						} else if (event.getSlot() == 32 && event.getCurrentItem().equals(plugin.createItem(Material.NETHERITE_AXE, 1, ChatColor.DARK_PURPLE + "Dwarves", Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the riches beneath the surface.")))) {//Dwarves
							chosenRace = "Dwarves";
						} else if (event.getSlot() == 34 && event.getCurrentItem().equals(plugin.createItem(Material.NETHERITE_HELMET, 1, ChatColor.DARK_RED + "Orcs", Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of iron forged weaponry and brutal combat", "Orcs smoulder in the fires of hell", "for they cannot go to the surface.")))) {//Orcs
							chosenRace = "Orcs";
						}
						
						if (!chosenRace.equals("")) {
							int inventorySize = 27;
							String inventoryName = "Confirm Selection";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " ", null));
							}
							
							
							inventory.setItem(4, plugin.createItem(
									Material.NETHER_STAR, 
									1, 
									ChatColor.YELLOW + "Confirm Selection", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want to choose " + chosenRace + "?")));
							
							inventory.setItem(11, plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes", null));
							
							inventory.setItem(15, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No", null));
							
							
							player.openInventory(inventory);
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new ConfirmRaceInventoryListener(plugin), plugin);
						}
						
						event.setCancelled(true);
					}
				}
				
			}
		}
	}

