package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EmblemOfTheRavenListener implements Listener {
	private Main plugin;
	
	public EmblemOfTheRavenListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
		
		Player player = event.getPlayer();
		if (!Recipes.EMBLEM_OF_THE_RAVEN.playerIsCarrying(player, plugin)) return;
		
		for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
			if (entity instanceof Item) {
				entity.setVelocity(player.getLocation().toVector().subtract(entity.getLocation().toVector()).normalize().multiply(0.3));
			}
		}
	}

}
