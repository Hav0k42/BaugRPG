package org.baugindustries.baugrpg.customitems.men.verdant_shepherd.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CorruptedStaffListener implements Listener {
	
	Main plugin;
	
	public CorruptedStaffListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickGrass(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (!event.getClickedBlock().getType().equals(Material.GRASS_BLOCK)) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.CORRUPTED_STAFF.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.CORRUPTED_STAFF.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		

		player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 4, 0.5, 0.5, 0.5);
		
		event.getClickedBlock().setType(Material.MYCELIUM);
	}

	

	@EventHandler
	public void clickCow(PlayerInteractEntityEvent event) {

		Player player = event.getPlayer();
		
		
		if (!(event.getRightClicked() instanceof Cow)) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.CORRUPTED_STAFF.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.CORRUPTED_STAFF.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		
		player.getWorld().spawnEntity(event.getRightClicked().getLocation(), EntityType.MUSHROOM_COW);

		player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getRightClicked().getLocation().add(0.5, 0.5, 0.5), 4, 0.5, 0.5, 0.5);
		event.getRightClicked().remove();
		
		
	}
}
