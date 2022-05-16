package org.baugindustries.baugrpg.customitems.dwarves.arcane_jeweler.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class GoldRingListener implements Listener {
	private Main plugin;
	
	private int maxAbsorption = 15;
	
	
	public GoldRingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onMineGold(BlockBreakEvent event) {
		if (!(event.getBlock().getType().equals(Material.GOLD_ORE) || event.getBlock().getType().equals(Material.DEEPSLATE_GOLD_ORE))) return;
		
		Player player = event.getPlayer();
		if (!Recipes.GOLD_RING.playerIsCarrying(player, plugin)) return;
		
		boolean droppedGold = false;
		
		for (ItemStack item : event.getBlock().getDrops(player.getInventory().getItemInMainHand())) {
			if (item.getType().equals(Material.RAW_GOLD)) {
				droppedGold = true;
			}
		}
		
		if (!droppedGold) return;
		
		double currentAbsorption = player.getAbsorptionAmount();
		
		if (currentAbsorption >= maxAbsorption) return;
		
		player.setAbsorptionAmount(currentAbsorption + 1);
		
	}

}
