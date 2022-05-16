package org.baugindustries.baugrpg.customitems.men.steeled_armorer.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FeatheredShoesListener implements Listener {
	private Main plugin;
	
	final private double damageMultiplier = 0.3;
	
	public FeatheredShoesListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void horseFall(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (!event.getCause().equals(DamageCause.FALL)) return;
		
		if (!Recipes.FEATHERED_SHOES.matches(plugin, player.getInventory().getBoots())) return;
		
		event.setDamage(event.getDamage() * damageMultiplier);
	}
	
}
