package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.advanced;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

public class GrapplingHookListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	HashMap<UUID, Integer> count = new HashMap<UUID, Integer>();
	HashMap<UUID, Location> hookedLocation = new HashMap<UUID, Location>();
	HashMap<UUID, Double> hookLength = new HashMap<UUID, Double>();
	int cooldownTime = 60000;//One minute
	int timeoutTicks = 400;
	
	int range = 30;
	double stepAmount = 1;
	double reelStep = 2;
	double minLength = 1;
	
	double magnitude = 0.3;
	
	public GrapplingHookListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseHook(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.GRAPPLING_HOOK.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.GRAPPLING_HOOK.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		event.setCancelled(true);
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				if (count.get(player.getUniqueId()) > 3) {
				
					int secondsRemaining = (int) ((cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000) + 1;
					String timeString = " seconds remaining.";
					if (secondsRemaining == 1) {
						timeString = " second remaining.";
					}
					
					player.sendMessage(ChatColor.RED + "You can't use that yet. " + secondsRemaining + timeString);
					return;
				}
			} else {
				count.remove(player.getUniqueId());
			}
		}
		
		if (hookedLocation.containsKey(player.getUniqueId())) {
			hookedLocation.remove(player.getUniqueId());
		}
		
		findAnchorBlock(player.getEyeLocation().subtract(0, 0.3, 0), player, 0);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.MASTER, 2f, 1f);
		
		
	}
	
	@EventHandler
	public void onReelHook(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.GRAPPLING_HOOK.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.GRAPPLING_HOOK.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		event.setCancelled(true);
		
		if (!hookedLocation.containsKey(player.getUniqueId())) return;
		
		double newLength = hookLength.get(player.getUniqueId()) - reelStep;
		if (newLength < minLength) {
			newLength = minLength;
		}
		hookLength.put(player.getUniqueId(), newLength);
		
	}
	
	private void findAnchorBlock(Location initLoc, Player player, int step) {
		Vector direction = initLoc.getDirection();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				Location currentLoc = new Location(initLoc.getWorld(), initLoc.getX() + (direction.getX() * stepAmount * step), initLoc.getY() + (direction.getY() * stepAmount * step), initLoc.getZ() + (direction.getZ() * stepAmount * step));
				
				double particleAmount = 80;
				for (double i = 0; i < particleAmount; i++) {
					double x = currentLoc.getX() - ((i / particleAmount) * (currentLoc.getX() - player.getLocation().getX()));
					double y = currentLoc.getY() - ((i / particleAmount) * (currentLoc.getY() - player.getLocation().getY()));
					double z = currentLoc.getZ() - ((i / particleAmount) * (currentLoc.getZ() - player.getLocation().getZ()));
					Location particleloc = new Location(player.getWorld(), x, y, z);
					player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleloc, 1, 0, 0, 0, 0);
				}
				
				if (currentLoc.distance(initLoc) > range) {
					returnHook(player);
				} else {
					if (currentLoc.getBlock().getType().isSolid()) {
						cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
						if (count.containsKey(player.getUniqueId())) {
							count.put(player.getUniqueId(), 1 + count.get(player.getUniqueId()));
						} else {
							count.put(player.getUniqueId(), 1);
						}
						hookedLocation.put(player.getUniqueId(), currentLoc);
						hookLength.put(player.getUniqueId(), currentLoc.distance(initLoc));
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								if (hookedLocation.containsKey(player.getUniqueId())) {
									Location hookedLoc = hookedLocation.get(player.getUniqueId());
									
									double particleAmount = 80;
									for (double i = 0; i < particleAmount; i++) {
										double x = hookedLoc.getX() - ((i / particleAmount) * (hookedLoc.getX() - player.getLocation().getX()));
										double y = hookedLoc.getY() - ((i / particleAmount) * (hookedLoc.getY() - player.getLocation().getY()));
										double z = hookedLoc.getZ() - ((i / particleAmount) * (hookedLoc.getZ() - player.getLocation().getZ()));
										Location particleloc = new Location(player.getWorld(), x, y, z);
										player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleloc, 1, 0, 0, 0, 0);
									}
									
									if (hookedLoc.distance(player.getLocation()) > hookLength.get(player.getUniqueId())) {
										double offAmount = hookedLoc.distance(player.getLocation()) - hookLength.get(player.getUniqueId());
										Vector hookDirection = hookedLoc.toVector().subtract(player.getLocation().toVector()).normalize().multiply(magnitude * offAmount);
										
										
										Vector velocity = player.getVelocity().add(hookDirection);
										player.setVelocity(velocity);
									}
									
									plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
								}
							}
						}, 1);
					} else {
						findAnchorBlock(initLoc, player, step + 1);
					}
				}
			}
		 }, 1);
	}
	
	private void returnHook(Player player) {
		
	}
	
	@EventHandler
	public void onUseGaiasWrath(PlayerToggleSneakEvent event) {
		if (!event.isSneaking()) return;
		Player player = event.getPlayer();
		if (hookedLocation.containsKey(player.getUniqueId())) {
			hookedLocation.remove(player.getUniqueId());
		}
	}
	
}
