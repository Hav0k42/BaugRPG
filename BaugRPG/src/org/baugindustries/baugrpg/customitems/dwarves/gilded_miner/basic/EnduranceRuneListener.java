package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EnduranceRuneListener implements Listener {
	private Main plugin;
	
	private double hungerReduction = 0.4;
	
	public EnduranceRuneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onLoseHunger(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();

		if (event.getItem() != null) return;
		
		if (!Recipes.ENDURANCE_RUNE.playerIsCarrying(player, plugin)) return;
		
		if (!player.isSprinting()) return;
		
		if (Math.random() > hungerReduction) return;
		
		
		event.setCancelled(true);
		
	}

}
