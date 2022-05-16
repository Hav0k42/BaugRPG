package org.baugindustries.baugrpg.customitems.men.stable_master.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class HorseshoeListener implements Listener {
	private Main plugin;
	
	final private double damageMultiplier = 0.5;
	
	public HorseshoeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void horseFall(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof AbstractHorse)) return;
		AbstractHorse horse = (AbstractHorse) event.getEntity();
		
		if (!event.getCause().equals(DamageCause.FALL)) return;
		
		if (!Recipes.HORSESHOE.matches(plugin, horse.getInventory().getItem(1))) return;
		
		event.setDamage(event.getDamage() * damageMultiplier);
	}
	
}
