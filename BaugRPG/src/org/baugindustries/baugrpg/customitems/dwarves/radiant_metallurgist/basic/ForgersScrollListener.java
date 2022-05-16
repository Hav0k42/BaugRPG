package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class ForgersScrollListener implements Listener {
	private Main plugin;
	
	public ForgersScrollListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PrepareAnvilEvent event) {
		
		if (event.getResult() == null) return;
		
		Player player = (Player) event.getViewers().get(0);
		
		if (!Recipes.FORGERS_SCROLL.playerIsCarrying(player, plugin)) return;
		
		
		int cost = event.getInventory().getRepairCost() / 2;
		
		plugin.getServer().getScheduler().runTask(plugin, () -> event.getInventory().setRepairCost(cost));
		
		
	}

}
