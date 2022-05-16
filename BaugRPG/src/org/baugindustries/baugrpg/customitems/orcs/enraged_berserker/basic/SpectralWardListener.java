package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class SpectralWardListener implements Listener {
	private Main plugin;
	
	public SpectralWardListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onGhastTarget(EntityTargetEvent event) {
		if (!(event.getEntity() instanceof Ghast)) return;
		
		if (!(event.getTarget() instanceof Player)) return;
		Player player = (Player) event.getTarget();

		
		
		if (event.getEntity().getLastDamageCause() != null && event.getEntity().getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK) && ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager().equals(player)) return;
		
		if (!Recipes.SPECTRAL_WARD.playerIsCarrying(player, plugin)) return;
		
		
		
		if (event.getReason().equals(TargetReason.TARGET_ATTACKED_ENTITY)) return;
		
		Ghast ghast = (Ghast) event.getEntity();
		double dist = player.getLocation().distance(ghast.getLocation());
		if (dist > 10 && dist < 120) {
			ghast.setVelocity(player.getLocation().toVector().subtract(ghast.getLocation().toVector()).normalize());
		}
		
		event.setCancelled(true);
	}

}
