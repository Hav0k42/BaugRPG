package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class PortableAccelerantListener implements Listener {
	private Main plugin;
	
	
	public PortableAccelerantListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.PORTABLE_ACCELERANT.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.PORTABLE_ACCELERANT.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		
		ItemStack newItem = null;
	    if (event.getHand().equals(EquipmentSlot.HAND)) {
	    	newItem = player.getInventory().getItemInMainHand();
	    	if (newItem.getAmount() == 1) {
	    		player.getInventory().setItemInMainHand(null);
	    	} else {
		    	newItem.setAmount(newItem.getAmount() - 1);
		    	player.getInventory().setItemInMainHand(newItem);
	    	}
	    } else {
	    	newItem = player.getInventory().getItemInOffHand();
	    	if (newItem.getAmount() == 1) {
	    		player.getInventory().setItemInOffHand(null);
	    	} else {
		    	newItem.setAmount(newItem.getAmount() - 1);
		    	player.getInventory().setItemInOffHand(newItem);
	    	}
	    }
		
	    Location loc = player.getLocation();
	    loc.add(0, 1.2, 0);
	    explodeAccelerant(0, player, loc);
	}
	
	private void explodeAccelerant(int tick, Player player, Location loc) {

		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				Location startLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1.2, loc.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
				player.teleport(startLoc);
				player.getWorld().spawnParticle(Particle.PORTAL, loc, tick * 10, 0, 0, 0, 1);
				if (tick > 50) {
					player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, loc, tick, 0, 0, 0, 1);
					if (tick % 5 == 0) {
						player.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BIT, 2, 2);
					}
					if (tick > 100) {
						player.getWorld().spawnParticle(Particle.FLASH, loc, 1, 0, 0, 0, 1);
						if (tick % 2 == 0) {
							player.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BIT, 2, 2);
						}
					}
				} else if (tick % 10 == 0) {
					player.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BIT, 2, 2);
				}
				
				if (tick < 160) {
					explodeAccelerant(tick + 1, player, loc);
				} else {
					player.setHealth(0);
					player.getWorld().spawnParticle(Particle.REVERSE_PORTAL, loc, 600, 0, 0, 0, 1);
					player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1, 0, 0, 0, 1);
					player.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 25, 1);
					for (Entity entity : loc.getWorld().getNearbyEntities(loc, 32, 32, 32)) {
						if (entity instanceof Monster || entity instanceof Player) {
							LivingEntity lEntity = (LivingEntity) entity;
							lEntity.damage(50 - loc.distance(entity.getLocation()));

							Vector launchVector = entity.getLocation().toVector().subtract(loc.toVector().setY(loc.getY() - 2));
							launchVector.normalize();
							launchVector.multiply(4 - (4 / loc.distance(entity.getLocation())));
							entity.setVelocity(launchVector);
						}
					}
				}
			}
		}, 1L);
	}

}
