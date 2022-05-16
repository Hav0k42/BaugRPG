package org.baugindustries.baugrpg.customitems.men.steeled_armorer.intermediate;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IronPlateSetListener implements Listener {
	private Main plugin;
	
	int radius = 30;

	
	public IronPlateSetListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void BlockBuild(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		
		if (!Recipes.IRON_PLATE_HELMET.matches(plugin, player.getInventory().getHelmet())) return;
		if (!Recipes.IRON_PLATE_CHESTPIECE.matches(plugin, player.getInventory().getChestplate())) return;
		if (!Recipes.IRON_PLATE_LEGGINGS.matches(plugin, player.getInventory().getLeggings())) return;
		if (!Recipes.IRON_PLATE_GREAVES.matches(plugin, player.getInventory().getBoots())) return;
		
		List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
		
		PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 80, 3);
		
		for (Entity entity : nearbyEntities) {
			if (entity instanceof Monster) {
				((LivingEntity) entity).addPotionEffect(slow);
			}
			if (entity instanceof Player) {
				if (plugin.getRace((Player)entity) != plugin.getRace(player)) {
					((LivingEntity) entity).addPotionEffect(slow);
				}
			}
		}
		
	}

}
