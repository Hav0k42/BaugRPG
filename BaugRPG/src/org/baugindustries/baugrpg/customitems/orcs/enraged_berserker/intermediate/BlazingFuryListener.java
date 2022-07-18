package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class BlazingFuryListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 30000;//30 seconds
	
	
	
	public BlazingFuryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void hitWithAxe(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		if (player.getInventory().getItemInMainHand() == null) return;
		if (!(Recipes.BLAZING_FURY.matches(plugin, player.getInventory().getItemInMainHand()))) return;
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) return;
		}
		
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 400);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, SoundCategory.MASTER, 2f, 1f);
		player.getNearbyEntities(20, 20, 20).forEach(entity -> {
			if (entity instanceof Monster) {
				entity.setFireTicks(60);
			} else if (entity instanceof Player) {
				Player otherPlayer = (Player) entity;
				if (plugin.getRace(player) != plugin.getRace(otherPlayer)) {
					otherPlayer.setFireTicks(60);
				}
			}
		});
	}

}
