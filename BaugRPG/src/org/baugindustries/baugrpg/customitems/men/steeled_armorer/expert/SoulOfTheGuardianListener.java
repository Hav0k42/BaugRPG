package org.baugindustries.baugrpg.customitems.men.steeled_armorer.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SoulOfTheGuardianListener implements Listener {
	private Main plugin;
	
	public SoulOfTheGuardianListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.STEELED_ARMORER)) return;
		
		if (!Recipes.SOUL_OF_THE_GUARDIAN.playerIsCarrying(player, plugin)) return;
		
		event.setDamage(event.getDamage() / 2);
	}

}
