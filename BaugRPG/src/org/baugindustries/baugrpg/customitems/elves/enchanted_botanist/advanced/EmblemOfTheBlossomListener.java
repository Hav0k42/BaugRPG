package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

public class EmblemOfTheBlossomListener implements Listener {
	private Main plugin;
	List<Material> cropTypes;
	int nearDist = 25;
	
	public EmblemOfTheBlossomListener(Main plugin) {
		this.plugin = plugin;
		cropTypes = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS, Material.MELON_STEM, Material.PUMPKIN_STEM, Material.NETHER_WART);
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockGrowEvent event) {
		if (!cropTypes.contains(event.getBlock().getType())) return;
		
		boolean match = false;
		for (Entity entity : event.getBlock().getWorld().getNearbyEntities(event.getBlock().getLocation(), nearDist, nearDist, nearDist)) {
			if (entity instanceof Player) {
				if (Recipes.EMBLEM_OF_THE_BLOSSOM.playerIsCarrying((Player) entity, plugin)) {
					match = true;
					break;
				}
			}
		}

		if (!match) return;
		
		
		Block block = event.getBlock();
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {Ageable meta = (Ageable) event.getBlock().getBlockData();
					int age = meta.getAge();
					if (age < meta.getMaximumAge()) {
						age++;
					}
					meta.setAge(age);
					block.setBlockData(meta);
			  }
		 }, 1L);
		
	}

}
