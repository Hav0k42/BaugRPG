package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class GoldenFlowerListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	HashMap<UUID, Integer> livingBees = new HashMap<UUID, Integer>();
	int cooldownTime = 120000;//two minutes
	int beeCount = 3;
	int maxBees = 6;
	
	public GoldenFlowerListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseGaiasWrath(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.GOLDEN_FLOWER.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.GOLDEN_FLOWER.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				int secondsRemaining = (int) ((cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000) + 1;
				String timeString = " seconds remaining.";
				if (secondsRemaining == 1) {
					timeString = " second remaining.";
				}
				player.sendMessage(ChatColor.RED + "You can't use that yet. " + secondsRemaining + timeString);
				return;
			}
		}
		
		if (livingBees.containsKey(player.getUniqueId()) && livingBees.get(player.getUniqueId()) == maxBees) {
			player.sendMessage(ChatColor.RED + "You cannot summon any more wasps");
			return;
		}
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
		for (int i = 0; i < beeCount; i++) {
			if (!livingBees.containsKey(player.getUniqueId()) || livingBees.get(player.getUniqueId()) < maxBees) {
				Bee bee = (Bee) player.getWorld().spawnEntity(player.getLocation(), EntityType.BEE);
				bee.setCustomName("Wasp");
				bee.setCustomNameVisible(false);
				bee.getPersistentDataContainer().set(new NamespacedKey(plugin, "wasp"), PersistentDataType.INTEGER, 1);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						if (bee.getAnger() == 0) {//follow mode.
							if (bee.getLocation().distance(player.getLocation()) > 25) {
								bee.teleport(player);
							} else if (bee.getLocation().distance(player.getLocation()) > 6.5) {
								bee.setVelocity(player.getLocation().toVector().subtract(bee.getLocation().toVector()).normalize().multiply(0.6));
							}
							for (Entity entity : bee.getNearbyEntities(25, 25, 25)) {
								if (bee.hasLineOfSight(entity)) {
									if (entity instanceof Monster) {
										bee.setAnger(1);
										bee.setTarget((LivingEntity) entity);
									} else if (entity instanceof Player) {
										if (plugin.getRace(player) != plugin.getRace((Player)entity)) {
											bee.setAnger(1);
											bee.setTarget((LivingEntity) entity);
										}
									}
								}
							}
						} else {//attack mode
							if (!bee.getTarget().isValid()) {
								bee.setAnger(0);
							}
							if (bee.hasStung()) {
								bee.setHasStung(false);
							}
						}
						if (bee.isValid()) {
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
						} else {
							livingBees.put(player.getUniqueId(), livingBees.get(player.getUniqueId()) - 1);
						}
					}
				}, 1L);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						bee.remove();
					}
				}, 4800L);
				if (livingBees.containsKey(player.getUniqueId())) {
					livingBees.put(player.getUniqueId(), livingBees.get(player.getUniqueId()) + 1);
				} else {
					livingBees.put(player.getUniqueId(), 1);
				}
			}
		}
		
		
		
	}
	
	@EventHandler
	public void onInventoryClick(BlockPlaceEvent event) {
		if (!Recipes.GOLDEN_FLOWER.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBreedWasp(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Bee)) return;
		
		
		Bee bee = (Bee) event.getRightClicked();
		if (!bee.getPersistentDataContainer().has(new NamespacedKey(plugin, "wasp"), PersistentDataType.INTEGER)) return;
		
		player.sendMessage(ChatColor.RED + "You cannot breed wasps.");
		event.setCancelled(true);
		
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Bee)) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.GOLDEN_FLOWER.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.GOLDEN_FLOWER.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		
	}
}
