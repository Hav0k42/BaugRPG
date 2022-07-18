package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulOfTheScientistListener implements Listener {
	
	public SoulOfTheScientistListener(Main plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.DARK_ALCHEMIST)) continue;
					
					if (!Recipes.SOUL_OF_THE_SCIENTIST.playerIsCarrying(player, plugin)) return;
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 201, 4));
				}
			}
		}, 200L, 200L);
	}
	
	
	

}
