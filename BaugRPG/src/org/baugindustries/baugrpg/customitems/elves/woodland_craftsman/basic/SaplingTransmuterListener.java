package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class SaplingTransmuterListener implements Listener {
	
	Main plugin;
	
	public SaplingTransmuterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickFlower(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.SAPLING_TRANSMUTER.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.SAPLING_TRANSMUTER.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		Material[] saplings = {Material.OAK_SAPLING, Material.BIRCH_SAPLING, Material.ACACIA_SAPLING, Material.JUNGLE_SAPLING, Material.DARK_OAK_SAPLING, Material.SPRUCE_SAPLING};
		
		
		Block block = event.getClickedBlock();
		
		
		for (Material flower : saplings) {
			if (block.getType().equals(flower)) {
				block.setType(saplings[(int)(Math.random() * saplings.length)]);
				player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.7, 0.5), 4, 0.2, 0.2, 0.2);
				break;
			}
		}
		
		event.setCancelled(true);
		
		
		
	}

}
