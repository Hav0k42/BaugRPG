package org.baugindustries.baugrpg.customitems.men.steeled_armorer.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KnockbackShieldListener implements Listener {
	private Main plugin;
	
	public KnockbackShieldListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void hitWithHammer(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (!player.isBlocking()) return;
		
		if (event.getFinalDamage() != 0) return;
		
		if (!Recipes.KNOCKBACK_SHIELD.matches(plugin, player.getItemInUse())) return;
		
		Location knockbackLocation = player.getLocation();
		knockbackLocation.setY(event.getDamager().getLocation().getY() - 0.6);
		
		event.getDamager().setVelocity(event.getDamager().getLocation().toVector().subtract(knockbackLocation.toVector()).normalize().multiply(1.7));
			
	}

}
