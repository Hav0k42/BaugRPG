package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PhoenixAshesListener implements Listener {
	private Main plugin;
	
	List<UUID> totemedPlayers = new ArrayList<UUID>();
	HashMap<UUID, Location> safeLocs = new HashMap<UUID, Location>();
	HashMap<UUID, Long> debounce = new HashMap<UUID, Long>();
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 180000;//three minutes
	
	final int maxTime = 25000;//less time than mole because it isnt restricted to ground.
	
	Particle particle = Particle.FLAME;
	
	public PhoenixAshesListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.PHOENIX_ASHES.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.PHOENIX_ASHES.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (debounce.containsKey(player.getUniqueId()) && debounce.get(player.getUniqueId()) + 500 > System.currentTimeMillis()) return;
		
		debounce.put(player.getUniqueId(), System.currentTimeMillis());
		
		
		if (totemedPlayers.contains(player.getUniqueId())) {
			player.setGameMode(GameMode.SURVIVAL);
			totemedPlayers.remove(player.getUniqueId());
			safeLocs.remove(player.getUniqueId());
			

			player.getWorld().spawnParticle(particle, player.getEyeLocation(), 1000, 5, 5, 5, 0.2);
			
			for (Entity entity : player.getNearbyEntities(10,  10, 10)) {
				if (entity instanceof Player) {
					Player otherPlayer = (Player) entity;
					if (plugin.getRace(player) != plugin.getRace(otherPlayer)) {
						plugin.damageArmorCalculation(otherPlayer, 8);
						otherPlayer.setFireTicks(200);
					}
				}
				if (entity instanceof Monster) {
					Monster monster = (Monster) entity;
					plugin.damageArmorCalculation(monster, 8);
					monster.setFireTicks(200);
				}
			}
			cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
			return;
		}
		
		if (totemedPlayers.contains(player.getUniqueId())) return;
		
		
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
		
		
		player.setGameMode(GameMode.SPECTATOR);
		
		totemedPlayers.add(player.getUniqueId());
		safeLocs.put(player.getUniqueId(), player.getLocation());
		
		long time = System.currentTimeMillis();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (System.currentTimeMillis() < time + maxTime && totemedPlayers.contains(player.getUniqueId())) {
					
					Location nextLoc = player.getLocation().add(player.getLocation().getDirection());
					
					if (!nextLoc.getBlock().getType().isSolid()) {
						safeLocs.put(player.getUniqueId(), player.getLocation());
						player.setVelocity(nextLoc.subtract(player.getLocation()).toVector().normalize());
					} else {
						player.teleport(safeLocs.get(player.getUniqueId()));
					}
					
					player.getWorld().spawnParticle(particle, player.getEyeLocation(), 10, 0.3, 0.3, 0.3, 0.2);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1, 1);
					
					
					if (player.getSpectatorTarget() != null) player.setSpectatorTarget(null);
					
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
				} else {
					player.setGameMode(GameMode.SURVIVAL);
					totemedPlayers.remove(player.getUniqueId());
					safeLocs.remove(player.getUniqueId());
					

					player.getWorld().spawnParticle(particle, player.getEyeLocation(), 1000, 5, 5, 5, 0.2);
					
					for (Entity entity : player.getNearbyEntities(10,  10, 10)) {
						if (entity instanceof Player) {
							Player otherPlayer = (Player) entity;
							if (plugin.getRace(player) != plugin.getRace(otherPlayer)) {
								plugin.damageArmorCalculation(otherPlayer, 8);
								otherPlayer.setFireTicks(200);
							}
						}
						if (entity instanceof Monster) {
							Monster monster = (Monster) entity;
							plugin.damageArmorCalculation(monster, 8);
							monster.setFireTicks(200);
						}
					}
					cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
				}
			}
		}, 1L);
		
		
		
		
	}
	

	@EventHandler
	public void onInventoryClick(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (!totemedPlayers.contains(player.getUniqueId())) return;
		
		player.setGameMode(GameMode.SURVIVAL);
		totemedPlayers.remove(player.getUniqueId());
		safeLocs.remove(player.getUniqueId());
		
		
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
	}

}
