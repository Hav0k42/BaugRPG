package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.advanced;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EmblemOfTheWhirlwindListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 60000;//One minute
	int timeoutTicks = 400;
	
	public EmblemOfTheWhirlwindListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onUseGaiasWrath(PlayerInteractEvent event) {
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.EMBLEM_OF_THE_WHIRLWIND.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.EMBLEM_OF_THE_WHIRLWIND.matches(plugin, player.getInventory().getItemInOffHand())) return;
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
		
		
		activateWhirlwind(player, player.getLocation().add(0, 15, 0), 0);
		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
	}
	
	private void activateWhirlwind(Player player, Location loc, int tick) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			  public void run() {
				  for (Entity entity : loc.getWorld().getNearbyEntities(loc, 2, 15, 2)) {
					  if (entity instanceof Player) {
						  Vector velocity = entity.getVelocity();
						  double newY = velocity.getY();
						  newY += 0.5;
						  if (newY > 1) {
							  newY = 1;
						  }
						  velocity.setY(newY);
						  entity.setVelocity(velocity);
						  Player otherPlayer = (Player) entity;
						  if (plugin.getRace(player) == plugin.getRace(otherPlayer)) {
							  otherPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 120, 0));
						  }
					  }
				  }
				  loc.getWorld().spawnParticle(Particle.SPELL, loc, 20, 2, 15, 2, 0);
				  player.getWorld().playSound(loc, Sound.ENTITY_PHANTOM_FLAP, SoundCategory.MASTER, 2f, 1f);
				  loc.getWorld().spawnParticle(Particle.CLOUD, loc, 5, 2, 15, 2, 0);
				  if (tick < timeoutTicks) {
					  activateWhirlwind(player, loc, tick + 1);
				  }
			  }
		 }, 1L);
	}

}
