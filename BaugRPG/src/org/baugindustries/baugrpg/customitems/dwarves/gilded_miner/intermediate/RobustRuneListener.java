package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class RobustRuneListener implements Listener {
	private Main plugin;
	
	private double damageReduction = 0.1;
	
	public RobustRuneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent event) {
		Player player = event.getPlayer();
		
		String matName = event.getItem().getType().name().toLowerCase();
		
		if (!(matName.contains("flint_and_steel") || matName.contains("shears") || matName.contains("shovel") || matName.contains("axe") || matName.contains("hoe") || matName.contains("rod"))) return;
		
		if (!Recipes.ROBUST_RUNE.playerIsCarrying(player, plugin)) return;
		int damage = event.getDamage();
		
		for (int i = 0; i < event.getDamage(); i++) {
			if (Math.random() < damageReduction) {
				damage--;
			}
		}
		event.setDamage(damage);
		
	}

}
