package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DemetersBenevolenceListener implements Listener {
	private Main plugin;
	
	Material[] tulips = {Material.ORANGE_TULIP, Material.PINK_TULIP, Material.RED_TULIP, Material.WHITE_TULIP};
	
	Material[] smallBushes = {Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.LILY_OF_THE_VALLEY};
	
	Material[] stemmedFlowers = {Material.CORNFLOWER, Material.ALLIUM, Material.OXEYE_DAISY, Material.POPPY, Material.DANDELION};
	
	Material[] tallBushes = {Material.PEONY, Material.ROSE_BUSH, Material.LILAC, Material.SUNFLOWER};
	
	Material[][] flowers = {tulips, smallBushes, stemmedFlowers, tallBushes};
	
	public DemetersBenevolenceListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerMoveEvent event) {
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		
		Player player = event.getPlayer();
		
		if (!Recipes.DEMETERS_BENEVOLENCE.matches(plugin, player.getInventory().getBoots())) return;
		
		if (!player.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.GRASS_BLOCK)) return;
		
		regrow(player.getLocation().getBlock().getRelative(0, -1, 0), 2);
	}
	
	private void regrow(Block center, int radius) {
		for (int x = -radius; x < radius + 1; x++) {
			for (int z = -radius; z < radius + 1; z++) {
				if (plugin.getTopBlock(center.getRelative(x, 0, z)).getType().equals(Material.GRASS_BLOCK)) {
					Block topGrass = plugin.getTopBlock(center.getRelative(x, 0, z));
					if (Math.random() < 0.04 && topGrass.getRelative(0, 1, 0).getType().equals(Material.AIR)) {
						if (Math.random() > 0.5) {//flower
							Material[] flowerPool = flowers[(int) (Math.random() * flowers.length)];
							if (flowerPool.equals(tallBushes)) {//tall flower
								Material type = flowerPool[(int) (Math.random() * flowerPool.length)];
								topGrass.getRelative(0, 1, 0).setType(type, false);

								topGrass.getRelative(0, 2, 0).setType(type, false);
								Bisected topData = (Bisected) topGrass.getRelative(0, 2, 0).getBlockData();
								topData.setHalf(Half.TOP);
								topGrass.getRelative(0, 2, 0).setBlockData(topData);
							} else {//short flower
								topGrass.getRelative(0, 1, 0).setType(flowerPool[(int) (Math.random() * flowerPool.length)]);
							}
						} else {//grass
							if (Math.random() > 0.5) {//short grass
								topGrass.getRelative(0, 1, 0).setType(Material.GRASS);
							} else {//tall grass
								topGrass.getRelative(0, 1, 0).setType(Material.TALL_GRASS, false);

								topGrass.getRelative(0, 2, 0).setType(Material.TALL_GRASS, false);
								Bisected topData = (Bisected) topGrass.getRelative(0, 2, 0).getBlockData();
								topData.setHalf(Half.TOP);
								topGrass.getRelative(0, 2, 0).setBlockData(topData);
							}
						}
					}
				}
			}
		}
    }

}
