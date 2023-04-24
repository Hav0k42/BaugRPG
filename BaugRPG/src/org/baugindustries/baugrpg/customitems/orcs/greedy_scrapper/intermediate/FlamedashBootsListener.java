package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlamedashBootsListener implements Listener {
	private Main plugin;
	
	public FlamedashBootsListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
		Player player = event.getPlayer();
		if (!player.isSprinting()) return;
		if (!Recipes.FLAMEDASH_BOOTS.matches(plugin, player.getInventory().getBoots())) return;
		if (!event.getFrom().getBlock().getType().equals(Material.AIR)) return;
		event.getFrom().getBlock().setType(Material.FIRE);
	}

}
