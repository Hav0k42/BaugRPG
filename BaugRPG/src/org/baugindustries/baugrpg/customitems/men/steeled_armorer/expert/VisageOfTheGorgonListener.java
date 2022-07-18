package org.baugindustries.baugrpg.customitems.men.steeled_armorer.expert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.RayTrace;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class VisageOfTheGorgonListener implements Listener {
	private Main plugin;
	List<UUID> gorgonWearers = new ArrayList<UUID>();
	List<UUID> frozenPlayers = new ArrayList<UUID>();
	List<UUID> frozenMonsters = new ArrayList<UUID>();
	
	int range = 50;
	
	public VisageOfTheGorgonListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (frozenPlayers.contains(player.getUniqueId())) {
			event.setCancelled(true);
			return;
		}
		
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) {
			if (gorgonWearers.contains(player.getUniqueId())) {
				if (!Recipes.VISAGE_OF_THE_GORGON.matches(plugin, player.getInventory().getHelmet())) {
					gorgonWearers.remove(player.getUniqueId());
				}
			} else if (Recipes.VISAGE_OF_THE_GORGON.matches(plugin, player.getInventory().getHelmet())) {
				gorgonWearers.add(player.getUniqueId());
			}
		}
		
		if (gorgonWearers.size() == 0) {
			return;
		}
		
		boolean proxCheck = false;
		
		for (UUID uuid : gorgonWearers) {
			if (!player.getUniqueId().equals(uuid)) {
				if (player.getLocation().distance(Bukkit.getPlayer(uuid).getLocation()) < range) {
					proxCheck = true;
					break;
				}
			}
		}
		
		if (!proxCheck) return;
		
		
		RayTrace raytrace = new RayTrace(player.getLocation().toVector(), player.getLocation().getDirection());
		Vector[] positions = new Vector[50];
		positions = raytrace.traverse(50, 1).toArray(positions);
		
		for (Vector pos : positions) {
			for (Entity entity : player.getWorld().getNearbyEntities(pos.toLocation(player.getWorld()), 1, 1, 1)) {
				if (entity instanceof Player) {
					Player otherPlayer = (Player) entity;
					if (gorgonWearers.contains(otherPlayer.getUniqueId())) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								frozenPlayers.remove(player.getUniqueId());
							}
						}, 200L);
						return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(EntityTargetEvent event) {
		if (!(event.getEntity() instanceof Monster)) return;
		Monster mob = (Monster) event.getEntity();
		if (frozenMonsters.contains(mob.getUniqueId())) {
			event.setCancelled(true);
			return;
		}
		
		if (!(event.getTarget() instanceof Player)) return;
		Player player = (Player) event.getTarget();
		if (!Recipes.VISAGE_OF_THE_GORGON.matches(plugin, player.getInventory().getHelmet())) return;
		
		mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 99));
		frozenMonsters.add(mob.getUniqueId());
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				frozenMonsters.remove(mob.getUniqueId());
			}
		}, 200L);
		event.setCancelled(true);
		
	}
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		
		if (frozenPlayers.size() != 0) {
			if (event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				if (!frozenPlayers.contains(player.getUniqueId())) return;
				event.setCancelled(true);
			} else if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				if (!frozenPlayers.contains(player.getUniqueId())) return;
				event.setCancelled(true);
			}
		}
		
		if (frozenMonsters.size() != 0) {
			if (event.getDamager() instanceof Monster) {
				Monster mob = (Monster) event.getDamager();
				if (!frozenMonsters.contains(mob.getUniqueId())) return;
				event.setCancelled(true);
			} else if (event.getEntity() instanceof Monster) {
				Monster mob = (Monster) event.getEntity();
				if (!frozenMonsters.contains(mob.getUniqueId())) return;
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(PlayerJoinEvent event) {
		if (Recipes.VISAGE_OF_THE_GORGON.matches(plugin, event.getPlayer().getInventory().getHelmet())) {
			gorgonWearers.add(event.getPlayer().getUniqueId());
		}
	}
	
	@EventHandler
	public void onInventoryClick(PlayerQuitEvent event) {
		if (gorgonWearers.contains(event.getPlayer().getUniqueId())) {
			gorgonWearers.remove(event.getPlayer().getUniqueId());
		}
	}
	
}
