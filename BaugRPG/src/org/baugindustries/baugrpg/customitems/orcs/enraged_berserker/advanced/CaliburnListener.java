package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CaliburnListener implements Listener {
	private Main plugin;
	
	public CaliburnListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		
		if (!Recipes.CALIBURN.matches(plugin, player.getInventory().getItemInMainHand())) return;
		
		if (event.getEntity() instanceof Player) {
			Player otherPlayer = (Player) event.getEntity();
			otherPlayer.sendMessage(ChatColor.GOLD + "You've been ignited with greek fire which can only be put out in water.");
			event.getEntity().setFireTicks(10000);
		} else if (event.getEntity() instanceof Monster) {
			event.getEntity().setFireTicks(10000);
		}
	}

}
