package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class FerrousHarvesterListener implements Listener {
	private Main plugin;
	
	public FerrousHarvesterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onMonsterDeath(EntityDeathEvent event) {
		Entity mob = event.getEntity();
		if (!(mob instanceof Monster)) return;
		Player player = ((Monster)mob).getKiller();
		if (player == null) return;
		
		if (!Recipes.FERROUS_HARVESTER.matches(plugin, player.getInventory().getItemInMainHand())) return;
		
		event.getDrops().forEach(drop -> {
			drop.setType(Material.IRON_NUGGET);
		});
		
	}

}
