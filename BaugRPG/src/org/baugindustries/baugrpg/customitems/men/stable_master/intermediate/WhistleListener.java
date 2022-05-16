package org.baugindustries.baugrpg.customitems.men.stable_master.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.ChatColor;

public class WhistleListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, AbstractHorse> lastRiddenHorse = new HashMap<UUID, AbstractHorse>();
	
	public WhistleListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseWhistle(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.WHISTLE.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.WHISTLE.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		if (player.getVehicle() != null) {
			if (player.getVehicle() instanceof AbstractHorse) {
				player.sendMessage(ChatColor.RED + "You're already riding your horse.");
				return;
			}
		}
		
		if (!lastRiddenHorse.containsKey(player.getUniqueId())) {
			player.sendMessage(ChatColor.RED + "You don't have a horse to call. Ride a horse you've tamed to be able to call it with a whistle.");
			return;
		}
		
		AbstractHorse horse = lastRiddenHorse.get(player.getUniqueId());
		
		if (!horse.isValid()) {
			player.sendMessage(ChatColor.RED + "Your horse died or isn't currently loaded.");
			return;
		}
		
		if (player.getLocation().distance(horse.getLocation()) > 100) {
			player.sendMessage(ChatColor.RED + "Your horse is too far away to hear you.");
			return;
		}
		
		horse.teleport(player.getLocation());

		player.sendMessage(ChatColor.GREEN + "called");
		
	}
	

	@EventHandler
	public void onExitHorse(VehicleExitEvent event) {
		if (!(event.getVehicle() instanceof AbstractHorse)) return;
		if (!(event.getExited() instanceof Player)) return;
		
		AbstractHorse horse = (AbstractHorse) event.getVehicle();
		if (!horse.isTamed()) return;
		
		Player player = (Player) event.getExited();
		if (!horse.getOwner().equals(player)) return;
		
		lastRiddenHorse.put(((Player)event.getExited()).getUniqueId(), (AbstractHorse)event.getVehicle());
			
	}
	

	@EventHandler
	public void onLoadHorse(EntitiesLoadEvent event) {//This could very possibly become extremely laggy. Recommend semi-frequent server resets to deal with that, OR add a one hour timer to remove players from the horse map.
		for (Entity entity : event.getEntities()) {
			if (entity instanceof AbstractHorse) {
				AbstractHorse horse = (AbstractHorse) entity;
				for (UUID playerUUID : lastRiddenHorse.keySet()) {
					if (horse.getUniqueId().equals(lastRiddenHorse.get(playerUUID).getUniqueId())) {
						lastRiddenHorse.put(playerUUID, horse);
					}
				}
			}
		}
	}

}
