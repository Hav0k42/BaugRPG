package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmblemOfThePastureListener {
	
	public EmblemOfThePastureListener(Main plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			  public void run() {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						if (Recipes.EMBLEM_OF_THE_PASTURE.playerIsCarrying(player, plugin)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 101, 1));
						}
					}
			  }
		 }, 100L, 100L);
	}

}
