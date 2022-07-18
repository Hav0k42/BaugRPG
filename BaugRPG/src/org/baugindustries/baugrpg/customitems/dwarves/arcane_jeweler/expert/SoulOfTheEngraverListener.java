package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class SoulOfTheEngraverListener implements Listener {
	private Main plugin;
	
	public SoulOfTheEngraverListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageEvent event) {

		if (!(event.getEntity() instanceof Player)) return;
		
		if (!event.getCause().equals(DamageCause.FALL)) return;
		
		
		Player player = (Player) event.getEntity();
		
		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.ARCANE_JEWELER)) return;
		
		if (!Recipes.SOUL_OF_THE_ENGRAVER.playerIsCarrying(player, plugin)) return;
		
		event.setCancelled(true);
	}

}
