package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.advanced;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;

public class EmblemOfTheForestListener {
	List<Material> treeTypes;
	int nearDist = 25;
	int interval = 1600; //160 seconds
	
	public EmblemOfTheForestListener(Main plugin) {
		treeTypes = Arrays.asList(Material.ACACIA_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.OAK_SAPLING, Material.SPRUCE_SAPLING, Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {//run the extremely calculation heavy task on another thread.
					public void run() {
						for (Player player : plugin.getServer().getOnlinePlayers()) {
							Block block = player.getLocation().getBlock();
							if (Recipes.EMBLEM_OF_THE_FOREST.playerIsCarrying(player, plugin)) {
								for (int x = -nearDist; x <= nearDist; x++) {// i really dont like that im doing this. seems like it will lag a lot, but its genuinely the most efficient way i can think to do this. Edit: Just timed it. Wouldn't lag at all even if I was running it on the main thread. Modern technology so fancy :sparkles:
									for (int y = -nearDist; y <= nearDist; y++) {
										for (int z = -nearDist; z <= nearDist; z++) {
											if (treeTypes.contains(block.getRelative(x, y, z).getType())) {
												Block sapling = block.getRelative(x, y, z);
												Sapling data = (Sapling) sapling.getBlockData();
												plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {//hook back into the main thread.
													public void run() {
														data.setStage(data.getMaximumStage());
														sapling.setBlockData(data);
													}
												});
												
											}
										}
									}
								}
							}
						}
					}
				});
				
				
			}
		}, interval, interval);
	}
	
	
	

}
