package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CactusGreavesListener implements Listener {
	
	Main plugin;
	
	public CactusGreavesListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void runOnSand(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!(Recipes.CACTUS_GREAVES.matches(plugin, player.getInventory().getBoots()))) return;


		if (!(player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.SAND) || player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.RED_SAND))) return;
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 1));
	}

}
