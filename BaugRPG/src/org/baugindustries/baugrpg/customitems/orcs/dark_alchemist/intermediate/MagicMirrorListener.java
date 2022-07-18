package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import org.bukkit.ChatColor;

public class MagicMirrorListener implements Listener {
	private Main plugin;

	public MagicMirrorListener(Main plugin) {
		this.plugin = plugin;
		
	}
	
	
	@EventHandler
	public void onClickCopper(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.MAGIC_MIRROR.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.MAGIC_MIRROR.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) event.setCancelled(true);
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) event.setCancelled(true);
		
		Location spawnLoc = player.getBedSpawnLocation();
		if (spawnLoc == null) {
			File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
			FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
			
			if (claimsConfig.contains(plugin.getRaceString(plugin.getRace(player)) + "Spawn")) {
				spawnLoc = claimsConfig.getLocation(plugin.getRaceString(plugin.getRace(player)) + "Spawn");
			} else {
				spawnLoc = player.getWorld().getSpawnLocation();
			}
		}
		
		player.sendMessage(ChatColor.YELLOW + "Teleporting. Please hold still for 5 seconds.");
		final Location coords = player.getLocation();
		final Location finalSpawnLoc = spawnLoc;
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
				  if (player.getLocation().getX() == coords.getX() && player.getLocation().getY() == coords.getY() && player.getLocation().getZ() == coords.getZ()) {
					  player.teleport(finalSpawnLoc);
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 2f, 1f);
						finalSpawnLoc.getWorld().playSound(finalSpawnLoc, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 2f, 1f);
				  } else {
					  player.sendMessage(ChatColor.RED + "You cretin, I said don't move.");
				  }
			  }
		 }, 100L);
		
	}

}
