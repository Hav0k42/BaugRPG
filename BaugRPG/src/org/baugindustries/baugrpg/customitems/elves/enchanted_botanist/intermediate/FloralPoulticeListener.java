package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import org.bukkit.ChatColor;

public class FloralPoulticeListener implements Listener {
	private Main plugin;
	
	public FloralPoulticeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Sheep)) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.FLORAL_POULTICE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.FLORAL_POULTICE.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!event.getClickedBlock().getType().name().toLowerCase().contains("sign")) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.FLORAL_POULTICE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.FLORAL_POULTICE.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		
	}

}
