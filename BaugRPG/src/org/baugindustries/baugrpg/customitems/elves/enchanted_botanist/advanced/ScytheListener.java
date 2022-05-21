package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.advanced;

import java.util.Collection;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ScytheListener implements Listener {
	private Main plugin;
	
	public ScytheListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if (!Recipes.SCYTHE.matches(plugin, player.getInventory().getItemInMainHand())) return;
		if (!(event.getBlock().getBlockData() instanceof Ageable)) return;
		
		Ageable blockData = (Ageable) event.getBlock().getBlockData();
		
		if (blockData.getAge() != blockData.getMaximumAge()) return;
		
		Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand(), player);
		for (ItemStack item : drops) {
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
		}
	}

}
