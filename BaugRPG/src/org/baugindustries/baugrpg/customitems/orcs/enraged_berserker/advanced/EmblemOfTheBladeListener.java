package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EmblemOfTheBladeListener implements Listener {
	private Main plugin;
	
	public EmblemOfTheBladeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		
		if (!Recipes.EMBLEM_OF_THE_BLADE.playerIsCarrying(player, plugin)) return;
		
		if (player.getHealth() + (event.getDamage() * 0.1) > player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
			player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		} else {
			player.setHealth(player.getHealth() + (event.getDamage() * 0.1));
		}
	}

}
