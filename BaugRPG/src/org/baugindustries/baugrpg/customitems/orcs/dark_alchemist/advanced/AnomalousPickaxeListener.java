package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.advanced;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AnomalousPickaxeListener implements Listener {
	private Main plugin;
	
	List<Material> mats;
	
	public AnomalousPickaxeListener(Main plugin) {
		this.plugin = plugin;
		mats = Arrays.asList(Material.NETHER_GOLD_ORE, Material.NETHER_QUARTZ_ORE, Material.ANCIENT_DEBRIS);
	}
	
	
	@EventHandler
	public void onInventoryClick(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if (!Recipes.ANOMALOUS_PICKAXE.matches(plugin, player.getInventory().getItemInMainHand())) return;

		if (!mats.contains(event.getBlock().getType())) return;
		
		Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand(), player);
		for (ItemStack item : drops) {
			
			if (item.getType().equals(Material.GOLD_NUGGET)) {
				item.setType(Material.IRON_NUGGET);
			} else if (item.getType().equals(Material.NETHER_GOLD_ORE)) {
				item.setType(Material.IRON_ORE);
			} else if (item.getType().equals(Material.QUARTZ)) {
				item.setType(Material.REDSTONE);
				item.setAmount(item.getAmount() * ((int)((Math.random() + 1) * 2)));
			} else if (item.getType().equals(Material.NETHER_QUARTZ_ORE)) {
				item.setType(Material.REDSTONE_ORE);
			} else if (item.getType().equals(Material.ANCIENT_DEBRIS)) {
				item.setType(Material.DIAMOND);
				item.setAmount(3);
			}
			
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
			event.setDropItems(false);
		}
	}

}
