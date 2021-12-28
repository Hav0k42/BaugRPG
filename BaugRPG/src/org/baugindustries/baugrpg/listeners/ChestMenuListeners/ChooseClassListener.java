package org.baugindustries.baugrpg.listeners.ChestMenuListeners;


import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
 		player.openInventory(plugin.inventoryManager.getConfirmClassMenuInventory(pickedClass));
 		
		event.setCancelled(true);
				
			
		}
		
	}

