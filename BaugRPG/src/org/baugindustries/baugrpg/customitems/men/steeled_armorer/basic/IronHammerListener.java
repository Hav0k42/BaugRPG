package org.baugindustries.baugrpg.customitems.men.steeled_armorer.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class IronHammerListener implements Listener {
	private Main plugin;
	
	public IronHammerListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void hitWithHammer(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		Player player = (Player) event.getDamager();
		if (player.getInventory().getItemInMainHand() == null) return;
		if (!(Recipes.IRON_HAMMER.matches(plugin, player.getInventory().getItemInMainHand()))) return;
		
		event.setDamage(event.getDamage() / 2);
		event.getEntity().getNearbyEntities(2.5, 2.5, 2.5).forEach(entity -> {
			boolean setKnockback = false;
			if (entity instanceof Player) {
				Player tempPlayer = (Player) entity;
				if (plugin.getRace(tempPlayer) != plugin.getRace(player)) {
					setKnockback = true;
				}
			} else {
				setKnockback = true;
			}
			
			if (setKnockback) {
				Location knockbackLocation = player.getLocation();
				knockbackLocation.setY(entity.getLocation().getY() - 2);
				entity.setVelocity(entity.getLocation().toVector().subtract(knockbackLocation.toVector()).normalize());
			}
		});
	}
	
}
