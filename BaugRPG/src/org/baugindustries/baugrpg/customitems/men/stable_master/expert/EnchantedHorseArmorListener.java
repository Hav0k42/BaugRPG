package org.baugindustries.baugrpg.customitems.men.stable_master.expert;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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

import org.bukkit.ChatColor;

public class EnchantedHorseArmorListener implements Listener {
	private Main plugin;

	HashMap<UUID, AbstractHorse> lastRiddenEnchantedHorse = new HashMap<UUID, AbstractHorse>();
	public EnchantedHorseArmorListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseWhistle(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.ENCHANTED_WHISTLE.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.ENCHANTED_WHISTLE.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		if (player.getVehicle() != null) {
			if (player.getVehicle() instanceof AbstractHorse) {
				player.sendMessage(ChatColor.RED + "You're already riding a horse.");
				return;
			}
		}
		
		if (!lastRiddenEnchantedHorse.containsKey(player.getUniqueId())) {
			player.sendMessage(ChatColor.RED + "You don't have a horse to call. Ride a horse you've tamed, and give it enchanted horse armor to wear to call it from anywhere.");
			return;
		}
		
		AbstractHorse horse = lastRiddenEnchantedHorse.get(player.getUniqueId());
		

		
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				
				if (!horse.getLocation().getChunk().isEntitiesLoaded()) {
					horse.getLocation().getChunk().load();
					horse.getLocation().getChunk().getEntities();
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
					return;
				} else {
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							
							AbstractHorse newhorse = lastRiddenEnchantedHorse.get(player.getUniqueId());
							
							if (newhorse.isDead()) {
								player.sendMessage(ChatColor.RED + "Your horse has died.");
								lastRiddenEnchantedHorse.remove(player.getUniqueId());
								return;
							}
							
							newhorse.teleport(player.getLocation());
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_FLUTE, SoundCategory.MASTER, 2f, 2f);
							player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, SoundCategory.MASTER, 2f, 2f);

							player.sendMessage(ChatColor.GREEN + "Called");
						}
					}, 1L);
				}
			}
		}, 1L);
		
		
	}
	
	@EventHandler
	public void onExitHorse(VehicleExitEvent event) {
		if (!(event.getVehicle() instanceof AbstractHorse)) return;
		if (!(event.getExited() instanceof Player)) return;
		
		AbstractHorse horse = (AbstractHorse) event.getVehicle();
		if (!horse.isTamed()) return;
		
		Player player = (Player) event.getExited();
		if (!horse.getOwner().equals(player)) return;
		
		if (!Recipes.ENCHANTED_HORSE_ARMOR.matches(plugin, horse.getInventory().getItem(1))) return;
		
		lastRiddenEnchantedHorse.put(((Player)event.getExited()).getUniqueId(), (AbstractHorse)event.getVehicle());
			
	}
	

	@EventHandler
	public void onLoadHorse(EntitiesLoadEvent event) {//This could very possibly become extremely laggy. Recommend semi-frequent server resets to deal with that, OR add a one hour timer to remove players from the horse map.
		for (Entity entity : event.getEntities()) {
			if (entity instanceof AbstractHorse) {
				AbstractHorse horse = (AbstractHorse) entity;
				for (UUID playerUUID : lastRiddenEnchantedHorse.keySet()) {
					if (horse.getUniqueId().equals(lastRiddenEnchantedHorse.get(playerUUID).getUniqueId())) {
						lastRiddenEnchantedHorse.put(playerUUID, horse);
					}
				}
			}
		}
	}

}
