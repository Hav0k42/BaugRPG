package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class VealListener implements Listener {
	
	Main plugin;
	
	public VealListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void feedWolf(PlayerInteractEntityEvent event) {
		if (!(event.getRightClicked() instanceof Tameable && event.getRightClicked() instanceof Sittable)) return;

		Player player = event.getPlayer();
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.VEAL.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.VEAL.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		Tameable animal = (Tameable) event.getRightClicked();
		if (animal.isTamed()) return;
		animal.setOwner(player);
		animal.setTamed(true);
		player.getWorld().spawnParticle(Particle.HEART, animal.getLocation().add(0, 0.7, 0), 4, 0.2, 0.2, 0.2);
		
		if (player.getGameMode().equals(GameMode.CREATIVE)) return;
		
		ItemStack newItem = null;
	    if (event.getHand().equals(EquipmentSlot.HAND)) {
	    	newItem = player.getInventory().getItemInMainHand();
	    	if (newItem.getAmount() == 1) {
	    		player.getInventory().setItemInMainHand(null);
	    	} else {
		    	newItem.setAmount(newItem.getAmount() - 1);
		    	player.getInventory().setItemInMainHand(newItem);
	    	}
	    } else {
	    	newItem = player.getInventory().getItemInOffHand();
	    	if (newItem.getAmount() == 1) {
	    		player.getInventory().setItemInOffHand(null);
	    	} else {
		    	newItem.setAmount(newItem.getAmount() - 1);
		    	player.getInventory().setItemInOffHand(newItem);
	    	}
	    }
		
	}

}
