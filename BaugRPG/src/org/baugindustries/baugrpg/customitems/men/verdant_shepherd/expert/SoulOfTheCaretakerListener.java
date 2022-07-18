package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SoulOfTheCaretakerListener implements Listener {
	private Main plugin;
	
	public SoulOfTheCaretakerListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(FoodLevelChangeEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.VERDANT_SHEPHERD)) return;
		
		if (!Recipes.SOUL_OF_THE_CARETAKER.playerIsCarrying(player, plugin)) return;
		
		event.setCancelled(true);
		player.setSaturation(20);
	}

}
