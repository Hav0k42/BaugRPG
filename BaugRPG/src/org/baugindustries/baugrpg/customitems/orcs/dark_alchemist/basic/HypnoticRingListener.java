package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.PiglinAbstract;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class HypnoticRingListener implements Listener {
	private Main plugin;
	
	public HypnoticRingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPiglinTarget(EntityTargetEvent event) {
		
		if (!(event.getEntity() instanceof PiglinAbstract || event.getEntity() instanceof PigZombie)) return;
		
		if (!(event.getTarget() instanceof Player)) return;
		Player player = (Player) event.getTarget();

		
		
		if (event.getEntity().getLastDamageCause() != null && event.getEntity().getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK) && ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager().equals(player)) return;
		
		if (!Recipes.HYPNOTIC_RING.playerIsCarrying(player, plugin)) return;
		
		
		
		if (event.getReason().equals(TargetReason.TARGET_ATTACKED_ENTITY)) return;
		
		
		
		event.setCancelled(true);
		
	}

}
