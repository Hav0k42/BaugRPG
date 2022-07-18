package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;


public class FleshCandleListener implements Listener {
	private Main plugin;
	
	
	public FleshCandleListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(EntitySpawnEvent event) {
		if (!(event.getEntity() instanceof Monster)) return;

		for (Entity entity : event.getLocation().getWorld().getNearbyEntities(event.getLocation(), 0, 0, 0)) {
			if (entity instanceof Player) {
				if (Recipes.FLESH_CANDLE.playerIsCarrying((Player) entity, plugin)) {
					event.getLocation().getWorld().spawnEntity(event.getLocation(), event.getEntityType());
					event.getLocation().getWorld().spawnEntity(event.getLocation(), event.getEntityType());
					
					return;
				}
			}
		}
		
		
	}
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.FLESH_CANDLE.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
	}

}
