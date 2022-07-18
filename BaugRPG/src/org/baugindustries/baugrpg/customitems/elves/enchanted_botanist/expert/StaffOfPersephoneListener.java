package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.expert;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class StaffOfPersephoneListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	HashMap<UUID, Integer> livingFlowers = new HashMap<UUID, Integer>();
	int cooldownTime = 120000;//two minutes
	int flowerCount = 2;
	int maxFlowers = 6;
	

	Material[] flowers = {Material.ORANGE_TULIP, Material.PINK_TULIP, Material.RED_TULIP, Material.WHITE_TULIP, Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.LILY_OF_THE_VALLEY, Material.CORNFLOWER, Material.ALLIUM, Material.OXEYE_DAISY, Material.POPPY, Material.DANDELION};
	
	public StaffOfPersephoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseGaiasWrath(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.STAFF_OF_PERSEPHONE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.STAFF_OF_PERSEPHONE.matches(plugin, player.getInventory().getItemInOffHand())) return;
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
		
		if (livingFlowers.containsKey(player.getUniqueId()) && livingFlowers.get(player.getUniqueId()) == maxFlowers) {
			player.sendMessage(ChatColor.RED + "You cannot summon any more spirits");
			return;
		}
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
		for (int i = 0; i < flowerCount; i++) {
			if (!livingFlowers.containsKey(player.getUniqueId()) || livingFlowers.get(player.getUniqueId()) < maxFlowers) {
				Zombie flower = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
				flower.setBaby();
				flower.setInvisible(true);
				flower.setCanPickupItems(false);
				flower.setCustomName("Fallen Spirit");
				flower.setCustomNameVisible(false);
				flower.getEquipment().setHelmet(new ItemStack(flowers[(int) (Math.random() * flowers.length)]));
				flower.getEquipment().setChestplate(null);
				flower.getEquipment().setLeggings(null);
				flower.getEquipment().setBoots(null);
				flower.getEquipment().setItemInMainHand(null);
				flower.getEquipment().setItemInOffHand(null);
				flower.getPersistentDataContainer().set(new NamespacedKey(plugin, "flower"), PersistentDataType.STRING, player.getUniqueId().toString());
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						if (flower.getTarget() == null || flower.getTarget().equals(player)) {//follow mode.
							if (flower.getLocation().distance(player.getLocation()) > 25) {
								flower.teleport(player);
							} else if (flower.getLocation().distance(player.getLocation()) > 6.5) {
								flower.setTarget(player);
							} else {
								flower.setTarget(null);
							}
							for (Entity entity : flower.getNearbyEntities(40, 40, 40)) {
								if (flower.hasLineOfSight(entity)) {
									if (entity instanceof Monster) {
										if (!(entity.getPersistentDataContainer().has(new NamespacedKey(plugin, "flower"), PersistentDataType.STRING) && plugin.getRace(Bukkit.getOfflinePlayer(UUID.fromString(entity.getPersistentDataContainer().get(new NamespacedKey(plugin, "flower"), PersistentDataType.STRING)))) == plugin.getRace(player))) {
											flower.setTarget((LivingEntity) entity);
										}
									} else if (entity instanceof Player) {
										if (plugin.getRace(player) != plugin.getRace((Player)entity)) {
											flower.setTarget((LivingEntity) entity);
										}
									}
								}
							}
						} else {//attack mode
							if (!flower.getTarget().isValid()) {
								flower.setTarget(null);
							}
						}
						if (flower.isValid()) {
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
						} else {
							livingFlowers.put(player.getUniqueId(), livingFlowers.get(player.getUniqueId()) - 1);
						}
					}
				}, 1L);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						flower.remove();
					}
				}, 9600L);
				if (livingFlowers.containsKey(player.getUniqueId())) {
					livingFlowers.put(player.getUniqueId(), livingFlowers.get(player.getUniqueId()) + 1);
				} else {
					livingFlowers.put(player.getUniqueId(), 1);
				}
			}
		}
		

		
		
	}
	
	@EventHandler
	public void onTargetEntity(EntityTargetEvent event) {
		if (!event.getEntityType().equals(EntityType.ZOMBIE)) return;
		Zombie zomb = (Zombie) event.getEntity();
		if (zomb.getPersistentDataContainer().has(new NamespacedKey(plugin, "flower"), PersistentDataType.STRING)) {
			event.setCancelled(true);
		}
	}
	
}
