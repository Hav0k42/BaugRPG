package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class EnrichedHoeListener implements Listener {
	private Main plugin;
	
	public EnrichedHoeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onHarvestCrop(BlockBreakEvent event) {
		if (!(event.getBlock().getBlockData() instanceof Ageable)) return;
		
		Ageable blockData = (Ageable) event.getBlock().getBlockData();
		
		if (blockData.getAge() != blockData.getMaximumAge()) return;
		
		Player player = event.getPlayer();
		if (!Recipes.ENRICHED_HOE.matches(plugin, player.getInventory().getItemInMainHand())) return;

		ExperienceOrb orb = (ExperienceOrb) player.getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.EXPERIENCE_ORB);
		orb.setExperience(1);
	}

}
