package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LightShieldListener implements Listener {
	private Main plugin;
	
	public LightShieldListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onProjectileLaunch(PlayerMoveEvent event) {
		if (!event.getPlayer().isBlocking()) return;
		
		Player player = event.getPlayer();
		if (!(Recipes.LIGHT_SHIELD.matches(plugin, player.getInventory().getItemInMainHand()) || (Recipes.LIGHT_SHIELD.matches(plugin, player.getInventory().getItemInOffHand()) && !player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD)))) return;

		
		List<Entity> entities = player.getNearbyEntities(10, 10, 10);
		
		boolean displayEffect = false;
		for (Entity entity : entities) {
			if (entity instanceof Projectile) {
				entity.remove();
				displayEffect = true;
			}
		}
		
		if (displayEffect) {
			showEffect(player.getLocation());
		}
		
	}
	
	private void showEffect(Location center) {
		World world = center.getWorld();
		
		int particleCount = 1000;
		double radius = 12;
		
		
		
		for (int i = 0; i < particleCount; i++) {

			double y = (Math.random() * radius * 2) - radius;
			
			double secondaryRadius = Math.sqrt((radius * radius) - (y * y));
			double angle = Math.random() * 2 * Math.PI;
					
			double x = secondaryRadius * Math.cos(angle);
			
			double z = secondaryRadius * Math.sin(angle);
			
			
			
			Location particleLocation = new Location(world, x + center.getX(), y + center.getY(), z + center.getZ());
			world.spawnParticle(Particle.END_ROD, particleLocation, 1, 0, 0, 0, 0);
		}
		
	}

}
