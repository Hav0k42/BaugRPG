package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.ChatColor;

public class BuffEnderDragonListener implements Listener {
	private Main plugin;
	
	private int respawnTime = 7 * 60 * 20;
	
	public BuffEnderDragonListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof EnderDragon)) return;
		
		event.setDamage(event.getDamage() / 8);
		
	}
	
	@EventHandler
	public void onDestroyCrystal(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof EnderCrystal)) return;
		
		boolean dragonAlive = false;
		for (Entity entity : event.getEntity().getWorld().getLivingEntities()) {
			if (entity instanceof EnderDragon) {
				dragonAlive = true;
				break;
			}
		}
		if (!dragonAlive) return;
		
		Location loc = event.getEntity().getLocation();
		if (!(Math.abs(loc.getX()) > 10 || Math.abs(loc.getZ()) > 10)) return;
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				event.getEntity().getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
			}
		}, respawnTime);
		
	}
	
	@EventHandler
	public void onClickBed(PlayerInteractEvent event) {
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		if (!((event.getClickedBlock().getType().name().toLowerCase().contains("bed") && !event.getClickedBlock().getType().name().toLowerCase().contains("bedrock")) || event.getClickedBlock().getType().name().toLowerCase().contains("respawn"))) return;
		
		for (Entity entity : event.getClickedBlock().getWorld().getNearbyEntities(event.getClickedBlock().getLocation(), 15, 15, 15)) {
			if (entity instanceof EnderDragon) {
				event.getPlayer().sendMessage(ChatColor.RED + "No bed strat >:(");
				event.setCancelled(true);
				return;
			}
		}
	}

}
