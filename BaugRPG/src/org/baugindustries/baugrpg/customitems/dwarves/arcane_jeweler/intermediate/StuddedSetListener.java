package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class StuddedSetListener implements Listener {
	private Main plugin;
	
	public StuddedSetListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onMonsterTarget(EntityTargetEvent event) {
		
		if (!(event.getTarget() instanceof Player)) return;
		if (!(event.getEntity() instanceof Monster)) return;
		Player player = (Player) event.getTarget();
		
		if (event.getEntity().getLastDamageCause() != null && event.getEntity().getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK) && ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager().equals(player)) return;
		
		
		if (!Recipes.STUDDED_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.STUDDED_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.STUDDED_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.STUDDED_GREAVES.matches(plugin, player.getInventory().getBoots())) return;
		
		event.setCancelled(true);
		
		if (!(((LivingEntity)event.getEntity()).getCategory().equals(EntityCategory.UNDEAD))) return;
		
		if (Math.random() < 0.1) return;
		
		event.getEntity().setFireTicks(40);
	}

}
