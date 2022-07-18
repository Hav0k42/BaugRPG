package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.expert;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulOfTheWoodsmanListener implements Listener {
	
	Main plugin;
	
	public SoulOfTheWoodsmanListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void runOnSand(PlayerMoveEvent event) {
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		Player player = event.getPlayer();
		if (!(Recipes.SOUL_OF_THE_WOODSMAN.playerIsCarrying(player, plugin))) return;


		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.WOODLAND_CRAFTSMAN)) return;
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
	}

}
