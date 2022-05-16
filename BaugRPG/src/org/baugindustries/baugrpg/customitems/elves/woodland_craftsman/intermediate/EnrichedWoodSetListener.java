package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class EnrichedWoodSetListener implements Listener {
	private Main plugin;
	
	public EnrichedWoodSetListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onGainExp(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		
		if (event.getAmount() < 0) return;
		
		if (!Recipes.ENRICHED_WOOD_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.ENRICHED_WOOD_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.ENRICHED_WOOD_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.ENRICHED_WOOD_GREAVES.matches(plugin, player.getInventory().getBoots())) return;
		
		event.setAmount(event.getAmount() * 2);
	}

}
