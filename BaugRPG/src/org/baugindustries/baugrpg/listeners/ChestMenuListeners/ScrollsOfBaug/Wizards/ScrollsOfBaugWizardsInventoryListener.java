package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import java.util.Arrays;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.PlayerSnooping.PlayerSnoopingHubInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ScrollsOfBaugWizardsInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugWizardsInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Scrolls of Baug")) {
					
					
					
					
					
					
					if (event.getSlot() == 11 && event.getCurrentItem().equals(plugin.createItem(
					Material.WRITABLE_BOOK,
					1,
					ChatColor.AQUA + "Feature Management", 
					Arrays.asList(ChatColor.LIGHT_PURPLE + "Turn certain features on and off,", "according to how you wish to run your server.")))) {//Player clicked on the Feature Management Item
						
						
						
						
						
					} else if (event.getSlot() == 12 && event.getCurrentItem().equals(plugin.createItem(
					Material.CHEST,
					1,
					ChatColor.AQUA + "Inventory Snooping", 
					Arrays.asList(ChatColor.LIGHT_PURPLE +  "Access every players' inventories and ender chests")))) {//Player clicked on the Inventory Snooping Item
						
						if (plugin.getServer().getPluginManager().isPluginEnabled("OpenInv")) {
							int inventorySize = 9;
							String inventoryName = "Inventory Snooping Hub";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							
							
							for (int i = 0; i < inventorySize; i++) {
								inventory.setItem(i, new ItemStack(Material.AIR));
							}
	
							
							inventory.setItem(0, plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, "Go Back", null));
							
							inventory.setItem(3, plugin.createItem(
									Material.CHEST, 
									1, 
									"Inventories", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Inventories")));
							
							inventory.setItem(5, plugin.createItem(
									Material.ENDER_CHEST, 
									1, 
									"Ender Chests", 
									Arrays.asList(ChatColor.LIGHT_PURPLE + "Access other players' Ender Chests")));
							
							event.getWhoClicked().openInventory(inventory);
							
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new PlayerSnoopingHubInventoryListener(plugin), plugin);
						} else {
							player.sendMessage(ChatColor.RED + "The supporting plugin for this feature is not installed.\nPlease install the OpenInv plugin.\n" + ChatColor.YELLOW + "https://dev.bukkit.org/projects/openinv");
						}
					}
					
					
					event.setCancelled(true);
				}
			}
		}
		
	}
}
