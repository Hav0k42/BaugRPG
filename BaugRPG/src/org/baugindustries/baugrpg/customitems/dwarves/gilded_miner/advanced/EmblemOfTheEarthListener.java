package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.potion.PotionEffectType;

public class EmblemOfTheEarthListener implements Listener {

	private Main plugin;
	
	
	public EmblemOfTheEarthListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onGetBlindnessEffect(EntityPotionEffectEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (!(event.getAction().equals(Action.ADDED) || event.getAction().equals(Action.CHANGED))) return;
		
		if (!event.getNewEffect().getType().equals(PotionEffectType.SLOW_DIGGING)) return;
		
		if (!Recipes.EMBLEM_OF_THE_EARTH.playerIsCarrying(player, plugin)) return;
		
		event.setCancelled(true);
		
	}
}
