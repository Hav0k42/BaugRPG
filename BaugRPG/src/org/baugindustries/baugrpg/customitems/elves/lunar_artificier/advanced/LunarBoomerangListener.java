package org.baugindustries.baugrpg.customitems.elves.lunar_artificier.advanced;


import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.ArmorStandEntity;
import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LunarBoomerangListener implements Listener {
	private Main plugin;
	final int spinSpeed = 25;
	final int rangeDist = 30;
	final double circleStepAmount = 1;
	final float circleAngleStepAmount = 5;
	final double totalCircleSteps = 180.0 / circleAngleStepAmount;
	final double movementSteps = 25;
	final int stepsToPlayer = 41;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 5000;//five seconds
	
	public LunarBoomerangListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.LUNAR_BOOMERANG.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.LUNAR_BOOMERANG.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
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
		
		ArmorStandEntity boomerang = new ArmorStandEntity(player.getLocation());
		double centerXOffset = -0.2;
		double centerZOffset = -0.7;
		boomerang.add(Material.FEATHER, new Vector(0 + centerXOffset, 0, 0 + centerZOffset), 0, Math.toRadians(90));
		boomerang.add(Material.FEATHER, new Vector(-0.5 + centerXOffset, 0.55, 0.5 + centerZOffset), 90, Math.toRadians(-90));
		boomerang.spawn();
		boomerang.glow(200);

		event.setCancelled(true);
		Location playerLoc = player.getLocation();
		Vector direction = playerLoc.getDirection().multiply(rangeDist);
		
		Location endPos = new Location(player.getWorld(), direction.getX() + playerLoc.getX(), direction.getY() + playerLoc.getY(), direction.getZ() + playerLoc.getZ());
		int curveDirection = 1;
		if (Math.random() < 0.5) {
			curveDirection = -1;
		}
		runMoveBoomerang(boomerang, playerLoc, endPos, 0, curveDirection, 0, player);
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
		
	}
	
	private void runMoveBoomerang(ArmorStandEntity boomerang, Location initPos, Location endPos, int stage, int curveDirection, int currentStepToPlayer, Player player) {
		Runnable throwBoomerang = new Runnable() {
			public void run() {
				Location currentPos = boomerang.getCenterLoc();
				double x;
				double y;
				double z;
				player.getWorld().playSound(currentPos, Sound.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.MASTER, 1f, 1f);
				switch (stage) {
					case 0:
						double xDist = endPos.getX() - initPos.getX();
						double yDist = endPos.getY() - initPos.getY();
						double zDist = endPos.getZ() - initPos.getZ();
						Location newLoc = new Location(endPos.getWorld(), boomerang.getCenterLoc().getX() + (xDist / movementSteps), boomerang.getCenterLoc().getY() + (yDist / movementSteps), boomerang.getCenterLoc().getZ() + (zDist / movementSteps), initPos.getYaw(), initPos.getPitch());
						
						boomerang.setLocation(newLoc);
						boomerang.rotate((float) (boomerang.getRotation() + spinSpeed));
						if (newLoc.distance(endPos) < 1.5) {
							runMoveBoomerang(boomerang, initPos, endPos, stage + 1, curveDirection, currentStepToPlayer, player);
						} else {
							runMoveBoomerang(boomerang, initPos, endPos, stage, curveDirection, currentStepToPlayer, player);
						}
						break;
					case 1:
						Vector currentDirection = currentPos.getDirection();
						x = currentPos.getX() + (currentDirection.getX() * circleStepAmount);
						y = currentPos.getY() + (currentDirection.getY() * circleStepAmount);
						z = currentPos.getZ() + (currentDirection.getZ() * circleStepAmount);
						
						float yaw = currentPos.getYaw() + (circleAngleStepAmount * curveDirection);
						float pitch = (float) (currentPos.getPitch() - ((initPos.getPitch() * 2) / totalCircleSteps));
						
						Location nextLoc = new Location(currentPos.getWorld(), x, y, z, yaw, pitch);
						
						boomerang.setLocation(nextLoc);
						boomerang.rotate((float) (boomerang.getRotation() + spinSpeed));
						
						if (yaw == initPos.getYaw() + 180 || (yaw == initPos.getYaw() - 180)) {
							runMoveBoomerang(boomerang, initPos, endPos, stage + 1, curveDirection, currentStepToPlayer, player);
						} else {
							runMoveBoomerang(boomerang, initPos, endPos, stage, curveDirection, currentStepToPlayer, player);
						}
						break;
					case 2:
						x = currentPos.getX() - ((currentPos.getX() - player.getLocation().getX()) / (stepsToPlayer - currentStepToPlayer));
						y = currentPos.getY() - ((currentPos.getY() - player.getLocation().getY()) / (stepsToPlayer - currentStepToPlayer));
						z = currentPos.getZ() - ((currentPos.getZ() - player.getLocation().getZ()) / (stepsToPlayer - currentStepToPlayer));
						
						Location newestLoc = new Location(initPos.getWorld(), x, y, z, currentPos.getYaw(), currentPos.getPitch());
						
						boomerang.setLocation(newestLoc);
						boomerang.rotate((float) (boomerang.getRotation() + spinSpeed));
						
						if (currentStepToPlayer + 1 != stepsToPlayer) {
							runMoveBoomerang(boomerang, initPos, endPos, stage, curveDirection, currentStepToPlayer + 1, player);
						} else {
							boomerang.kill();
						}
						break;
				}
				
				for (Entity entity : player.getWorld().getNearbyEntities(currentPos, 1, 1, 1)) {
					if (entity instanceof LivingEntity) {
						LivingEntity lEntity = (LivingEntity) entity;
						if (lEntity instanceof Monster) {
							lEntity.setVelocity(player.getLocation().add(0, 4, 0).toVector().subtract(lEntity.getLocation().toVector()).normalize().multiply(2));
							lEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0));
							lEntity.damage(plugin.damageArmorCalculation(lEntity, 2));
						} else if (lEntity instanceof Player) {
							Player otherPlayer = (Player) lEntity;
							if (plugin.getRace(player) != plugin.getRace(otherPlayer)) {
								lEntity.setVelocity(player.getLocation().add(0, 1, 0).toVector().subtract(lEntity.getLocation().toVector()).normalize().multiply(2));
								lEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 0));
								lEntity.damage(plugin.damageArmorCalculation(lEntity, 2));
							}
						}
					}
				}
				
			}
  		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, throwBoomerang, 1L);
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.LUNAR_BOOMERANG.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.LUNAR_BOOMERANG.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		
	}
}
