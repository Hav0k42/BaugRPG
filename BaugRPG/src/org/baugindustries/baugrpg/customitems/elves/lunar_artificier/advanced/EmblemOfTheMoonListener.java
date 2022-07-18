package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.advanced;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmblemOfTheMoonListener {
	List<Material> treeTypes;
	int nearDist = 25;
	int interval = 100; //5 seconds
	
	public EmblemOfTheMoonListener(Main plugin) {
		treeTypes = Arrays.asList(Material.ACACIA_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.OAK_SAPLING, Material.SPRUCE_SAPLING, Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					if (Recipes.EMBLEM_OF_THE_MOON.playerIsCarrying(player, plugin)) {
						for (Entity entity : player.getNearbyEntities(nearDist, nearDist, nearDist)) {
							if (entity instanceof LivingEntity) {
								LivingEntity lEntity = (LivingEntity) entity;
								lEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, interval + 1, 0));
							}
						}
					}
				}
			}
		}, interval, interval);
	}
	
	
	

}
