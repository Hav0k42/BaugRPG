package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HempListener implements Listener {
	
	Main plugin;
	
	public HempListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void eatHemp(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.HEMP.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.HEMP.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 0));
		event.setCancelled(true);
		
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
