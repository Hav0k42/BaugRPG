package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced;

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

public class EssenceOfFaunaListener implements Listener {
	private Main plugin;
	
	public EssenceOfFaunaListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Sheep)) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.ESSENCE_OF_FAUNA.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.ESSENCE_OF_FAUNA.matches(plugin, player.getInventory().getItemInOffHand())) return;
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
			if (!Recipes.ESSENCE_OF_FAUNA.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.ESSENCE_OF_FAUNA.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		
	}

}
