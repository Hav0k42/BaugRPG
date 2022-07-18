package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public class CompressiveWandListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, List<Location>> queuedBlocks = new HashMap<UUID, List<Location>>();
	final int maxQueues = 48;
	
	int time = 9600; //8 minutes
	int timeMulti = 600;//30 seconds added per extra ore.
	
	public CompressiveWandListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.COMPRESSIVE_WAND.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.COMPRESSIVE_WAND.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (!(event.getClickedBlock().getType().equals(Material.COAL_ORE) || event.getClickedBlock().getType().equals(Material.DEEPSLATE_COAL_ORE))) return;
			
		if (queuedBlocks.containsKey(player.getUniqueId()) && queuedBlocks.get(player.getUniqueId()).size() >= maxQueues) {
			player.sendMessage(ChatColor.RED + "You cannot queue more than " + maxQueues + " coal ore for compression.");
			return;
		}
		
		for (UUID uuid : queuedBlocks.keySet()) {
			if (queuedBlocks.get(uuid).contains(event.getClickedBlock().getLocation())) {
				player.sendMessage(ChatColor.RED + "This block is already queued for compression.");
				return;
			}
		}
		
		List<Location> locations;
		if (queuedBlocks.containsKey(player.getUniqueId())) {
			locations = queuedBlocks.get(player.getUniqueId());
		} else {
			locations = new ArrayList<Location>();
		}
		
		locations.add(event.getClickedBlock().getLocation());
		
		queuedBlocks.put(player.getUniqueId(), locations);
		
		double calcedTime = time + (timeMulti * (queuedBlocks.get(player.getUniqueId()).size() - 1));
		
		long minutes = Math.round(calcedTime / 1200);
		
		player.sendMessage(ChatColor.GREEN + "Compression initializing. Estimated time of completion: " + minutes + " minutes.");
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (event.getClickedBlock().getType().equals(Material.COAL_ORE)) {
					event.getClickedBlock().setType(Material.DIAMOND_ORE);
					player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 4, 0.5, 0.5, 0.5);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.MASTER, 2f, 1f);
				} else if (event.getClickedBlock().getType().equals(Material.DEEPSLATE_COAL_ORE)) {
					event.getClickedBlock().setType(Material.DEEPSLATE_DIAMOND_ORE);
					player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 4, 0.5, 0.5, 0.5);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.MASTER, 2f, 1f);
				}
				
				List<Location> locs;
				if (queuedBlocks.containsKey(player.getUniqueId())) {
					locs = queuedBlocks.get(player.getUniqueId());
				} else {
					locs = new ArrayList<Location>();
				}
				
				locs.remove(event.getClickedBlock().getLocation());
				queuedBlocks.put(player.getUniqueId(), locs);
				
			}
		}, (long) calcedTime);
		
	}

}
