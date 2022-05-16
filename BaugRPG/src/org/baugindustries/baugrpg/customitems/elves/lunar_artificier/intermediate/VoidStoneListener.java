package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.intermediate;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class VoidStoneListener implements Listener {
	private Main plugin;
	
	int radius = 30;

	
	public VoidStoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void BlockBuild(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (event.getTo().getBlock().equals(event.getFrom().getBlock())) return;
		
		if (!Recipes.VOID_STONE.playerIsCarrying(player, plugin)) return;
		
		List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
		
		PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 100, 0);
		
		for (Entity entity : nearbyEntities) {
			if (entity instanceof Player) {
				if (plugin.getRace((Player)entity) != plugin.getRace(player)) {
					((LivingEntity) entity).addPotionEffect(blind);
				}
			}
		}
		
	}
	
	@EventHandler
	public void onUseEgg(PlayerInteractEvent event) {
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.VOID_STONE.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.VOID_STONE.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
	    }

		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		event.setCancelled(true);
	}

}
