package org.baugindustries.baugrpg.customitems.orcs.greedy_scrapper.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SoulOfTheThiefListener implements Listener {
	private Main plugin;
	
	public SoulOfTheThiefListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageEvent event) {

		if (!(event.getEntity() instanceof Player)) return;
		
		if (Math.random() > 0.15) return;
		
		
		Player player = (Player) event.getEntity();
		
		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.GREEDY_SCRAPPER)) return;
		
		if (!Recipes.SOUL_OF_THE_THIEF.playerIsCarrying(player, plugin)) return;
		
		event.setCancelled(true);
	}

}
