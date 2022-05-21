package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VerdantMedallionListener implements Listener {
	private Main plugin;
	
	public VerdantMedallionListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

		Player player = event.getPlayer();
		if (!Recipes.VERDANT_MEDALLION.matches(plugin, player.getInventory().getChestplate())) return;
		player.getNearbyEntities(40, 40, 40).forEach(entity -> {
			if (entity instanceof Animals) {
				Animals animal = (Animals) entity;
				animal.setVelocity(player.getLocation().toVector().subtract(animal.getLocation().toVector()).normalize().multiply(0.6));
			}
		});
	}

}
