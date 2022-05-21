package org.baugindustries.baugrpg.customitems.men.steeled_armorer.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class EmblemOfTheShieldListener implements Listener {
	private Main plugin;
	
	public EmblemOfTheShieldListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		
		Player player = (Player) event.getEntity();
		
		if (!Recipes.EMBLEM_OF_THE_SHIELD.playerIsCarrying(player, plugin)) return;
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
					player.setVelocity(new Vector(0, 0, 0));
			  }
		 }, 1L);
	}

}
