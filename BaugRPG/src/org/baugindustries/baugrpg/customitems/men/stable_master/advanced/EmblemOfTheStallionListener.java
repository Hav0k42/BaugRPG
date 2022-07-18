package org.baugindustries.baugrpg.customitems.men.stable_master.advanced;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmblemOfTheStallionListener implements Listener {
	private Main plugin;
	
	public EmblemOfTheStallionListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void run(PlayerMoveEvent event) {
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		Player player = event.getPlayer();
		
		if (!Recipes.EMBLEM_OF_THE_STALLION.playerIsCarrying(player, plugin)) return;

		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 2));
	}

}
