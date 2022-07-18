package org.baugindustries.baugrpg.customitems.orcs.enraged_berserker.intermediate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import org.bukkit.ChatColor;

public class SpectralHarnessListener implements Listener {
	private Main plugin;
	
	private int breedCooldown = 450000;
	private int breedingTimeout = 600;//30 seconds in ticks
	
	HashMap<Ghast, Long> breedingGhasts;
	HashMap<Ghast, Ghast> foundGhasts;
	List<Ghast> riddenGhasts;
	
	public SpectralHarnessListener(Main plugin) {
		this.plugin = plugin;
		breedingGhasts = new HashMap<Ghast, Long>();
		foundGhasts = new HashMap<Ghast, Ghast>();
		riddenGhasts = new ArrayList<Ghast>();
		
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			  public void run() {
					for (Ghast ghast : breedingGhasts.keySet()) {
						if (ghast.isValid()) {
							ghast.getWorld().spawnParticle(Particle.HEART, ghast.getLocation(), 2, 2, 1, 2);
							breedingGhasts.put(ghast, 5 + breedingGhasts.get(ghast));
							if (breedingGhasts.get(ghast) < breedingTimeout) {
								for (Entity entity : ghast.getNearbyEntities(30, 30, 30)) {
									if (entity instanceof Ghast) {
										if (breedingGhasts.containsKey((Ghast)entity)) {
											foundGhasts.put(ghast, (Ghast) entity);
											breedingGhasts.remove(ghast);
											breedingGhasts.remove((Ghast)entity);
										}
									}
								}
							} else {
								breedingGhasts.remove(ghast);
							}
						} else {
							breedingGhasts.remove(ghast);
						}
							 
					}
					
					
					
					foundGhasts.forEach((ghast1, ghast2) -> {
						if (ghast1.isValid() && ghast2.isValid()) {
							ghast1.getWorld().spawnParticle(Particle.HEART, ghast1.getLocation(), 2, 2, 1, 2);
							ghast1.getWorld().spawnParticle(Particle.HEART, ghast2.getLocation(), 2, 2, 1, 2);
							if (ghast1.getLocation().distance(ghast2.getLocation()) < 2.5) {
								ghast1.getWorld().spawnEntity(ghast1.getLocation(), EntityType.GHAST);
								foundGhasts.remove(ghast1);
							} else if (ghast1.getLocation().distance(ghast2.getLocation()) < 4.5) {
								ghast1.setVelocity(ghast2.getLocation().toVector().subtract(ghast1.getLocation().toVector()).normalize().multiply(0.8));
								ghast2.setVelocity(ghast1.getLocation().toVector().subtract(ghast2.getLocation().toVector()).normalize().multiply(0.8));
							} else {
								ghast1.setVelocity(ghast2.getLocation().toVector().subtract(ghast1.getLocation().toVector()).normalize().multiply(0.2));
								ghast2.setVelocity(ghast1.getLocation().toVector().subtract(ghast2.getLocation().toVector()).normalize().multiply(0.2));
							}
						} else {
							foundGhasts.remove(ghast1);
						}
					});
					
					riddenGhasts.forEach(ghast -> {
						if (ghast.getPassengers().size() == 0) {
							riddenGhasts.remove(ghast);
						} else {
							if (ghast.getPassengers().get(0) instanceof Player) {
								Player player = (Player) ghast.getPassengers().get(0);
								ghast.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(0.3));
							}
						}
					});
					
			  }
		 }, 5L, 5L);
		
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.SPECTRAL_HARNESS.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.SPECTRAL_HARNESS.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}
		event.setCancelled(true);

		if (!event.getRightClicked().getType().equals(EntityType.GHAST)) return;
		
		Ghast ghast = (Ghast) event.getRightClicked();
		PersistentDataContainer data = ghast.getPersistentDataContainer();
		
		
		
		if (data.has(new NamespacedKey(plugin, "breedCooldown"), PersistentDataType.LONG)) {
			if (data.get(new NamespacedKey(plugin, "breedCooldown"), PersistentDataType.LONG) > System.currentTimeMillis()) {
				player.sendMessage(ChatColor.RED + "This ghast isn't ready to breed.");
				return;
			}
		}
		breedingGhasts.put(ghast, 0L);
		data.set(new NamespacedKey(plugin, "breedCooldown"), PersistentDataType.LONG, breedCooldown + System.currentTimeMillis());
		
	}
	
	@EventHandler
	public void onInventoryClick(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		if (!(event.getEntity() instanceof Ghast)) return;
		
		Player player = (Player) event.getDamager();
		Ghast ghast = (Ghast) event.getEntity();
		
		if (!Recipes.SPECTRAL_HARNESS.matches(plugin, player.getInventory().getItemInMainHand())) return;
		event.setCancelled(true);
		
		if (ghast.getPassengers().size() != 0) return;
		
		ghast.addPassenger(player);
		riddenGhasts.add(ghast);
		
	}

}
