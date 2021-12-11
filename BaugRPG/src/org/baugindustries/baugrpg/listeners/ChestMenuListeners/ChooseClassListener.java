package org.baugindustries.baugrpg.listeners.ChestMenuListeners;


import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;

public class ChooseClassListener implements Listener{
	
	
	private Main plugin;
	public ChooseClassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() == null) return;
		if (!event.getView().getTitle().equals("Choose your Class")) return;
		if (event.getClickedInventory().getLocation() != null) return;
		if (event.getCurrentItem() == null) return;
		if (!event.getView().getTopInventory().equals(event.getClickedInventory())) return;
		
		Player player = (Player)event.getWhoClicked();
 		
 		
 		String pickedClass = event.getCurrentItem().getItemMeta().getDisplayName();
 		
 		int inventorySize = 27;
		String inventoryName = "Confirm Class Selection";
		Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
		for (int i = 0; i < inventorySize; i++) {
			inventory.setItem(i, plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " ", null));
		}
		
		
		inventory.setItem(4, plugin.createItem(
				Material.NETHER_STAR, 
				1, 
				ChatColor.YELLOW + "Confirm Selection", 
				Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want to choose " + pickedClass + "?")));
		
		inventory.setItem(11, plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes", null));
		
		inventory.setItem(15, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No", null));
		
		
		player.openInventory(inventory);
		event.setCancelled(true);
				
			
		}
		
	}

