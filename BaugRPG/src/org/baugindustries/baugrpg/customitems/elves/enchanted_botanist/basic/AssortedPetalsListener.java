package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import org.bukkit.ChatColor;

public class AssortedPetalsListener implements Listener {
	private Main plugin;
	
	public AssortedPetalsListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.ASSORTED_PETALS.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be placed");
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Bee)) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.ASSORTED_PETALS.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.ASSORTED_PETALS.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		
	}

}
