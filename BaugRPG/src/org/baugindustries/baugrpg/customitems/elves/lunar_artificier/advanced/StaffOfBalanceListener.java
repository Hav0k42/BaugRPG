package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.advanced;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StaffOfBalanceListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 4000;//four seconds
	double endIncrementAmount = 0.001;
	double incrementDist = 0.05;
	double timingIncrement = 0.02;
	double nearbyValue = 0.5;
	
	public StaffOfBalanceListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseGaiasWrath(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.STAFF_OF_BALANCE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.STAFF_OF_BALANCE.matches(plugin, player.getInventory().getItemInOffHand())) return;
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
		
		
		activateGaiasWrath(player, player.getEyeLocation(), 0, 1);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, SoundCategory.MASTER, 2f, 1f);
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
	}
	
	private void activateGaiasWrath(Player player, Location loc, double end, double time) {
		
		if (end > 1) {
			return;
		}
		Particle particle = Particle.REDSTONE;
		int grayScale = (int) ((127.5 * Math.sin(time)) + 127.5);
		Particle.DustOptions options = new Particle.DustOptions(Color.fromBGR(grayScale, grayScale, grayScale), 1);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
					loc.getWorld().spawnParticle(particle, loc, 2, 0, 0, 0, 0, options);
					for (Entity entity : loc.getWorld().getNearbyEntities(loc, nearbyValue, nearbyValue, nearbyValue)) {
						if (entity instanceof Monster) {
							Monster monster = (Monster) entity;
							monster.damage(plugin.damageArmorCalculation(monster, 5));
						} else if (entity instanceof Player) {
							Player otherPlayer = (Player) entity;
							if (plugin.getRace(player) != plugin.getRace(otherPlayer)) {
								if (otherPlayer.getStatistic(Statistic.DEATHS) > otherPlayer.getStatistic(Statistic.PLAYER_KILLS)) {
									otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 3, 50));
								} else {
									otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 4));
								}
							}
						}
					}
			  }
		 }, (int)time);
		
			end += endIncrementAmount;
			Location newLoc = incrementForward(loc, incrementDist);
			
			activateGaiasWrath(player, newLoc, end, time + timingIncrement);
	}
	
	private Location incrementForward(Location loc, double dist) {
		
		
		double x = loc.getX() + (loc.getDirection().getX() * dist);
		double y = loc.getY() + (loc.getDirection().getY() * dist);
		double z = loc.getZ() + (loc.getDirection().getZ() * dist);
		
		
		
		Location newLoc = new Location(loc.getWorld(), x, y, z, loc.getYaw(), loc.getPitch());

		return newLoc;
	}

}
