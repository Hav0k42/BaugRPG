package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

public class PotentHoneyListener implements Listener {

	private Main plugin;
	public PotentHoneyListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onDrinkPotentHoney(PlayerItemConsumeEvent event) {
		if(!Recipes.POTENT_HONEY.matches(plugin, event.getItem())) return;
		
		Player player = event.getPlayer();
		
		PotionEffectType[] debuffs = {
				PotionEffectType.BAD_OMEN,
				PotionEffectType.BLINDNESS,
				PotionEffectType.CONFUSION,
				PotionEffectType.HUNGER,
				PotionEffectType.LEVITATION,
				PotionEffectType.POISON,
				PotionEffectType.SLOW,
				PotionEffectType.SLOW_DIGGING,
				PotionEffectType.UNLUCK,
				PotionEffectType.WEAKNESS,
				PotionEffectType.WITHER};
		
		for (PotionEffectType debuff : debuffs) {
			player.removePotionEffect(debuff);
		}
		
	}
}
