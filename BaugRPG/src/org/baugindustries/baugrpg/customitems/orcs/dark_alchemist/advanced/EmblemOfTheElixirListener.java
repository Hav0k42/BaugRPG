package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.advanced;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmblemOfTheElixirListener {
	List<PotionEffectType> buffs;
	int nearDist = 25;
	int interval = 1600; //160 seconds
	
	public EmblemOfTheElixirListener(Main plugin) {
		buffs = Arrays.asList(PotionEffectType.ABSORPTION, PotionEffectType.CONDUIT_POWER, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.DOLPHINS_GRACE, PotionEffectType.FAST_DIGGING, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.HEALTH_BOOST, PotionEffectType.HERO_OF_THE_VILLAGE, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.JUMP, PotionEffectType.LUCK, PotionEffectType.NIGHT_VISION, PotionEffectType.REGENERATION, PotionEffectType.SATURATION, PotionEffectType.SPEED, PotionEffectType.WATER_BREATHING);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						if (Recipes.EMBLEM_OF_THE_ELIXIR.playerIsCarrying(player, plugin)) {
							player.addPotionEffect(new PotionEffect(buffs.get((int) (Math.random() * buffs.size())), 100, 0));
						}
					}
			}
					
		}, 1200L, 1200L);
	}
	
	
	

}
