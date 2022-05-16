package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class ReinforcedRingListener implements Listener {
	private Main plugin;
	
	private double damageReduction = 0.1;
	
	public ReinforcedRingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		Player player = event.getPlayer();
		
		String matName = event.getItem().getType().name().toLowerCase();
		
		if (!(matName.contains("helmet") || matName.contains("chestplate") || matName.contains("leggings") || matName.contains("boots") || matName.contains("elytra"))) return;
		
		if (!Recipes.REINFORCED_RING.playerIsCarrying(player, plugin)) return;
		int damage = event.getDamage();
		
		for (int i = 0; i < event.getDamage(); i++) {
			if (Math.random() < damageReduction) {
				damage--;
			}
		}
		event.setDamage(damage);
		
	}

}
