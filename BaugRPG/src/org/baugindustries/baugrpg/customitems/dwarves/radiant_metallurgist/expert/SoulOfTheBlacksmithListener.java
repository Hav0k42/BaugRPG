package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulOfTheBlacksmithListener implements Listener {
	
	public SoulOfTheBlacksmithListener(Main plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.RADIANT_METALLURGIST)) continue;
					
					if (!Recipes.SOUL_OF_THE_BLACKSMITH.playerIsCarrying(player, plugin)) return;
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 201, 0));
				}
			}
		}, 200L, 200L);
	}
	
	
	

}
