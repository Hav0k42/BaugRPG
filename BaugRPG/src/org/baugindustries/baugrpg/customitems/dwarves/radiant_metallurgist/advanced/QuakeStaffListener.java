package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.advanced;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import org.bukkit.ChatColor;

public class QuakeStaffListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 15000;//15 seconds
	double splitChance = 0.035;
	double endIncrementAmount = 0.001;
	double incrementDist = 0.2;
	double randOffsetBound = 0.15;
	double timingIncrement = 0.02;
	double nearbyValue = 5;
	
	public QuakeStaffListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseFlamelash(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.QUAKE_STAFF.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.QUAKE_STAFF.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				int secondsRemaining = (int) ((cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000) + 1;
				String timeString = " seconds remaining.";
				if (secondsRemaining == 1) {
					timeString = " second remaining.";
				}
				player.sendMessage(ChatColor.RED + "You can't use that yet. " + secondsRemaining + timeString);
				return;
			}
		}
		
		activateFlamelash(player, player.getLocation(), 0, 1);
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
	}
	
	private void activateFlamelash(Player player, Location loc, double end, double time) {
		
		if (end > 1) {
			return;
		}
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
					loc.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 1, 0, 0, 0, 0, Material.DIRT.createBlockData());
			  }
		 }, (int)time);
		
		
		if (Math.random() < splitChance) {
			player.getWorld().playSound(loc, Sound.BLOCK_STONE_BREAK, SoundCategory.MASTER, 2f, 1f);
			Location newLoc1 = incrementForward(loc, incrementDist, randOffsetBound);
			Location newLoc2 = incrementForward(loc, incrementDist, randOffsetBound);
			
			float newYaw = (float) (Math.random() * 80);
			newLoc1.setYaw(loc.getYaw() + newYaw);
			newLoc2.setYaw(loc.getYaw() - newYaw);
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				  public void run() {
						for (Entity entity : loc.getWorld().getNearbyEntities(loc, nearbyValue, nearbyValue, nearbyValue)) {
							if (entity instanceof Monster) {
								entity.setVelocity(new Vector(0, 1, 0));
							}
							if (entity instanceof Player) {
								Player otherPlayer = (Player) entity;
								if (plugin.getRace(otherPlayer) != plugin.getRace(player)) {
									entity.setVelocity(new Vector(0, 1, 0));
								}
							}
						}
				  }
			 }, (int)time);
			
			
			activateFlamelash(player, newLoc1, end * 2, time + timingIncrement);
			activateFlamelash(player, newLoc2, end * 2, time + timingIncrement);
		} else {
			end += endIncrementAmount;
			Location newLoc = incrementForward(loc, incrementDist, randOffsetBound);
			newLoc.setY(plugin.getTopBlock(newLoc.getBlock()).getY() + 1);
			
			activateFlamelash(player, newLoc, end, time + timingIncrement);
		}
	}
	
	private Location incrementForward(Location loc, double dist, double offset) {
		
		double angle = Math.toRadians(loc.getYaw()) + (Math.PI / 2);
		
		double x = loc.getX() + (dist * Math.cos(angle)) + ((Math.random() * offset) - (offset / 2));
		double y = loc.getY();
		double z = loc.getZ() + (dist * Math.sin(angle)) + ((Math.random() * offset) - (offset / 2));
		
		
		
		Location newLoc = new Location(loc.getWorld(), x, y, z, loc.getYaw(), loc.getPitch());

		return newLoc;
	}

}
