package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.basic;

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

public class RockyTransmuterListener implements Listener {
	
	Main plugin;
	
	public RockyTransmuterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickRock(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.ROCKY_TRANSMUTER.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.ROCKY_TRANSMUTER.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		Material[] stones = {Material.STONE, Material.CALCITE, Material.ANDESITE, Material.GRANITE, Material.DIORITE, Material.TUFF};
		
		
		Block block = event.getClickedBlock();
		
		
		for (Material stone : stones) {
			if (block.getType().equals(stone)) {
				block.setType(stones[(int)(Math.random() * stones.length)]);
				player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.5, 0.5), 4, 0.5, 0.5, 0.5);
				break;
			}
		}
		
		event.setCancelled(true);
		
		
		
	}

}
