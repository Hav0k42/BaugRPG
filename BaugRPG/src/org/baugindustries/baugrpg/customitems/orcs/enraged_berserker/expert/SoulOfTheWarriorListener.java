package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulOfTheWarriorListener implements Listener {
	
	public SoulOfTheWarriorListener(Main plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.ENRAGED_BERSERKER)) continue;
					
					if (!Recipes.SOUL_OF_THE_WARRIOR.playerIsCarrying(player, plugin)) return;
					
					double health = player.getHealth();
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 201, 4));
					player.setHealth(health);
				}
			}
		}, 200L, 200L);
	}
	
	
	

}
