package org.baugindustries.baugrpg.customitems.men.stable_master.expert;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SoulOfTheEquestrianListener implements Listener {
	private Main plugin;
	
	int horseCount = 30;
	double horseSpeed = 1;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 900000;//fifteen minutes
	
	public SoulOfTheEquestrianListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		
		if (event.getDamage() < player.getHealth()) return;
		
		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.STABLE_MASTER)) return;
		
		if (!Recipes.SOUL_OF_THE_EQUESTRIAN.playerIsCarrying(player, plugin)) return;
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				return;
			}
		}
		
		event.setCancelled(true);
		
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		

		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
		for (int i = 0; i < horseCount; i++) {
			
			
			
			Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
			loc.add((Math.random() * 30) - 15, (Math.random() * 10) - 5, (Math.random() * 30) - 15);
			loc.setY(plugin.getTopBlock(loc.getBlock()).getY() + 1);
			
			Location pseudoLoc = player.getLocation();
			pseudoLoc.add((Math.random() * 8) - 4, 0, (Math.random() * 8) - 4);
			
			Vector direction = pseudoLoc.toVector().subtract(loc.toVector()).normalize().multiply(horseSpeed);
			
			Horse horse = (Horse) player.getWorld().spawnEntity(loc, EntityType.HORSE);
			horse.setInvulnerable(true);
			horse.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000, 120, false, false));
			horse.getInventory().setArmor(new ItemStack(Material.IRON_HORSE_ARMOR));
			
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					if (horse.isValid()) {
						
						for (Entity entity : horse.getNearbyEntities(1, 1, 1)) {
							if (entity instanceof Monster) {
								Monster monster = (Monster) entity;
								monster.damage(plugin.damageArmorCalculation(monster, 3));
								Vector vec = horse.getLocation().toVector();
								vec.setY(entity.getLocation().getY() - 1);
								entity.setVelocity(entity.getLocation().toVector().subtract(vec).multiply(1));
							} else if (entity instanceof Player) {
								Player otherPlayer = (Player) entity;
								if (plugin.getRace(player) != plugin.getRace(otherPlayer)) {
									
									player.damage(plugin.damageArmorCalculation(player, 3));
									Vector vec = horse.getLocation().toVector();
									vec.setY(entity.getLocation().getY() - 1);
									entity.setVelocity(entity.getLocation().toVector().subtract(vec).multiply(1));
								}
							}
						}
						
						Location newLoc = horse.getLocation().add(direction);
						newLoc.setY(plugin.getTopBlock(newLoc.getBlock()).getY() + 1);
						if (horse.getLocation().distance(newLoc) > 5) {
							horse.remove();
						} else {
							horse.teleport(newLoc);
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1L);
						}
					}
				}
			}, 1L);
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					if (horse.isValid()) {
						horse.remove();
					}
				}
			}, 60L);
			
			
		}
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.SOUL_OF_THE_EQUESTRIAN.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.SOUL_OF_THE_EQUESTRIAN.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (!player.isSneaking()) return;
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		

		if ((plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.STABLE_MASTER))) {
			player.sendMessage(ChatColor.RED + "You must be a Stable Master to use this.");
			return;
		}
		
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				
				
				int minutesToMillis = 60000;
				Long timeRemaining = (cooldown.get(player.getUniqueId()) - System.currentTimeMillis());
				String timeString = "";
				int timeValue;
				if (timeRemaining > minutesToMillis) {
					//display in minutes
					timeValue = (int)(timeRemaining / minutesToMillis) + 1;
					if (timeValue == 1) {
						timeString = (timeValue + " minute remaining.");
					} else {
						timeString = (timeValue + " minutes remaining.");
					}
				} else {
					//display in seconds
					timeValue = (int)(timeRemaining / 1000) + 1;
					if (timeValue == 1) {
						timeString = (timeValue + " second remaining.");
					} else {
						timeString = (timeValue + " seconds remaining.");
					}
					
				}
				
				
				player.sendMessage(ChatColor.RED + "You can't use that yet. " + timeString);
				return;
			} else {
				player.sendMessage(ChatColor.GREEN + "Soul of the Equestrian is ready.");
			}
		} else {
			player.sendMessage(ChatColor.GREEN + "Soul of the Equestrian is ready.");
		}
	}

}
