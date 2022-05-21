package org.baugindustries.baugrpg.listeners.Crafting;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ComposterListener implements Listener {
	private Main plugin;
	
	public ComposterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onUseComposter(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		if (!event.getClickedBlock().getType().equals(Material.COMPOSTER)) return;
		
		ItemStack item = null;
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			item = event.getPlayer().getInventory().getItemInMainHand();
		} else {
			item = event.getPlayer().getInventory().getItemInOffHand();
		}
		
		for (Recipes recipe : Recipes.values()) {
			if (recipe.matches(plugin, item)) {
				event.setCancelled(true);
				return;
			}
		}
	}

}
